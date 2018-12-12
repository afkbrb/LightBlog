package com.iustu.controller.admin;

import com.iustu.common.pojo.MyResult;
import com.iustu.entity.Link;
import com.iustu.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/link", method = RequestMethod.POST)
public class AdminLinkController {

    @Autowired
    private LinkService linkService;

    @RequestMapping("/insert")
    @ResponseBody
    public String insertLink(Link link){
        linkService.insertLink(link);
        return "success";
    }

    @RequestMapping("/delete/{ids}")
    @ResponseBody
    public String deleteLink(@PathVariable String ids){
        String[] strings = ids.split(",");
        for(String str : strings){
            int id = Integer.parseInt(str);
            linkService.deleteLinkById(id);
        }
        return "success";
    }

    @RequestMapping("/update/{id}")
    @ResponseBody
    public String updateLink(@PathVariable int id, Link link){
        link.setId(id);
        linkService.updateLink(link);
        return "success";
    }

    @RequestMapping("/list")
    @ResponseBody
    public MyResult getLinkListByPage(@RequestParam(value="page", required=false) Integer page, @RequestParam(value="rows", required=false) Integer rows){
        MyResult result = new MyResult();
        List<Link> list = linkService.getLinkListByPage(rows * (page - 1), rows);
        result.setRows(list);
        result.setTotal(linkService.getLinkCount());

        return result;

    }

}
