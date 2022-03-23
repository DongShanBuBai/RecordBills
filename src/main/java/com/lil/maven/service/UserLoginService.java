package com.lil.maven.service;

import com.alibaba.fastjson.JSONObject;
import com.lil.maven.pojo.User;

/**
 * @Author:lil
 * @Date: 2022-02-25
 */
public interface UserLoginService {
    JSONObject weChatUserLoginService(String code);
//    User normalUserLoginService(String username,String passord);
    void addUserAndProfile(User user, JSONObject jsonObject);
}
