package com.iustu.service;

import com.iustu.entity.BlogType;

import java.util.List;

public interface BlogTypeService {

    String getBlogTypeNameById(Integer id);
    List<BlogType> getBlogTypeList();
    List<BlogType> getBlogTypeListByPage(int start, int rows);
    Long getBlogTypeCount();
    void insertBlogType(BlogType blogType);
    void updateBlogType(BlogType blogType);
    void deleteBlogType(int id);

}
