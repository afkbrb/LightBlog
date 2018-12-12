package com.iustu.controller.home;

import com.iustu.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @Value("${ABOUT_ID}")
    private String ABOUT_ID;

    @Autowired
    private BlogService blogService;

    @RequestMapping(value={"/", "/index"})
    public String showIndex(@RequestParam(value="title", required=false) String title,@RequestParam(value="blogType", required=false) Integer blogType, Model model){
        if(title != null){
            model.addAttribute("title", title);
        }else{
            model.addAttribute("title", "");
        }
        if(blogType != null){
            model.addAttribute("blogType", blogType);
        }else{
            model.addAttribute("blogType", 0);
        }
        return "index";
    }

    @RequestMapping("/article/{id}")
    public String showArticle(@PathVariable int id, Model model){
        if(blogService.getBlogById(id) == null){
            return "error";
        }
        model.addAttribute("blog", blogService.getBlogById(id));
        return "article";
    }

    @RequestMapping("/login")
    public String login(){
        return "admin/login";
    }

    @RequestMapping("/about")
    public String showAbout(Model model){
        model.addAttribute("blog", blogService.getBlogById(Integer.parseInt(ABOUT_ID)));
        return "about";
    }

    @RequestMapping("/error")
    public String showError(){
        return "error";
    }

}
