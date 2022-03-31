package com.lil.maven.dao.mapper;

import com.lil.maven.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author:lil
 * @Date: 2022-02-24
 */
@Mapper
@Repository
public interface UserMapper {
//    List<User> queryAll();
    User queryByUserName(String userName);   //通过用户名即手机号进行查询
    User  queryByWeChatOpenId(String weChatOpenId);   //通过微信openid进行查询


    void insertAtOpenId(User user);     //第一次登陆，插入微信openID
    void updateUserLastLoginTime(User user);

    boolean insertViaPhone(User user);
}
