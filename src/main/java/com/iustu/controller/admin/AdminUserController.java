package com.iustu.controller.admin;

import com.iustu.common.util.CryptographyUtil;
import com.iustu.common.util.UploadUtil;
import com.iustu.entity.User;
import com.iustu.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AdminUserController {

    @Value("${UPLOAD_PATH}")
    private String UPLOAD_PATH;

    @Autowired
    private UserService userService;

    private final String SALT = "blog";

    @RequestMapping("/loginUser")
    public String login(User user, HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), CryptographyUtil.md5(user.getPassword(), SALT));
        try {
            subject.login(token);
            return "redirect:admin/main";
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorInfo", "用户名或密码错误");
        }
        return "admin/login";
    }

    @RequestMapping(value = "/admin/user/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(User userParam, @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile, HttpServletRequest request, HttpSession session) {
        User user = userService.getUserById(userParam.getId());
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String imageName = null;
            try {
                imageName = UploadUtil.uploadFile(UPLOAD_PATH, avatarFile, request);
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.setAvatar(imageName);
        }
        user.setProfile(userParam.getProfile());
        userService.updateUser(user);
        //更新session
        session.setAttribute("user", user);
        return "success";
    }

    @RequestMapping(value = "/admin/user/updatePassword/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String updatePassword(@PathVariable int id, @RequestParam("newPassword") String password) {
        User user = userService.getUserById(id);
        user.setPassword(CryptographyUtil.md5(password, SALT));
        userService.updateUser(user);
        return "success";
    }

    @RequestMapping("/admin/user/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/index";
    }

    @RequestMapping(value = "admin/user/{id}", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    @ResponseBody
    public String getUserProfile(@PathVariable Integer id){
        return userService.getUserById(id).getProfile();
    }

}
