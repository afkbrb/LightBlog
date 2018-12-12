package com.iustu.service;

import com.iustu.entity.Blog;

import java.util.List;

public interface BlogService{
    List<Blog> getBlogList();
    List<Blog> getBlogListByTypeIdTitleAndPage(int start, int rows, int typeId, String title);
    List<Blog> getBlogListByPage(int start, int rows);
    List<Blog> getBlogListByTitleAndPage(int start, int rows, String title);
    List<Blog> getBlogListByPageWithBlobs(int start, int rows);
    List<Blog> getBlogListOrderByClickCount();
    Long getBlogCount();
    Long getBlogCountByTitle(String title);
    Long getBlogCountByTypeId(int id);
    void insertBlog(Blog blog);
    void updateBlog(Blog blog);
    void deleteBlogById(Integer id);
    void deleteBlogByTypeId(int typeId);
    Blog getBlogById(Integer id);

}
