package com.iustu.controller.home;

import com.iustu.entity.BlogType;
import com.iustu.entity.custom.BlogTypeCustom;
import com.iustu.service.BlogService;
import com.iustu.service.BlogTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/articleType")
public class ArticleTypeController {

    @Autowired
    private BlogTypeService blogTypeService;

    @Autowired
    private BlogService blogService;

    @RequestMapping("/list")
    @ResponseBody
    public List<BlogTypeCustom> getArticleTypeList(){
        List<BlogTypeCustom> resultList = new ArrayList<>();
        List<BlogType> list = blogTypeService.getBlogTypeList();
        for(BlogType type : list){
            BlogTypeCustom typeCustom = new BlogTypeCustom();
            BeanUtils.copyProperties(type, typeCustom);
            typeCustom.setBlogCount(blogService.getBlogCountByTypeId(type.getId()));
            resultList.add(typeCustom);
        }
        return resultList;
    }

}
