package com.example.demo.service;

import com.example.demo.Webadd;
import com.example.demo.repository.WebRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by wangxiang on 2018/8/7.
 */
@Service
@Transactional
public class WebaddServiceimpl implements WebaddService {
    @Autowired
    WebRepository webRepository;

    public Webadd saveWebadd(Webadd webadd) {
        return webRepository.save(webadd);
    }





    public void delete( Integer id) {
        webRepository.deleteById(id );
    }

    public List<Webadd> findAll() {
        return webRepository.findAll();
    }


    public int updateWorderByWebadds(String worder, String webadds) {
        return webRepository.updateWorderByWebadds(worder, webadds);
    }




}
