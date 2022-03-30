package com.lil.maven.service;

import com.alibaba.fastjson.JSONObject;
import com.lil.maven.pojo.User;
import com.lil.maven.pojo.UserProfile;

/**
 * @Author:lil
 * @Date: 2022-02-25
 */
public interface UserLoginService {
    JSONObject weChatUserLoginService(String code);
    UserProfile getUserProfile(User user);
    boolean register(User user);
    void addUserAndProfile(User user, JSONObject jsonObject);
}
