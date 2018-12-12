package com.iustu.service.impl;

import com.iustu.entity.BlogType;
import com.iustu.entity.BlogTypeExample;
import com.iustu.mapper.BlogTypeMapper;
import com.iustu.mapper.custom.BlogTypeMapperCustom;
import com.iustu.service.BlogTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogTypeServiceImpl implements BlogTypeService {

    @Autowired
    private BlogTypeMapper blogTypeMapper;
    @Autowired
    private BlogTypeMapperCustom blogTypeMapperCustom;

    @Override
    public String getBlogTypeNameById(Integer id) {
        BlogType blogType = blogTypeMapper.selectByPrimaryKey(id);
        String result = blogType.getTypeName();
        return result;
    }

    @Override
    public List<BlogType> getBlogTypeList() {
        BlogTypeExample example = new BlogTypeExample();
        return blogTypeMapper.selectByExample(example);
    }

    @Override
    public List<BlogType> getBlogTypeListByPage(int start, int rows) {
        return blogTypeMapperCustom.getBlogTypeListByPage(start, rows);
    }

    @Override
    public Long getBlogTypeCount() {
        BlogTypeExample example = new BlogTypeExample();
        return blogTypeMapper.countByExample(example);
    }

    @Override
    public void insertBlogType(BlogType blogType) {
        blogTypeMapper.insert(blogType);
    }

    @Override
    public void updateBlogType(BlogType blogType) {
        blogTypeMapper.updateByPrimaryKey(blogType);
    }

    @Override
    public void deleteBlogType(int id) {
        blogTypeMapper.deleteByPrimaryKey(id);
    }

}
