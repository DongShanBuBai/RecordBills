package com.lil.maven.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lil.maven.dao.mapper.UserMapper;
import com.lil.maven.dao.mapper.UserProfileMapper;
import com.lil.maven.pojo.User;
import com.lil.maven.pojo.UserProfile;
import com.lil.maven.pojo.WeChatUserInfo;
import com.lil.maven.resultformat.msgcode.MsgCode;
import com.lil.maven.resultformat.ResultData;
import com.lil.maven.service.TokenService;
import com.lil.maven.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author:lil
 * @Date: 2022-02-23
 */
@RestController
@RequestMapping("/login")
public class UserLoginController {
    @Autowired
    UserLoginService userLoginService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserProfileMapper userProfileMapper;
    @Autowired
    TokenService tokenService;

    /**
     * 通过RequestBody将json数据进行反序列化得到weChatUserInfo，code为参数调用weChatUserLoginService()
     * 方法访问微信接口返回JSONObject数据，进行是否第一次登陆判断（第一次登陆插入数据），返回UserProfile
     * 类型的data而非直接返回User类型
     * @param weChatUserInfo 请求参数
     * @return 返回ResultData响应体
     */
    @PostMapping("/weChatLogin")
    public ResultData<UserProfile> weChatLogin(@RequestBody WeChatUserInfo weChatUserInfo){
        if (weChatUserInfo.getCode() == null || weChatUserInfo.getRawData() == null){
            return ResultData.fail(MsgCode.MSG_CODE10001.getCode(),MsgCode.MSG_CODE10001.getMsg());
        }
        try{
            JSONObject jsonObjectWithOpenId = userLoginService.weChatUserLoginService(weChatUserInfo.getCode());
            String openId = jsonObjectWithOpenId.getString("openid");
            //微信非敏感信息
            JSONObject jsonObjectWithRawData = JSON.parseObject(weChatUserInfo.getRawData());

            User user = userMapper.queryByWeChatOpenId(openId);
            if (user == null){
                //不存在，第一次登陆，新建用户信息
                user = new User();
                user.setUserWechatOpenid(openId);
                user.setLastLoginTime(new Date());
                userLoginService.addUserAndProfile(user,jsonObjectWithRawData);
            }else{
                //不是第一次登陆，修改最后登陆时间
                user.setLastLoginTime(new Date());
                userMapper.updateUserLastLoginTime(user);
            }
            //ResultData响应体中将返回UserProfile类型的data
            UserProfile userProfile = userProfileMapper.queryAllByUserId(user);
//            return ResultData.success(user);
            String token = tokenService.buildToken(userProfile.getUserId());
            ResultData<UserProfile> resultData = ResultData.success(userProfile,token);
            System.out.println(resultData);
            return resultData;
        }catch(Exception e){
            e.printStackTrace();
            return ResultData.fail(MsgCode.MSG_CODE500.getCode(),MsgCode.MSG_CODE500.getMsg());
        }
    }
}

