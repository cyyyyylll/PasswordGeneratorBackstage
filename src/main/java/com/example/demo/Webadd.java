package com.example.demo;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wangxiang on 2018/8/6.
 */
@Entity
@Table(name = "webadd")
public class Webadd implements Serializable {

    @Id

    @GeneratedValue
    private Integer id;

    private String mima;
    private String worder;
    private String webadds;
    private String shuiji;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;


    public Integer getId(){
        return id;
    }
    public String getShuiji(){ return shuiji; }
    public String getMima(){
        return mima;
    }
    public String getWorder(){
        return worder;
    }
    public String getWebadds(){
        return webadds;
    }
    public void setId(){
        this.id = id;
    }

    public void setShuiji(String shuiji){
        this.shuiji=shuiji;
    }

    public void setMima(String mima){
        this.mima=mima;
    }

    public void setWorder(String worder){
        this.worder=worder;
    }

    public void setWebadd(String webs){
        this.webadds=webs;
    }
    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user=user;
    }
}
