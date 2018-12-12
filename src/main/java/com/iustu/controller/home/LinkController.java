package com.iustu.controller.home;

import com.iustu.entity.Link;
import com.iustu.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @RequestMapping("/list")
    @ResponseBody
    public List<Link> getLinkList(){
        return linkService.getLinkList();
    }
}
