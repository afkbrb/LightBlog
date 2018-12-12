package com.iustu.entity.custom;

import com.iustu.entity.BlogType;

public class BlogTypeCustom extends BlogType {

    private Long blogCount;

    public Long getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Long blogCount) {
        this.blogCount = blogCount;
    }
}
