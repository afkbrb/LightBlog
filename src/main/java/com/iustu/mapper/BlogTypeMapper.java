package com.iustu.mapper;

import com.iustu.entity.BlogType;
import com.iustu.entity.BlogTypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogTypeMapper {
    long countByExample(BlogTypeExample example);

    int deleteByExample(BlogTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BlogType record);

    int insertSelective(BlogType record);

    List<BlogType> selectByExample(BlogTypeExample example);

    BlogType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BlogType record, @Param("example") BlogTypeExample example);

    int updateByExample(@Param("record") BlogType record, @Param("example") BlogTypeExample example);

    int updateByPrimaryKeySelective(BlogType record);

    int updateByPrimaryKey(BlogType record);
}