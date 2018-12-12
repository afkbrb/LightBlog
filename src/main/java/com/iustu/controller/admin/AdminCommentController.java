package com.iustu.controller.admin;

import com.iustu.common.pojo.MyResult;
import com.iustu.entity.Blog;
import com.iustu.entity.Comment;
import com.iustu.entity.custom.CommentCustom;
import com.iustu.service.BlogService;
import com.iustu.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/comment", method = RequestMethod.POST)
public class AdminCommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @RequestMapping("/list")
    @ResponseBody
    public MyResult getCommentListByPage(@RequestParam(value="page", required=false) Integer page, @RequestParam(value="rows", required=false) Integer rows){
        MyResult result = new MyResult();
        List<CommentCustom> resultList = new ArrayList<>();
        List<Comment> list = commentService.getCommentListByPage(rows * (page - 1), rows);
        if(list != null && list.size() > 0){
            for(Comment comment : list){
                CommentCustom commentCustom = new CommentCustom();
                BeanUtils.copyProperties(comment, commentCustom);
                commentCustom.setBlogTitle(blogService.getBlogById(comment.getBlogId()).getTitle());
                resultList.add(commentCustom);
            }
        }
        result.setRows(resultList);
        result.setTotal(commentService.getCommentCount());
        return result;
    }

    @RequestMapping("/unreviewedlist")
    @ResponseBody
    public MyResult getUnreviewedCommentListByPage(@RequestParam(value="page", required=false) Integer page, @RequestParam(value="rows", required=false) Integer rows){
        MyResult result = new MyResult();
        List<CommentCustom> resultList = new ArrayList<>();
        List<Comment> list = commentService.getUnreviewedCommentListByPage(rows * (page - 1), rows);
        if(list != null && list.size() > 0){
            for(Comment comment : list){
                CommentCustom commentCustom = new CommentCustom();
                BeanUtils.copyProperties(comment, commentCustom);
                commentCustom.setBlogTitle(blogService.getBlogById(comment.getBlogId()).getTitle());
                resultList.add(commentCustom);
            }
        }
        result.setRows(resultList);
        result.setTotal(commentService.getCommentCountByState(0));
        return result;
    }

    @RequestMapping("/delete/{ids}")
    @ResponseBody
    public String deleteComment(@PathVariable String ids){
        String[] strings = ids.split(",");
        for(String str : strings){
            int id = Integer.parseInt(str);
            Comment comment = commentService.getCommentById(id);
            //如果评论是审核通过的，删除时应从相应的文章评论总数中减去
            if(comment.getState() == 1){
                Blog blog = blogService.getBlogById(comment.getBlogId());
                blog.setReplyCount(blog.getReplyCount() - 1);
                blogService.updateBlog(blog);
            }
            commentService.deleteCommentById(id);
        }
        return "success";
    }

    @RequestMapping("/review/{ids}/{state}")
    @ResponseBody
    public String setCommentState(@PathVariable String ids, @PathVariable int state){
        String[] strings = ids.split(",");
        for(String str : strings){
            int id = Integer.parseInt(str);
            Comment comment = commentService.getCommentById(id);
            comment.setState(state);
            commentService.updateComment(comment);
            Blog blog = blogService.getBlogById(comment.getBlogId());
            blog.setReplyCount(commentService.getPassedCommentCountByBlogId(blog.getId()));
            blogService.updateBlog(blog);
        }
        return "success";
    }


}
