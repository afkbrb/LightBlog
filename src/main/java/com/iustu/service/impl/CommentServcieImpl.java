package com.iustu.service.impl;

import com.iustu.entity.Comment;
import com.iustu.entity.CommentExample;
import com.iustu.mapper.CommentMapper;
import com.iustu.mapper.custom.CommentMapperCustom;
import com.iustu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServcieImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentMapperCustom commentMapperCustom;

    @Override
    public void deleteCommentById(int id) {
        commentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteCommentByBlogId(int blogId) {
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andBlogIdEqualTo(blogId);
        commentMapper.deleteByExample(example);
    }

    @Override
    public void insertComment(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    public void updateComment(Comment comment) {
        commentMapper.updateByPrimaryKey(comment);
    }

    @Override
    public Comment getCommentById(int id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Comment> getCommentListByPage(int start, int rows) {
        return commentMapperCustom.getCommentListByPage(start, rows);
    }

    @Override
    public List<Comment> getUnreviewedCommentListByPage(int start, int rows) {
        return commentMapperCustom.getUnreviewedCommentListByPage(start, rows);
    }

    @Override
    public List<Comment> getCommentListByPageAndBlogId(int start, int rows, int blogId) {
        return commentMapperCustom.getCommentListByPageAndBlogId(start, rows, blogId);
    }

    @Override
    public Integer getCommentCount() {
        CommentExample example = new CommentExample();
        return commentMapper.countByExample(example);
    }

    @Override
    public Integer getCommentCountByState(int state) {
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(state);
        return commentMapper.countByExample(example);
    }

    @Override
    public Integer getPassedCommentCountByBlogId(int blogId) {
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(1);
        criteria.andBlogIdEqualTo(blogId);
        return commentMapper.countByExample(example);
    }
}
