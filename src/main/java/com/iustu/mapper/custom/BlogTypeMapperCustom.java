package com.iustu.mapper.custom;

import com.iustu.entity.BlogType;

import java.util.List;

public interface BlogTypeMapperCustom {
    List<BlogType> getBlogTypeListByPage(int start, int rows);
}
