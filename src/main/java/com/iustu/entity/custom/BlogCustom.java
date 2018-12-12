package com.iustu.entity.custom;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iustu.entity.Blog;

import java.util.Date;

public class BlogCustom extends Blog {
    private String blogType;

    public String getBlogType() {
        return blogType;
    }

    public void setBlogType(String blogType) {
        this.blogType = blogType;
    }

    @Override
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    public Date getCreateDate() {
        return super.getCreateDate();
    }

    @Override
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    public Date getUpdateDate() {
        return super.getUpdateDate();
    }
}
