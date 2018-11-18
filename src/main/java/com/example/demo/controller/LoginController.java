package com.example.demo.controller;

import com.example.demo.User;
import com.example.demo.repository.UserRepository;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

/**
 * Created by wangxiang on 2018/8/20.
 */
@RestController
@EnableSwagger2
@Transactional
public class LoginController extends HttpServlet{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/adduser",method = RequestMethod.POST)
    public  int addUser(HttpServletRequest request) throws Exception {
        User user1=new User();
        User user2=new User();
        User user3=new User();
        user2=userRepository.findByName(request.getParameter("name"));
        if (user2!=null){
            return 2;//用户名已存在
        }
        user3=userRepository.findByMail(request.getParameter("mail"));
        if(user3!=null){
            return 3;//邮箱已经被注册；
        }
        user1.setName(request.getParameter("name"));
        user1.setPassword(request.getParameter("password"));
        user1.setMail(request.getParameter("mail"));
        userRepository.save(user1);

        if (user1.getName().equals("")){
            return 0;
        }else {
            return 1;
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response)throws ServletException {
        String username = request.getParameter("name");
        User user=new User();
        user = userRepository.findPassWordByName(username);
        if(user==null){
            return "nameError";
        }
        HttpSession session = request.getSession(true);
        User user1=userRepository.findPassWordByName(username);
        session.setAttribute("username",username);
        String oldpassword = user1.getPassword();
        String newpassword = request.getParameter("password");
        if (oldpassword.equals(newpassword)) {
            return "true";
        } else {
            return "passwordError";
        }
    }

}
