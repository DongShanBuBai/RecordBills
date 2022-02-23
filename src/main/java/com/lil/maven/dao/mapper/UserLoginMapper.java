package com.lil.maven.dao.mapper;


import com.lil.maven.pojo.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用于连接数据库测试
 */
@Mapper
@Repository
public interface UserLoginMapper {
    //查询
    List<UserLogin> queryAll();
    //添加数据
    int add(UserLogin userLogin);
    //根据用户名查询数据
    UserLogin queryByName(String username);
}
