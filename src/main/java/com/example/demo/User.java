package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by wangxiang on 2018/8/6.
 */
@Entity
@Table(name = "user")
public class User  implements Serializable {
    @Id

    @GeneratedValue
    private Integer id;

    private String name;

    private String password;

    private String mail;

    private String photo;


    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private Set<Webadd> webs;

    public User() {
        super();
    }


    public void setName(String name) {this.name=name;}

    public void setId(Integer id) { this.id = id; }

    public void setPhoto(String photo) { this.photo = photo;}

    public  String getPhoto(){ return photo; }

    public Integer getId() { return id; }

    public void setPassword(String password){ this.password = password;}


    public String getPassword(){ return password; }

    public String getName() {
        return name;
    }

    public String getMail(){
        return mail;
    }

    public void setMail(String mail){
        this.mail=mail;
    }


    public Set<Webadd> getWebadds(){
        return webs;
    }
    public void setWebadds(Set<Webadd> webadds){
        this.webs = webadds;
    }

}
