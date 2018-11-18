package com.example.demo.service;

import com.example.demo.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by wangxiang on 2018/8/7.
 */
@Service

public class UserServiceimpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }


    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByName(String name) {
        userRepository.deleteByName(name);
    }

    public int updateUser(User user) {
        userRepository.deleteById(user.getId());
        userRepository.save(user);
        return 0;
    }


    public int updatePasseordByName(String password, String name) {
        return userRepository.updatePasswordByName(password,name);
    }

}
