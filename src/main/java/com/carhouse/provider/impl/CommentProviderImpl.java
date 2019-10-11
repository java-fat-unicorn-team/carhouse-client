package com.carhouse.provider.impl;

import com.carhouse.model.Comment;
import com.carhouse.provider.CommentProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * The comment data provider.
 */
@Component
public class CommentProviderImpl implements CommentProvider {

    private final Logger LOGGER = LogManager.getLogger(CommentProviderImpl.class);

    @Value("${carSale.url.host}:${carSale.url.port}")
    private String URL;
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

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Gets car sale advertisement comments by id.
     *
     * @param carSaleId the car sale id to get comments
     * @return the car sale advertisement comments list
     */
    @Override
    public List<Comment> getComments(final int carSaleId) {
        LOGGER.debug("method getComments with parameter carSaleId = {}", carSaleId);
        ResponseEntity<List<Comment>> response = restTemplate.exchange(URL + CAR_SALE_COMMENT_ALL, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Comment>>() {
                }, carSaleId);
        return response.getBody();
    }

    /**
     * Gets car sale advertisement comment by id.
     *
     * @param commentId the comment id
     * @return the car sale
     */
    @Override
    public Comment getCommentById(final int commentId) {
        LOGGER.debug("method getCommentById with parameter commentId = {}", commentId);
        return restTemplate.getForObject(URL + CAR_SALE_COMMENT_BY_ID, Comment.class, commentId);
    }

    /**
     * Add new comment to the car sale advertisement.
     *
     * @param comment   object from form
     * @param carSaleId car sale id
     * @return the generated comment id
     */
    @Override
    public Integer addComment(final Comment comment, final int carSaleId) {
        LOGGER.debug("method addComment with parameters comment = [{}], carSaleId = {}", comment, carSaleId);
        return restTemplate.postForObject(URL + CAR_SALE_COMMENT_ADD, comment, Integer.class, carSaleId);
    }

    /**
     * Update car sale advertisement comment.
     *
     * @param comment object from form
     */
    @Override
    public void updateComment(final Comment comment) {
        LOGGER.debug("method updateComment with parameter comment = [{}]", comment);
        restTemplate.put(URL + CAR_SALE_COMMENT_UPDATE, comment);
    }

    /**
     * Delete car sale advertisement comment by id.
     *
     * @param commentId the comment id
     */
    @Override
    public void deleteComment(final int commentId) {
        LOGGER.debug("method deleteComment with parameter commentId = [{}]", commentId);
        restTemplate.delete(URL + CAR_SALE_COMMENT_DELETE, commentId);
    }
}
