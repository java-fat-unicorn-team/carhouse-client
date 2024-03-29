package com.carhouse.controller;

import com.carhouse.handler.RestTemplateResponseErrorHandler;
import com.carhouse.model.Comment;
import com.carhouse.model.dto.ExceptionJSONResponse;
import com.carhouse.provider.CommentProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    @Mock
    private CommentProvider commentProvider;

    @InjectMocks
    private CommentController commentController;

    private static List<Comment> commentList;

    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeAll
    static void createCommentList() {
        commentList = new ArrayList<>() {{
            add(new Comment().setCommentId(1).setUserName("Vadim").setComment("Great"));
            add(new Comment().setCommentId(2).setUserName("Petya").setComment("Cool"));
            add(new Comment().setCommentId(3).setUserName("Sasha").setComment("Nice"));
        }};
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController)
                .setControllerAdvice(new RestTemplateResponseErrorHandler(objectMapper))
                .build();
    }

    @Test
    void getAddForm() throws Exception {
        mockMvc.perform(get("/carhouse/carSale/comment/addForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments::addDialog"))
                .andExpect(forwardedUrl("fragments::addDialog"));
    }

    @Test
    void addComment() throws Exception {
        int carSaleId = 3;
        when(commentProvider.addComment(any(Comment.class), eq(carSaleId))).thenReturn(12);
        mockMvc.perform(post("/carhouse/carSale/{carSaleId}/comment", carSaleId)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .flashAttr("comment", commentList.get(1)))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments::comment"))
                .andExpect(forwardedUrl("fragments::comment"));
        verifyNoMoreInteractions(commentProvider);
    }

    @Test
    void addCommentToNotExistCarSale() throws Exception {
        int carSaleId = 3;
        String requestUrl = UriComponentsBuilder.newInstance()
                .path("/carhouse/carSale/{carSaleId}/comment").buildAndExpand(carSaleId).toString();
        List<String> errorMassage = Collections.singletonList("there is not car sale with id = " + carSaleId);
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        when(commentProvider.addComment(any(Comment.class), eq(carSaleId)))
                .thenThrow(createException(httpStatus, errorMassage, requestUrl));
        mockMvc.perform(post(requestUrl)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .flashAttr("comment", commentList.get(0)))
                .andExpect(status().is(httpStatus.value()))
                .andExpect(view().name("fragments::error"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsgList", errorMassage));
        verify(commentProvider).addComment(any(Comment.class), eq(carSaleId));
    }

    @Test
    void addCommentValidationError() throws Exception {
        Comment comment = new Comment().setUserName("").setComment("");
        mockMvc.perform(post("/carhouse/carSale/{carSaleId}/comment", 12)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .flashAttr("comment", comment))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments::addDialog"))
                .andExpect(forwardedUrl("fragments::addDialog"));
    }

    @Test
    void getUpdateForm() throws Exception {
        int commentId = 3;
        when(commentProvider.getCommentById(commentId)).thenReturn(commentList.get(1));
        mockMvc.perform(get("/carhouse/carSale/comment/{commentId}/updateForm", commentId))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments::updateDialog"))
                .andExpect(forwardedUrl("fragments::updateDialog"))
                .andExpect(model().attribute("comment", commentList.get(1)));
    }

    @Test
    void getUpdateFormForNotExistComment() throws Exception {
        int commentId = 3;
        String requestUrl = UriComponentsBuilder.newInstance()
                .path("/carhouse/carSale/comment/{commentId}/updateForm").buildAndExpand(commentId).toString();
        List<String> errorMassage = Collections.singletonList("there is not comment with id = " + commentId);
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        when(commentProvider.getCommentById(commentId)).thenThrow(createException(httpStatus, errorMassage, requestUrl));
        mockMvc.perform(get(requestUrl))
                .andExpect(status().is(httpStatus.value()))
                .andExpect(view().name("fragments::error"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsgList", errorMassage));
        verify(commentProvider).getCommentById(commentId);
    }

    @Test
    void updateComment() throws Exception {
        int commentId = 12;
        Comment comment = commentList.get(0).setCommentId(commentId);
        mockMvc.perform(post("/carhouse/carSale/comment/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .flashAttr("comment", comment))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments::comment"))
                .andExpect(forwardedUrl("fragments::comment"));
    }

    @Test
    void updateNotExistComment() throws Exception {
        int commentId = 30;
        String requestUrl = "/carhouse/carSale/comment/" + commentId;
        List<String> errorMassage = Collections.singletonList("there is not comment with id = " + commentId);
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        doThrow(createException(httpStatus, errorMassage, requestUrl))
                .when(commentProvider).updateComment(any(Comment.class));
        mockMvc.perform(post(requestUrl)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .flashAttr("comment", commentList.get(0)))
                .andExpect(status().is(httpStatus.value()))
                .andExpect(view().name("fragments::error"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsgList", errorMassage));
        verify(commentProvider).updateComment(any(Comment.class));
    }

    @Test
    void updateCommentValidationError() throws Exception {
        Comment comment = new Comment().setUserName("").setComment("");
        mockMvc.perform(post("/carhouse/carSale/comment/12")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .flashAttr("comment", comment))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments::updateDialog"))
                .andExpect(forwardedUrl("fragments::updateDialog"));
    }

    @Test
    void deleteComment() throws Exception {
        int commentId = 2;
        mockMvc.perform(get("/carhouse/carSale/comment/{commentId}/delete", commentId))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments::success"))
                .andExpect(forwardedUrl("fragments::success"));
        verify(commentProvider).deleteComment(commentId);
    }

    @Test
    void deleteNotExistComment() throws Exception {
        int commentId = 20;
        String requestUrl = UriComponentsBuilder.newInstance()
                .path("/carhouse/carSale/comment/{commentId}/delete").buildAndExpand(commentId).toString();
        List<String> errorMassage = Collections.singletonList("there is not comment with id = " + commentId);
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        doThrow(createException(httpStatus, errorMassage, requestUrl)).when(commentProvider).deleteComment(commentId);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().is(httpStatus.value()))
                .andExpect(view().name("fragments::error"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsgList", errorMassage));
        verify(commentProvider).deleteComment(commentId);
    }

    private HttpClientErrorException createException(HttpStatus httpStatus, List<String> errorMassage, String requestUrl)
            throws JsonProcessingException {
        ExceptionJSONResponse exceptionJSONResponse = new ExceptionJSONResponse();
        exceptionJSONResponse.setStatus(httpStatus.value());
        exceptionJSONResponse.setMessages(errorMassage);
        exceptionJSONResponse.setPath(requestUrl);
        HttpClientErrorException exception = HttpClientErrorException.create(httpStatus,
                String.valueOf(httpStatus.value()), null, objectMapper.writeValueAsBytes(exceptionJSONResponse),
                null);
        return exception;
    }
}
