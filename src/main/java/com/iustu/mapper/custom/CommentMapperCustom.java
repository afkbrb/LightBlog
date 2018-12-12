package com.iustu.mapper.custom;

import com.iustu.entity.Comment;

import java.util.List;

public interface CommentMapperCustom {
    List<Comment> getCommentListByPage(int start, int rows);
    List<Comment> getUnreviewedCommentListByPage(int start, int rows);
    List<Comment> getCommentListByPageAndBlogId(int start, int rows, int blogId);
}
