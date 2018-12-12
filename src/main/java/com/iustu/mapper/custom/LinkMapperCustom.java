package com.iustu.mapper.custom;

import com.iustu.entity.Link;

import java.util.List;

public interface LinkMapperCustom {
    List<Link> getLinkListByPage(int start, int rows);
    List<Link> getLinkListOrdered();
}
