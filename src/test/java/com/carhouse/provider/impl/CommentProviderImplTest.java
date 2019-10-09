package com.carhouse.provider.impl;

import com.carhouse.config.TestConfig;
import com.carhouse.model.Comment;
import com.carhouse.model.dto.ExceptionJSONResponse;
import com.carhouse.provider.CommentProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class CommentProviderImplTest {

    @Value("${carSale.carSale.comments.byId}")
    private String CAR_SALE_COMMENT_ALL;
    @Value("${carSale.carSale.comment.byId}")
    private String CAR_SALE_COMMENT_BY_ID;
    @Value("${carSale.carSale.comment.add}")
    private String CAR_SALE_COMMENT_ADD;
    @Value("${carSale.carSale.comment.update}")
    private String CAR_SALE_COMMENT_UPDATE;
    @Value("${carSale.carSale.comment.delete}")
    private String CAR_SALE_COMMENT_DELETE;

    private static List<Comment> commentList;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommentProvider commentProvider;

    @BeforeAll
    static void setup() {
        commentList = new ArrayList<>() {{
            add(new Comment().setCommentId(1).setComment("cool").setUserName("Vadim"));
            add(new Comment().setCommentId(2).setComment("good").setUserName("Kolya"));
            add(new Comment().setCommentId(3).setComment("amazing").setUserName("Vlad"));
        }};
    }

    @Test
    void getComments() throws JsonProcessingException {
        int carSaleId = 2;
        stubFor(get(urlPathEqualTo(buildUrl(CAR_SALE_COMMENT_ALL, carSaleId)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(commentList)))
        );
        List<Comment> result = commentProvider.getComments(carSaleId);
        assertEquals(commentList.size(), result.size());
        assertEquals(commentList.get(0).getCommentId(), result.get(0).getCommentId());
        assertEquals(commentList.get(1).getComment(), result.get(1).getComment());
        assertEquals(commentList.get(2).getUserName(), result.get(2).getUserName());
    }


    @Test
    void getCommentsOfNotExistCarSale() throws JsonProcessingException {
        int carSaleId = 123;
        int responseStatus = 404;
        String errorMsg = "there is not car sale with id = " + carSaleId;
        stubFor(get(urlPathEqualTo(buildUrl(CAR_SALE_COMMENT_ALL, carSaleId)))
                .willReturn(aResponse()
                        .withStatus(responseStatus)
                        .withBody(objectMapper.writeValueAsString(
                                createExceptionJSONResponse(responseStatus, errorMsg))))
        );
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> commentProvider.getComments(carSaleId));
        ExceptionJSONResponse response = objectMapper.readValue(exception.getResponseBodyAsString(),
                ExceptionJSONResponse.class);
        assertEquals(responseStatus, response.getStatus());
        assertEquals(errorMsg, response.getMessages().get(0));
    }

    @Test
    void getComment() throws JsonProcessingException {
        int commentId = 1;
        Comment comment = new Comment().setCommentId(commentId).setUserName("Vadim").setComment("Cool");
        stubFor(get(urlPathEqualTo(buildUrl(CAR_SALE_COMMENT_BY_ID, commentId)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(comment)))
        );
        Comment result = commentProvider.getCommentById(commentId);
        assertEquals(comment.getCommentId(), result.getCommentId());
        assertEquals(comment.getUserName(), result.getUserName());
        assertEquals(comment.getComment(), result.getComment());
    }

    @Test
    void getNotExistComment() throws JsonProcessingException {
        int commentId = 123;
        int responseStatus = 404;
        String errorMsg = "there is not comment with id = " + commentId;
        stubFor(get(urlPathEqualTo(buildUrl(CAR_SALE_COMMENT_BY_ID, commentId)))
                .willReturn(aResponse()
                        .withStatus(responseStatus)
                        .withBody(objectMapper.writeValueAsString(
                                createExceptionJSONResponse(responseStatus, errorMsg))))
        );
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> commentProvider.getCommentById(commentId));
        ExceptionJSONResponse response = objectMapper.readValue(exception.getResponseBodyAsString(),
                ExceptionJSONResponse.class);
        assertEquals(responseStatus, response.getStatus());
        assertEquals(errorMsg, response.getMessages().get(0));
    }

    @Test
    void addComment() throws JsonProcessingException {
        int carSaleId = 7;
        Integer commentId = 13;
        stubFor(post(urlPathEqualTo(buildUrl(CAR_SALE_COMMENT_ADD, carSaleId)))
                .withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(commentList.get(1))))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .withBody(String.valueOf(commentId))));
        assertEquals(commentId, commentProvider.addComment(commentList.get(1), carSaleId));
    }

    @Test
    void addCommentsToNotExistCarSale() throws JsonProcessingException {
        int carSaleId = 123;
        int responseStatus = 424;
        String errorMsg = "there is not car sale with id = " + carSaleId;
        stubFor(get(urlPathEqualTo(buildUrl(CAR_SALE_COMMENT_ADD, carSaleId)))
                .willReturn(aResponse()
                        .withStatus(responseStatus)
                        .withBody(objectMapper.writeValueAsString(
                                createExceptionJSONResponse(responseStatus, errorMsg))))
        );
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> commentProvider.getComments(carSaleId));
        ExceptionJSONResponse response = objectMapper.readValue(exception.getResponseBodyAsString(),
                ExceptionJSONResponse.class);
        assertEquals(responseStatus, response.getStatus());
        assertEquals(errorMsg, response.getMessages().get(0));
    }

    @Test
    void updateComment() throws JsonProcessingException {
        int commentId = 3;
        Comment comment = commentList.get(2).setCommentId(commentId);
        stubFor(put(urlPathEqualTo(CAR_SALE_COMMENT_UPDATE))
                .withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(comment)))
                .willReturn(aResponse()
                        .withStatus(200)));
        commentProvider.updateComment(comment);
    }

    @Test
    void updateNotExistComment() throws JsonProcessingException {
        int commentId = 123;
        int responseStatus = 404;
        Comment comment = commentList.get(2).setCommentId(commentId);
        String errorMsg = "there is not comment with id = " + commentId;
        stubFor(put(urlPathEqualTo(CAR_SALE_COMMENT_UPDATE))
                .withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(comment)))
                .willReturn(aResponse()
                        .withStatus(responseStatus)
                        .withBody(objectMapper.writeValueAsString(
                                createExceptionJSONResponse(responseStatus, errorMsg))))
        );
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> commentProvider.updateComment(comment));
        ExceptionJSONResponse response = objectMapper.readValue(exception.getResponseBodyAsString(),
                ExceptionJSONResponse.class);
        assertEquals(responseStatus, response.getStatus());
        assertEquals(errorMsg, response.getMessages().get(0));
    }

    @Test
    void deleteComment() {
        int commentId = 2;
        stubFor(delete(urlPathEqualTo(buildUrl(CAR_SALE_COMMENT_DELETE, commentId)))
                .willReturn(aResponse()
                        .withStatus(200))
        );
        commentProvider.deleteComment(commentId);
    }

    @Test
    void deleteNotExistComment() throws JsonProcessingException {
        int commentId = 30;
        int responseStatus = 404;
        String errorMsg = "there is not comment with id = " + commentId;
        stubFor(delete(urlPathEqualTo(buildUrl(CAR_SALE_COMMENT_DELETE, commentId)))
                .willReturn(aResponse()
                        .withStatus(responseStatus)
                        .withBody(objectMapper.writeValueAsString(
                                createExceptionJSONResponse(responseStatus, errorMsg))))
        );
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> commentProvider.deleteComment(commentId));
        ExceptionJSONResponse response = new ObjectMapper().readValue(exception.getResponseBodyAsString(),
                ExceptionJSONResponse.class);
        assertEquals(responseStatus, response.getStatus());
        assertEquals(errorMsg, response.getMessages().get(0));
    }

    private String buildUrl(String url, int parameter) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path(url)
                .buildAndExpand(parameter);
        return uriComponents.toString();
    }

    private ExceptionJSONResponse createExceptionJSONResponse(int responseStatus, String errorMsg) {
        ExceptionJSONResponse exceptionJSONResponse = new ExceptionJSONResponse();
        exceptionJSONResponse.setStatus(responseStatus);
        exceptionJSONResponse.setMessages(Collections.singletonList(errorMsg));
        return exceptionJSONResponse;
    }
}