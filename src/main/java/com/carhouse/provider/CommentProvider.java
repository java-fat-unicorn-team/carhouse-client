package com.carhouse.provider;

import com.carhouse.model.Comment;

import java.util.List;

/**
 * The interface for car sale comment data provider.
 */
public interface CommentProvider {

    /**
     * Gets car sale advertisement comments by id.
     *
     * @param carSaleId the car sale id to get comments
     * @return the car sale advertisement comments list
     */
    List<Comment> getComments(int carSaleId);

    /**
     * Gets car sale advertisement comment by id.
     *
     * @param commentId the comment id
     * @return the car sale
     */
    Comment getCommentById(int commentId);

    /**
     * Add new comment to the car sale advertisement.
     *
     * @param comment   object from form
     * @param carSaleId car sale id
     * @return the generated comment id
     */
    Integer addComment(Comment comment, int carSaleId);

    /**
     * Update car sale advertisement comment.
     *
     * @param comment object from form
     */
    void updateComment(Comment comment);

    /**
     * Delete car sale advertisement comment by id.
     *
     * @param commentId the comment id
     */
    void deleteComment(int commentId);
}
