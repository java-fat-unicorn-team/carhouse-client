package com.carhouse.controller;

import com.carhouse.model.Comment;
import com.carhouse.provider.CommentProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * The type Comment controller.
 * It is used to perform queries to server with ajax without updating page.
 */
@Controller
public class CommentController {

    private CommentProvider commentProvider;

    /**
     * Instantiates a new Comment controller.
     *
     * @param commentProvider the comment provider
     */
    @Autowired
    public CommentController(final CommentProvider commentProvider) {
        this.commentProvider = commentProvider;
    }

    /**
     * Gets update form.
     *
     * @param model the model
     * @return the update form
     */
    @GetMapping("/carSale/comment/addForm")
    public String getAddForm(final Model model) {
        model.addAttribute("comment", new Comment());
        return "fragments::addDialog";
    }

    /**
     * Add comment to car sale advertisement by id.
     *
     * @param comment   the comment object from form
     * @param carSaleId the car sale id
     * @param model     the model
     * @return the comment block html
     */
    @PostMapping("/carSale/{carSaleId}/comment")
    public String addComment(@ModelAttribute final Comment comment, @PathVariable final int carSaleId,
                             final Model model) {
        int commentId = commentProvider.addComment(comment, carSaleId);
        comment.setCommentId(commentId);
        model.addAttribute("comment", comment);
        return "fragments::comment";
    }

    /**
     * Gets add comment form.
     *
     * @param commentId the comment id
     * @param model     the model
     * @return the update form
     */
    @GetMapping("/carSale/comment/{commentId}/updateForm")
    public String getUpdateForm(@PathVariable final int commentId, final Model model) {
        model.addAttribute("comment", commentProvider.getCommentById(commentId));
        return "fragments::updateDialog";
    }

    /**
     * Update comment.
     *
     * @param comment   the comment object from form
     * @param commentId the comment id
     * @param model     the model
     * @return the comment block html
     */
    @PostMapping("/carSale/comment/{commentId}")
    public String updateComment(@ModelAttribute final Comment comment, @PathVariable final int commentId,
                                final Model model) {
        comment.setCommentId(commentId);
        commentProvider.updateComment(comment);
        model.addAttribute("comment", comment);
        return "fragments::comment";
    }

    /**
     * Delete comment by id.
     *
     * @param commentId the comment id
     * @return the success model
     */
    @GetMapping("/carSale/comment/{commentId}/delete")
    public String deleteComment(@PathVariable final int commentId) {
        commentProvider.deleteComment(commentId);
        return "fragments::success";
    }
}
