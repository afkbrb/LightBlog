package com.iustu.mapper.custom;

import com.iustu.entity.Blog;

import java.util.List;

public interface BlogMapperCustom {
    List<Blog> getBlogListByPage(int start, int rows);
    List<Blog> getBlogListByPageWithBlobs(int start, int rows);
    List<Blog> getBlogListByTitleAndPage(int start, int rows, String title);
    List<Blog> getBlogListByTypeIdTitleAndPage(int start, int rows, int typeId, String title);
    List<Blog> getBlogListOrderByClickCount();
}
