package com.example.demo.controller;

import com.example.demo.User;
import com.example.demo.repository.UserRepository;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.io.File;
import java.io.*;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.example.demo.Webadd;

import com.example.demo.repository.WebRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.WebaddService;


import org.springframework.web.bind.annotation.*;


/**
 * Created by wangxiang on 2018/8/6.
 */
@RestController
@RequestMapping("/user")
@EnableSwagger2
@Transactional
public class UserController extends HttpServlet {

    @Autowired
    UserRepository userRepository;
    @Autowired
    WebRepository webRepository;
    @Autowired
    UserService userService;
    @Autowired
    WebaddService webaddService;

    User user1;
    Webadd webadd;


    //@RequestMapping(value = "/addweb", method = RequestMethod.POST)
    //public Webadd addweb(@RequestParam(value = "order") String worder,
      //                   @RequestParam(value = "webadds") String webadds) {
        //Webadd webadd = new Webadd();
       // webadd.setWorder(worder);
        //webadd.setUser(user1);
        //webadd.setWebadd(webadds);
        //webRepository.save(webadd);
        //return webadd;
    //}

    @RequestMapping(value = "/savemima", method = RequestMethod.POST)
    public String addmiam(@RequestParam(value = "mima") String mima) {
        webadd.setMima(mima);
        return "true";
    }


    @RequestMapping(value = "/updatepass", method = RequestMethod.POST)
    @Transactional
    public String updatepassword(HttpServletRequest request) {
        User user3 = new User();
        user3 = userRepository.findByName(request.getParameter("name"));
        String oldpass = user3.getPassword();
        String newpass = request.getParameter("password");
        if (newpass.equals(oldpass)) {
            return "false";
        } else {
            user3.setPassword(request.getParameter("password"));
            return "true";
        }
    }
}