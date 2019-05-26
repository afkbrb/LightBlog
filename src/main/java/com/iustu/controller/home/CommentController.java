package com.iustu.controller.home;

import com.iustu.common.pojo.MyResult;
import com.iustu.common.util.AvatarGeneratorUtil;
import com.iustu.common.util.IpAddressUtil;
import com.iustu.common.util.UploadUtil;
import com.iustu.entity.Blog;
import com.iustu.entity.Comment;
import com.iustu.service.BlogService;
import com.iustu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Value("${UPLOAD_PATH}")
    private String UPLOAD_PATH;

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public String addComment(Comment comment, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile, HttpServletRequest request) {

        String imagePath = "";

        //评价日期
        comment.setCommentDate(new Date());
        //未审核状态
        comment.setState(0);
        comment.setVisitorIp(IpAddressUtil.getIpAdrress(request));
        // 读者上传了头像
        if (imageFile != null) {
            try {
                imagePath = UploadUtil.uploadFile(UPLOAD_PATH, imageFile, request);
                comment.setVisitorAvatar(imagePath);
                commentService.insertComment(comment);
                return imagePath;
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
            // 如果没有头像则随机生成8-bit像素头像
        } else if (comment.getVisitorAvatar() == null || comment.getVisitorAvatar().equals("")) {
            imagePath = AvatarGeneratorUtil.generatorAvatar(UPLOAD_PATH, request);
            comment.setVisitorAvatar(imagePath);
            commentService.insertComment(comment);
            return imagePath;
            // 已经有头像了则直接插入即可
        } else {
            commentService.insertComment(comment);
            return comment.getVisitorAvatar();
        }
    }

    @RequestMapping("/get")
    @ResponseBody
    public MyResult getCommentList(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows, @RequestParam(value = "blogId", required = false) Integer blogId) {
        MyResult result = new MyResult();
        result.setRows(commentService.getCommentListByPageAndBlogId(rows * (page - 1), rows, blogId));
        result.setTotal(commentService.getPassedCommentCountByBlogId(blogId));
        return result;
    }
}
