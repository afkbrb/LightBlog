package com.iustu.controller.admin;

import com.iustu.common.pojo.MyResult;
import com.iustu.entity.BlogType;
import com.iustu.service.BlogService;
import com.iustu.service.BlogTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/blogType", method = RequestMethod.POST)
public class AdminBlogTypeController {

    @Autowired
    private BlogTypeService blogTypeService;
    @Autowired
    private BlogService blogService;

    @RequestMapping("/list")
    @ResponseBody
    public MyResult getBlogTypeListByPage(){
        MyResult result = new MyResult();
        List<BlogType> list = blogTypeService.getBlogTypeList();
        result.setRows(list);
        result.setTotal(blogTypeService.getBlogTypeCount());
        return result;
    }


    @RequestMapping("/insert")
    @ResponseBody
    public String insertBlogType(BlogType blogType){
        blogTypeService.insertBlogType(blogType);
        System.out.println("insert:" + new Date().toString() + "  " + blogType.getId());
        return "success";
    }

    @RequestMapping("/update/{id}")
    @ResponseBody
    public String updateBlogType(@PathVariable int id, BlogType blogType){
        blogType.setId(id);
        blogTypeService.updateBlogType(blogType);
        System.out.println("update:" + new Date().toString() + "  " + blogType.getId());
        return "success";
    }

    @RequestMapping("/delete/{ids}")
    @ResponseBody
    public String deleteBlogType(@PathVariable String ids){
        String[] strings = ids.split(",");
        for(String str : strings){
            int id = Integer.parseInt(str);
            //删除关联的blog
            blogService.deleteBlogByTypeId(id);
            blogTypeService.deleteBlogType(id);
        }
        return "success";
    }
}
