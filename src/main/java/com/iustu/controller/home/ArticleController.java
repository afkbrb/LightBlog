package com.iustu.controller.home;

import com.iustu.common.pojo.MyResult;
import com.iustu.entity.Blog;
import com.iustu.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private BlogService blogService;


    @RequestMapping("/get/{id}")
    @ResponseBody
    public Blog getArticle(@PathVariable int id){
        return blogService.getBlogById(id);
    }

    @RequestMapping("/list")
    @ResponseBody
    public MyResult getArticleList(@RequestParam(value="page", required=false) Integer page, @RequestParam(value="rows", required=false) Integer rows,
                                   @RequestParam(value="blogType") Integer typeId, @RequestParam(value="title", required=false) String title) {
        if(title == null){
            title = "";
        }
        MyResult result = new MyResult();
        List<Blog> list;
        if(typeId == 0){
            list = blogService.getBlogListByTitleAndPage(rows * (page - 1), rows, "%" + title + "%");
            result.setTotal(blogService.getBlogCount());
        }else{
            list = blogService.getBlogListByTypeIdTitleAndPage(rows * (page - 1), rows, typeId, "%" + title + "%");
            result.setTotal(blogService.getBlogCountByTypeId(typeId));
        }

        result.setRows(list);

        return result;
    }

    @RequestMapping(value = "/like/add", method = RequestMethod.POST)
    @ResponseBody
    public Integer addLike(Integer blogId){
        Blog blog = blogService.getBlogById(blogId);
        blog.setLikeCount(blog.getLikeCount() + 1);
        blogService.updateBlog(blog);
        return blog.getLikeCount();
    }

    @RequestMapping(value = "/click/add", method = RequestMethod.POST)
    @ResponseBody
    public Integer addClick(Integer blogId){
        Blog blog = blogService.getBlogById(blogId);
        blog.setClickCount(blog.getClickCount() + 1);
        blogService.updateBlog(blog);
        return blog.getClickCount();
    }

    @RequestMapping(value="/order/clickCount", method = RequestMethod.POST)
    @ResponseBody
    public List<Blog> getBlogListOrderByClickCount(){
        return blogService.getBlogListOrderByClickCount();
    }


}
