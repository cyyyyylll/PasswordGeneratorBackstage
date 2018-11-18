package com.example.demo.service;

import com.example.demo.User;
import com.example.demo.Webadd;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangxiang on 2018/8/7.
 */
@Component
public interface WebaddService {
    Webadd saveWebadd(Webadd webadd);
    void delete(Integer id);
    List<Webadd> findAll();
    int updateWorderByWebadds(String worder, String webadds);

    //int updateMimaByWebaddsAndUser(String shuiji, String webadds, User user);

}
