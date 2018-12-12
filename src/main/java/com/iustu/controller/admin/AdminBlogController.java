package com.iustu.controller.admin;

import com.iustu.common.pojo.MyResult;
import com.iustu.common.util.UploadUtil;
import com.iustu.entity.Blog;
import com.iustu.entity.custom.BlogCustom;
import com.iustu.service.BlogService;
import com.iustu.service.BlogTypeService;
import com.iustu.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/blog", method = RequestMethod.POST)
public class AdminBlogController {

    @Value("${UPLOAD_PATH}")
    private String UPLOAD_PATH;

    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogTypeService blogTypeService;
    @Autowired
    private CommentService commentService;

    @RequestMapping("/list")
    @ResponseBody
    public MyResult getBlogList(@RequestParam(value="page", required=false) Integer page, @RequestParam(value="rows", required=false) Integer rows, @RequestParam(value="title", required=false) String title) {
        MyResult result = new MyResult();
        //请求参数中page从1开始
        List<Blog> list;
        if(title == null || title.equals("")){
            list = blogService.getBlogListByPage(rows * (page - 1), rows);
        }else{
            list = blogService.getBlogListByTitleAndPage(rows * (page - 1), rows, "%" + title + "%");
        }
        List<BlogCustom> resultList = new ArrayList<>();
        for(Blog blog : list){
            BlogCustom blogCustom = new BlogCustom();
            BeanUtils.copyProperties(blog, blogCustom);
            Integer typeId = blogCustom.getTypeId();
            String typeName = blogTypeService.getBlogTypeNameById(typeId);
            blogCustom.setBlogType(typeName);
            resultList.add(blogCustom);
        }
        result.setRows(resultList);
        if(title == null || title.equals("")){
            result.setTotal(blogService.getBlogCount());
        }else{
            result.setTotal(blogService.getBlogCountByTitle("%" + title + "%"));
        }
        return result;
    }

    @RequestMapping("/insert")
    @ResponseBody
    public String insertBlog(Blog blog, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile, HttpServletRequest request){

        //补全blog属性
        blog.setClickCount(0);
        blog.setLikeCount(0);
        blog.setReplyCount(0);
        blog.setCreateDate(new Date());
        blog.setUpdateDate(new Date());

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageName = null;
            try {
                imageName = UploadUtil.uploadFile(UPLOAD_PATH, imageFile, request);
            } catch (Exception e) {
                e.printStackTrace();
            }
            blog.setBlogImage(imageName);
        }

        blogService.insertBlog(blog);
        return "success";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String modifyBlog(Blog blog, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile, HttpServletRequest request){
        //不能改变如create_time的属性
        Blog blog1 = blogService.getBlogById(blog.getId());
        blog1.setTitle(blog.getTitle());
        blog1.setContent(blog.getContent());
        blog1.setTypeId(blog.getTypeId());
        blog1.setSummary(blog.getSummary());
        blog1.setUpdateDate(new Date());
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageName = null;
            try {
                imageName = UploadUtil.uploadFile(UPLOAD_PATH, imageFile, request);
            } catch (Exception e) {
                e.printStackTrace();
            }
            blog1.setBlogImage(imageName);
        }
        if(!(blog.getReprint() == null || blog.getReprint().equals(""))){
            blog1.setReprint(blog.getReprint());
        }
        blogService.updateBlog(blog1);
        return "success";
    }

    @RequestMapping("/delete/{ids}")
    @ResponseBody
    public String deleteBlogById(@PathVariable String ids){
        String[] strings = ids.split(",");
        for(String str : strings){
            int id = Integer.parseInt(str);
            commentService.deleteCommentByBlogId(id);
            blogService.deleteBlogById(id);
        }
        return "success";
    }

    @RequestMapping("/get/{blogId}")
    @ResponseBody
    public BlogCustom getBlogById(@PathVariable Integer blogId){
        BlogCustom blogCustom = new BlogCustom();
        Blog blog = blogService.getBlogById(blogId);
        BeanUtils.copyProperties(blog, blogCustom);
        blogCustom.setBlogType(blogTypeService.getBlogTypeNameById(blogCustom.getTypeId()));
        return blogCustom;
    }

}
