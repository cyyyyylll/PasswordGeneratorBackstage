package com.example.demo.repository;

import com.example.demo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by wangxiang on 2018/8/6.
 */
public interface UserRepository extends JpaRepository<User, Integer>{

    User findByName(String name); //通过用户名查找User
    User findPassWordByName(String name);//通过用户名查找Password
    User findPhotoByName(String name);//通过用户名查找照片
    User findByPassword(String password);//通过Password查找User
    User findByMail(String mail);//通过邮件地址查找User

    void deleteByName(String name);//通过用户名删除User
    void deleteById(Integer id);//通过Id删除User

    User save(User user);//保存User


    @Modifying
    @Transactional
    @Query(value = "update User c set c.password = :password where c.name = :name",nativeQuery = true)
    int updatePasswordByName(@Param("name") String name, @Param("password") String password);//通过用户名更新password

}
