package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.example.demo.until.AES;
import com.example.demo.User;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WebRepository;
import com.example.demo.until.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping("/email")
public class MailController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebRepository webRepository;


    @Autowired
    private JavaMailSender jms;

    @Value("${spring.mail.username}")
    private String from;

    @Qualifier("templateEngine")
    @Autowired
    private TemplateEngine templateEngine;

    private String vercode;
    LocalDateTime dateTime;





    @RequestMapping(value = "/sendTemplateEmail",method = RequestMethod.POST)
    public String sendTemplateEmail(HttpServletRequest request) throws Exception {
        MimeMessage message = null;
        User user=new User();
        String key="1100101101011101010101";
        String scode= RandomUtil.getRandomString(4);
        user=userRepository.findByName(request.getParameter("name"));
        if (user==null){
            return "userError";
        }
        String mailpath =user.getMail();
        mailpath= AES.decrypt(key,mailpath);
        message = jms.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(mailpath); // 接收地址
        helper.setSubject("密码找回"); // 标题
        // 处理邮件模板
        Context context = new Context();
        context.setVariable("scode",scode);
        String template = templateEngine.process("emailTemplate", context);
        helper.setText(template, true);
        jms.send(message);
        vercode=null;
        dateTime=null;
        dateTime= LocalDateTime.now();
        vercode=scode;

        return "true";

    }


//    @RequestMapping(value = "/sendWebTemplateEmail",method = RequestMethod.POST)
//    public String sendWebTemplateEmail(HttpServletRequest request) throws Exception {
//        MimeMessage message = null;
//        User user=new User();
//        Webadd webadd= new Webadd();
//        String key="1100101101011101010101";
//        scode= RandomUtil.getRandomString(4);
//        String webadds = request.getParameter("webadds");
//        webadd = webRepository.findByWebadds(webadds);
//        user=webadd.getUser();
//        if (user==null){
//            return "userError";
//        }
//        String mailpath =user.getMail();
//        mailpath= AES.decrypt(key,mailpath);
//
//        try {
//            message = jms.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom(from);
//            helper.setTo(mailpath); // 接收地址
//            helper.setSubject("密码找回"); // 标题
//            // 处理邮件模板
//            Context context = new Context();
//            context.setVariable("scode",scode);
//            String template = templateEngine.process("emailTemplate", context);
//            helper.setText(template, true);
//            jms.send(message);
//            HttpSession session = request.getSession(true);
//            session.removeAttribute("verCode");
//            session.removeAttribute("codeTime");
//            session.setAttribute("verCode",scode.toLowerCase());
//            session.setAttribute("codeTime", LocalDateTime.now());
//            session.setAttribute("mima");
//
//            return "true";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return e.getMessage();
//        }
//    }
    @RequestMapping(value = "/yz",method = RequestMethod.POST)
    private String yz(HttpServletRequest request,HttpSession session) {
        String yzcode = request.getParameter("code");
        System.out.println(yzcode);
        System.out.println(vercode);
        System.out.println(dateTime);
        if (null == vercode) {
            return "验证码已失效";
        }
        LocalDateTime localDateTime=dateTime;
        long past = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        if ((now - past)/1000/60>2) {
            //session.removeAttribute("verCode");
            return "TimeError";
        } else {
            if (yzcode.equals(vercode)) {
                return "true";
            } else {
                return "false";
            }
        }
    }
}