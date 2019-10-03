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

import java.util.ArrayList;
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
        mockMvc.perform(get("/carSale/comment/addForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments::addDialog"))
                .andExpect(forwardedUrl("fragments::addDialog"));
    }

    @Test
    void addComment() throws Exception {
        int carSaleId = 3;
        int commentId = 12;
        Comment comment = new Comment().setCommentId(commentId);
        when(commentProvider.addComment(any(Comment.class), eq(carSaleId))).thenReturn(commentId);
        mockMvc.perform(post("/carSale/{carSaleId}/comment", carSaleId)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments::comment"))
                .andExpect(forwardedUrl("fragments::comment"));
        verifyNoMoreInteractions(commentProvider);
    }

    @Test
    void addCommentToNotExistCarSale() throws Exception {
        int carSaleId = 3;
        String errorMassage = "there is not car sale with id = " + carSaleId;
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        when(commentProvider.addComment(any(Comment.class), eq(carSaleId)))
                .thenThrow(createException(httpStatus, errorMassage));
        mockMvc.perform(post("/carSale/{carSaleId}/comment", carSaleId)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(new Comment())))
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsg", errorMassage));
        verify(commentProvider).addComment(any(Comment.class), eq(carSaleId));
    }

    @Test
    void getUpdateForm() throws Exception {
        int commentId = 3;
        when(commentProvider.getCommentById(commentId)).thenReturn(commentList.get(1));
        mockMvc.perform(get("/carSale/comment/{commentId}/updateForm", commentId))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments::updateDialog"))
                .andExpect(forwardedUrl("fragments::updateDialog"))
                .andExpect(model().attribute("comment", commentList.get(1)));
    }

    @Test
    void getUpdateFormForNotExistComment() throws Exception {
        int commentId = 3;
        String errorMassage = "there is not comment with id = " + commentId;
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        when(commentProvider.getCommentById(commentId)).thenThrow(createException(httpStatus, errorMassage));
        mockMvc.perform(get("/carSale/comment/{commentId}/updateForm", commentId))
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsg", errorMassage));
        verify(commentProvider).getCommentById(commentId);
    }

    @Test
    void updateComment() throws Exception {
        int commentId = 12;
        Comment comment = new Comment().setCommentId(commentId);
        mockMvc.perform(post("/carSale/comment/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments::comment"))
                .andExpect(forwardedUrl("fragments::comment"));
    }

    @Test
    void updateNotExistComment() throws Exception {
        int commentId = 30;
        String errorMassage = "there is not comment with id = " + commentId;
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        doThrow(createException(httpStatus, errorMassage)).when(commentProvider).updateComment(any(Comment.class));
        mockMvc.perform(post("/carSale/comment/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(new Comment())))
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsg", errorMassage));
        verify(commentProvider).updateComment(any(Comment.class));
    }

    @Test
    void deleteComment() throws Exception {
        int commentId = 2;
        mockMvc.perform(get("/carSale/comment/{commentId}/delete", commentId))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments::success"))
                .andExpect(forwardedUrl("fragments::success"));
        verify(commentProvider).deleteComment(commentId);
    }

    @Test
    void deleteNotExistComment() throws Exception {
        int commentId = 20;
        String errorMassage = "there is not comment with id = " + commentId;
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        doThrow(createException(httpStatus, errorMassage)).when(commentProvider).deleteComment(commentId);
        mockMvc.perform(get("/carSale/comment/{commentId}/delete", commentId)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(new Comment())))
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorCode", httpStatus.value()))
                .andExpect(model().attribute("errorMsg", errorMassage));
        verify(commentProvider).deleteComment(commentId);
    }

    private HttpClientErrorException createException(HttpStatus httpStatus, String errorMassage)
            throws JsonProcessingException {
        ExceptionJSONResponse exceptionJSONResponse = new ExceptionJSONResponse();
        exceptionJSONResponse.setStatus(httpStatus.value());
        exceptionJSONResponse.setMessage(errorMassage);
        HttpClientErrorException exception = HttpClientErrorException.create(httpStatus,
                String.valueOf(httpStatus.value()), null, objectMapper.writeValueAsBytes(exceptionJSONResponse),
                null);
        return exception;
    }
}
