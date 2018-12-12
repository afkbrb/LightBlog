package com.iustu.service.impl;

import com.iustu.entity.Blog;
import com.iustu.entity.BlogExample;
import com.iustu.mapper.BlogMapper;
import com.iustu.mapper.custom.BlogMapperCustom;
import com.iustu.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private BlogMapperCustom blogMapperCustom;

    @Override
    public List<Blog> getBlogList() {
        BlogExample example = new BlogExample();
        List<Blog> list = blogMapper.selectByExampleWithBLOBs(example);

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public List<Blog> getBlogListByTypeIdTitleAndPage(int start, int rows, int typeId, String title) {
        return blogMapperCustom.getBlogListByTypeIdTitleAndPage(start, rows, typeId, title);
    }


    @Override
    public List<Blog> getBlogListByTitleAndPage(int start, int rows, String title) {
        return blogMapperCustom.getBlogListByTitleAndPage(start, rows, title);
    }

    @Override
    public List<Blog> getBlogListByPage(int start, int rows) {
        return blogMapperCustom.getBlogListByPage(start, rows);
    }

    @Override
    public List<Blog> getBlogListByPageWithBlobs(int start, int rows) {
        return blogMapperCustom.getBlogListByPageWithBlobs(start, rows);
    }

    @Override
    public List<Blog> getBlogListOrderByClickCount() {

        return blogMapperCustom.getBlogListOrderByClickCount();
    }

    @Override
    public Long getBlogCount() {
        BlogExample example = new BlogExample();
        return blogMapper.countByExample(example);
    }

    @Override
    public Long getBlogCountByTitle(String title) {
        BlogExample example = new BlogExample();
        BlogExample.Criteria criteria = example.createCriteria();
        criteria.andTitleLike(title);
        return blogMapper.countByExample(example);
    }

    @Override
    public Long getBlogCountByTypeId(int id) {
        BlogExample example = new BlogExample();
        BlogExample.Criteria criteria = example.createCriteria();
        criteria.andTypeIdEqualTo(id);
        return blogMapper.countByExample(example);
    }

    @Override
    public void insertBlog(Blog blog) {
        blogMapper.insert(blog);
    }

    @Override
    public void updateBlog(Blog blog) {
        blogMapper.updateByPrimaryKeyWithBLOBs(blog);
    }

    @Override
    public void deleteBlogById(Integer id) {
        blogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteBlogByTypeId(int typeId) {
        BlogExample example = new BlogExample();
        BlogExample.Criteria criteria = example.createCriteria();
        criteria.andTypeIdEqualTo(typeId);
        blogMapper.deleteByExample(example);
    }

    @Override
    public Blog getBlogById(Integer id) {
        return blogMapper.selectByPrimaryKey(id);
    }
}
