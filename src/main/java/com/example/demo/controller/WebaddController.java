package com.example.demo.controller;

import com.example.demo.until.AES;
import com.example.demo.until.Miuntil;
import com.example.demo.until.Getmima;
import com.example.demo.User;
import com.example.demo.Webadd;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WebRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by wangxiang on 2018/8/7.
 */
@RestController
@RequestMapping(value = "/webadd")
public class WebaddController {
    @Autowired
    WebRepository webRepository;


    @Autowired
    UserRepository userRepository;



    @RequestMapping(value = "/addweb",method = RequestMethod.POST)
    @Transactional
    public String addweb(HttpServletRequest request) throws Exception {
        String key="1100101101011101010101";
        String shuiji=Miuntil.getRandomString(6);
        String username = request.getParameter("name");
        User userw = new User();
        userw = userRepository.findByName(username);
        String photo = userw.getPhoto();
        System.out.println(userw.getName());//
        Webadd webadd = new Webadd();
        Getmima mima = new Getmima();
        String w=request.getParameter("webadds");
        //String we= AES.decrypt(key,w);
        String o=request.getParameter("worder");
        //String oe=AES.decrypt(key,o);
        String finalorder = (o+shuiji).toString();
        //String finalmima =mima.getmima(photo,finalorder,w);
        //System.out.println(finalmima);
        try{
            Webadd webadd1=new Webadd();
            webadd1 = webRepository.findByWebaddsAndUser(w,userw);

        if (webadd1!=null){
            return "cf";

        }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        try{
        //if (webadd==null){
            webadd.setWorder(o);
            webadd.setWebadd(w);
            webadd.setUser(userw);
            webadd.setShuiji(shuiji);
            webRepository.save(webadd);
            System.out.println(webadd.getMima());
            if (webadd.getWebadds().equals(null)){
                return webadd.getWebadds();
            //}else {
            //}
//        }else {
//            return "cf";
        }
        }catch (NullPointerException e){
        }
        return "true";
    }


    @RequestMapping(value = "/finweb",method = RequestMethod.GET)
    public String finweb(HttpServletRequest request){
        String username1 = request.getParameter("name");
        User user1 = new User();
        user1 = userRepository.findByName(username1);
        Integer id = user1.getId();
        String webadds = request.getParameter("webadds");
        Webadd webadd = webRepository.findMimaByWebaddsAndUser(webadds,user1);
        if (webadd.getMima().equals(null)){
            return "NULL";
        }else{
        return webadd.getMima();
        }
    }


    @RequestMapping(value = "/updatemima",method = RequestMethod.POST)
    @Transactional
    public String updatemima(HttpServletRequest request) throws Exception {
        String key ="1100101101011101010101";
        String username = request.getParameter("name");
        String webadds = request.getParameter("webadds");
        String webaddsde = AES.decrypt(key,webadds);
        String pass = request.getParameter("password");
        Webadd webadd= new Webadd();
        Webadd webadd1 = new Webadd();
        Getmima getmima = new Getmima();
        String shuiji = Miuntil.getRandomString(6);
        User user = new User();
        user = userRepository.findByName(username);
        webadd = webRepository.findByWebaddsAndUser(webadds, user);
        try {
            if (webadd.getWorder().equals(null)) {
                return "webNull";
            }
        }catch (Exception e){
                return "webNull";
        }
        String password = user.getPassword();
        try{
        if (pass.equals(password)) {

            String image = user.getPhoto();
            image = AES.decrypt(key,image);
            webadd1 = webRepository.findByWebaddsAndUser(webadds, user);
            webadd1.setShuiji(shuiji);
            String worder = webadd1.getWorder();
            String worderde = AES.decrypt(key, worder);
            String finalworder = (worderde + shuiji);
            String newmima = getmima.getmima(image,finalworder,webaddsde);
            newmima = newmima.substring(2,14);
            String aesmima = AES.encrypt(key, newmima);
            webRepository.updateMimaByWebaddsAndUser(aesmima,webadds,user);
            return aesmima;
        } else {
            return "false";
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "true";

    }

//    @RequestMapping(value = "/yzWebadd",method = RequestMethod.POST)
//    public String yzWebadd(HttpServletRequest request,HttpSession session) throws Exception{
//        String rpassword =request.getParameter("password");
//        Object spassword = session.getAttribute("password");
//        String mima = session.getAttribute("mima").toString();
//        if(rpassword.equals(spassword)){
//            return mima;
//        }else {
//            return "PasswordError";
//        }
//    }

    @RequestMapping(value = "/findallweb",method = RequestMethod.GET)
    public List<Webadd> webfinall(HttpServletRequest request) throws Exception{
        String username2 = request.getParameter("name");
        User user4 = new User();
        user4 = userRepository.findByName(username2);
        List<Webadd> webaddList = webRepository.findAllByUser(user4);
        return webaddList;
    }
    @RequestMapping(value = "/deleteweb",method = RequestMethod.POST)
    @Transactional
    public String deleteweb (HttpServletRequest request){
        String webadds=request.getParameter("webadds");
        String username3=request.getParameter("name");
        User user5=new User();
        user5=userRepository.findByName(username3);
        try{
        if(webRepository.findByWebaddsAndUser(webadds,user5).equals(null)){
            return "NULl webadds";
        }
        }catch (Exception e){
            return "NULl";
        }
        webRepository.deleteByWebaddsAndUser(webadds,user5);
        return "true";

    }

    //@RequestMapping(value = "/updateOrder/{webadds}",method = RequestMethod.POST)

    //public int updateOrder(@RequestParam String worder,
      //                     @PathVariable String webadds){
       // return webRepository.updateWorderByWebadds(worder,webadds);
    //}



    //@RequestMapping(value = "/findall",method = RequestMethod.GET)
    //public List<Webadd> findAll(){
      //  List<Webadd> webaddList = webRepository.findAll();
        //return webaddList;
    //}
}
