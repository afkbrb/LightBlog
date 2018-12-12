package com.iustu.controller.admin;

import com.iustu.common.util.UploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminUploadController {

    @Value("${UPLOAD_PATH}")
    private String UPLOAD_PATH;

    @RequestMapping("/uploadImg")
    @ResponseBody
    public Map uploadImage(@RequestParam(value = "editormd-image-file", required = true) MultipartFile myFileName, HttpServletRequest request) {
        String imageUrl = "";
        Map res = new HashMap();
        try {
            imageUrl =  UploadUtil.uploadFile(UPLOAD_PATH, myFileName, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        res.put("url", imageUrl);
        res.put("success", 1);
        res.put("message", "upload success!");

        return res;
    }
}
