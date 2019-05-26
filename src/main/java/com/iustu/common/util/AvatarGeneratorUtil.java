package com.iustu.common.util;

import com.talanlabs.avatargenerator.Avatar;
import com.talanlabs.avatargenerator.eightbit.EightBitAvatar;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


public class AvatarGeneratorUtil {

    private static final String SUFFIX = ".png";
    private static Random random = new Random();
    private static Avatar avatar = EightBitAvatar.newMaleAvatarBuilder().build();

    /**
     * 随机生成头像
     * @param uploadPath
     * @param request
     * @return 生成的头像所在url路径
     */
    public static String generatorAvatar(String uploadPath, HttpServletRequest request) {
        String realName = UUID.randomUUID().toString() + SUFFIX;
        String realPath = request.getServletContext().getRealPath(uploadPath);
        Date date = new Date();
        String datePath = new SimpleDateFormat("yyyy/MM/dd").format(date);
        String path = realPath + "/" + datePath;
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        // 随机生成png图像到文件中
        avatar.createAsPngToFile(random.nextLong(), new File(path + "/" + realName));

        return request.getContextPath() + uploadPath + "/" + datePath + "/" + realName;
    }
}
