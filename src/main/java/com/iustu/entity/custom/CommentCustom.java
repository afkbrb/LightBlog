package com.iustu.entity.custom;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iustu.entity.Comment;

import java.util.Date;

public class CommentCustom extends Comment {
    String blogTitle;

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    @Override
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    public Date getCommentDate() {
        return super.getCommentDate();
    }

}
