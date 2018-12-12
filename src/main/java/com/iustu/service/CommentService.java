package com.iustu.service;

import com.iustu.entity.Comment;

import java.util.List;

public interface CommentService {
    void deleteCommentById(int id);
    void deleteCommentByBlogId(int blogId);
    void insertComment(Comment comment);
    void updateComment(Comment comment);

    Comment getCommentById(int id);
    List<Comment> getCommentListByPage(int start, int rows);
    List<Comment> getUnreviewedCommentListByPage(int start, int rows);
    List<Comment> getCommentListByPageAndBlogId(int start, int rows, int blogId);
    Integer getCommentCount();
    Integer getCommentCountByState(int state);
    Integer getPassedCommentCountByBlogId(int blogId);

}
