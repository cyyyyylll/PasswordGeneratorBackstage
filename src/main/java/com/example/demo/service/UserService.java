package com.example.demo.service;

import com.example.demo.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Created by wangxiang on 2018/8/7.
 */
@Component
public interface UserService {
    User saveUser(User user);

    void deleteUser(Integer id);
    void deleteUserByName(String name);


    int updateUser(User user);
    int updatePasseordByName(String password, String name);

}
