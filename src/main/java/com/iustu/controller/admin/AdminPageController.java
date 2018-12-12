package com.iustu.controller.admin;

import com.iustu.service.BlogService;
import com.iustu.service.BlogTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @Autowired
    private BlogTypeService blogTypeService;
    @Autowired
    private BlogService blogService;

    @RequestMapping(value = {"/index", "/"})
    public String showIndex() {
        return "admin/main";
    }

    @RequestMapping("/modifyBlog/{blogId}")
    public String showModifyBlog(@PathVariable Integer blogId, Model model){
        model.addAttribute("blogTypeList", blogTypeService.getBlogTypeList());
        model.addAttribute("blog", blogService.getBlogById(blogId));
        return "admin/modifyBlog";
    }

    @RequestMapping("/writeBlog")
    public String showWriteBlog(Model model){
        model.addAttribute("blogTypeList", blogTypeService.getBlogTypeList());
        return "admin/writeBlog";
    }


    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return "admin/" + page;
    }

}
