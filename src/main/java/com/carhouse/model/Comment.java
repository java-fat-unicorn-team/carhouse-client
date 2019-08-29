package com.carhouse.model;

/**
 * The comment model is used to create comments for car sale announcement.
 * The model includes user's name who added the comment, comment and car sale announcement to which this comment applies
 * @author Katuranau Maksimilyan
 */
public class Comment {
    private int commentId;
    private String userName;
    private String comment;

    /**
     * Instantiates a new Comment.
     */
    public Comment() {
    }

    /**
     * Gets comment id.
     *
     * @return the comment id
     */
    public int getCommentId() {
        return commentId;
    }

    /**
     * Sets comment id.
     *
     * @param commentId the comment id
     * @return comment object
     */
    public Comment setCommentId(final int commentId) {
        this.commentId = commentId;
        return this;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     * @return comment object
     */
    public Comment setUserName(final String userName) {
        this.userName = userName;
        return this;
    }

    /**
     * Gets comment.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets comment.
     *
     * @param comment the comment
     * @return comment object
     */
    public Comment setComment(final String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public final String toString() {
        return "userName='" + userName + '\''
                + ", comment='" + comment;
    }
}
