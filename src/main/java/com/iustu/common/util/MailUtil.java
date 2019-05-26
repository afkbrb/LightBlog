package com.iustu.common.util;

import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 发送邮件
 * 相关属性需在resource/mail.properties中配置
 */
public class MailUtil {

    private static final String PROTOCOL;
    private static final String HOST;
    private static final String PORT;
    private static final String FROM;
    private static final String PASSWORD;
    private static final String TO;
    private static final String DEBUG;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("resource/mail");
        PROTOCOL = bundle.getString("mail.protocol");
        HOST = bundle.getString("mail.host");
        PORT = bundle.getString("mail.port");
        PASSWORD = bundle.getString("mail.password");
        FROM = bundle.getString("mail.from");
        TO = bundle.getString("mail.to");
        String debug = bundle.getString("mail.debug");
        // 默认开启debug模式
        if( debug == null || debug.equals("")) {
            DEBUG = "true";
        }else {
            DEBUG = debug;
        }
    }

    /**
     * 发送邮件（同步）
     * @param subject 主题
     * @param content 内容
     * @throws MessagingException
     */
    public static void sendMail(String subject, String content) throws MessagingException {

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", PROTOCOL);// 连接协议
        properties.put("mail.smtp.host", HOST);// 主机名
        properties.put("mail.smtp.port", PORT);// 端口号
        properties.put("mail.debug", DEBUG);// 设置是否显示debug信息 true 会在控制台显示相关信息
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
        // 得到回话对象
        Session session = Session.getInstance(properties);
        // 获取邮件对象
        Message message = new MimeMessage(session);
        // 设置发件人邮箱地址
        message.setFrom(new InternetAddress(FROM));
        // 设置收件人邮箱地址
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));//一个收件人
        // 设置邮件标题
        message.setSubject(subject);
        // 设置邮件内容
        message.setText(content);
        // 得到邮差对象
        Transport transport = session.getTransport();
        // 连接自己的邮箱账户
        transport.connect(FROM, PASSWORD);// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    /**
     * 发送邮件（异步），可用于提高请求响应速度
     * @param subject 主题
     * @param content 内容
     */
    public static void sendMailAsynchronously(String subject, String content){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendMail(subject, content);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
