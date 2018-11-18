package com.example.demo.repository;

import com.example.demo.User;
import com.example.demo.Webadd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

/**
 * Created by wangxiang on 2018/8/6.
 */
public interface WebRepository extends JpaRepository<Webadd,Integer>{
    Webadd findByWebaddsAndUser(String webadds,User user);
    List<Webadd> findByWebaddsContaining(String wabadds);
    Webadd findMimaByWebaddsAndUser(String webadds, User user);
    Webadd findByWebadds(String webadds);
    Webadd save(Webadd webadd);

    List<Webadd> findAllByUser(User user);

    @Modifying
    @Transactional
    @Query(value = "update webadd  set worder= :worder where webadds= :webadds",nativeQuery = true)
    int updateWorderByWebadds(@Param("worder") String order, @Param("webadds") String webs);
    void deleteByWebaddsAndUser(String webadds,User user);

    @Modifying
    @Transactional
    @Query(value = "update webadd set mima= :mima where webadds= :webadds AND user_id= :user_id",nativeQuery = true)
    int updateMimaByWebaddsAndUser(@Param("mima") String shuiji,@Param("webadds") String webadds,@Param("user_id") User user);
}
