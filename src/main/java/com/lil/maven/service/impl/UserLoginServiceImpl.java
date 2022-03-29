package com.lil.maven.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lil.maven.dao.mapper.UserMapper;
import com.lil.maven.dao.mapper.UserProfileMapper;
import com.lil.maven.Utils.UrlUtil;
import com.lil.maven.pojo.User;
import com.lil.maven.pojo.UserProfile;
import com.lil.maven.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:lil
 * @Date: 2022-02-25
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserProfileMapper userProfileMapper;

    /**
     * 通过code访问微信的codesession接口返回携带openid的JSONObject
     * @param code
     * @return
     */
    @Override
    public JSONObject weChatUserLoginService(String code){
        //微信秘钥
        String weChatSecret = "";
        //微信appid
        String weChatAppId = "";
        String codeSession2Url = "https://api.weixin.qq.com/sns/jscode2session";

        Map<String,String> requestForCodeSessionURLParam = new HashMap<String,String>();

        requestForCodeSessionURLParam.put("appid",weChatAppId);
        requestForCodeSessionURLParam.put("secret",weChatSecret);
        requestForCodeSessionURLParam.put("js_code",code);
        requestForCodeSessionURLParam.put("grant_type","authorization_code");

        JSONObject jsonObject = JSON.parseObject(UrlUtil.sendPost(codeSession2Url,requestForCodeSessionURLParam));
        return jsonObject;
    }

    /*@Override
    public User normalUserLoginService(String username, String passord){

    }*/

    /**
     * @param user
     * @param jsonObject
     */
    @Transactional
    public void addUserAndProfile(User user, JSONObject jsonObject){
        UserProfile userProfile = new UserProfile();

        //向user表中插入微信用户的openid
        userMapper.insertAtOpenId(user);

        Integer userId = user.getUserId();
        String nickName = jsonObject.getString("nickName");
        String avatarUrl = jsonObject.getString("avatarUrl");
        Integer gender = jsonObject.getInteger("gender");
        String country = jsonObject.getString("country");
        String province = jsonObject.getString("province");
        String city = jsonObject.getString("city");
        Date createTime = new Date();
        String phoneNumber = jsonObject.getString("phoneNumber");

        userProfile.setUserId(userId);
        userProfile.setNickName(nickName);
        userProfile.setAvatarUrl(avatarUrl);
        userProfile.setGender(gender);
        userProfile.setCountry(country);
        userProfile.setProvince(province);
        userProfile.setCity(city);
        userProfile.setCreateTime(createTime);
        userProfile.setPhoneNumber(phoneNumber);

        userProfileMapper.insertAtAll(userProfile);


    }
}
