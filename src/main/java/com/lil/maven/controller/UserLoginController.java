package com.lil.maven.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lil.maven.common.annotation.LoginCheck;
import com.lil.maven.dao.mapper.UserMapper;
import com.lil.maven.dao.mapper.UserProfileMapper;
import com.lil.maven.pojo.User;
import com.lil.maven.pojo.UserProfile;
import com.lil.maven.pojo.WeChatUserInfo;
import com.lil.maven.responseformat.msgcode.GenericCode;
import com.lil.maven.responseformat.RespondData;
import com.lil.maven.service.TokenService;
import com.lil.maven.service.UserLoginService;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    Logger logger = LogManager.getLogger(UserLoginController.class);

    /**
     * 通过RequestBody将json数据进行反序列化得到weChatUserInfo，code为参数调用weChatUserLoginService()
     * 方法访问微信接口返回JSONObject数据，进行是否第一次登陆判断（第一次登陆插入数据），返回UserProfile
     * 类型的data而非直接返回User类型
     * @param weChatUserInfo 请求参数
     * @return 返回ResultData响应体
     */
    @ApiOperation(value = "微信用户登录接口")
    @PostMapping("/weChatLogin")
    public RespondData<Map> weChatLogin(@RequestBody WeChatUserInfo weChatUserInfo){
        if (weChatUserInfo.getCode() == null || weChatUserInfo.getRawData() == null){
            return RespondData.fail(GenericCode.MSG_CODE10001.getCode(), GenericCode.MSG_CODE10001.getMsg());
        }
        try{
            JSONObject jsonObjectWithOpenId = userLoginService.weChatUserLoginService(weChatUserInfo.getCode());
            String openId = jsonObjectWithOpenId.getString("openid");
            //微信非敏感信息
            JSONObject jsonObjectWithRawData = JSON.parseObject(weChatUserInfo.getRawData());

            User user = userMapper.queryByWeChatOpenId(openId);
            if (user == null){
                //不存在，第一次登陆，新建用户信息
                logger.info("用户第一次登录");
                user = new User();
                user.setUserWechatOpenid(openId);
                user.setLastLoginTime(new Date());
                userLoginService.addUserAndProfile(user,jsonObjectWithRawData);
            }else{
                //不是第一次登陆，修改最后登陆时间
                logger.info("用户登录");
                user.setLastLoginTime(new Date());
                userMapper.updateUserLastLoginTime(user);
            }
            //ResultData响应体中将返回UserProfile类型的data
            UserProfile userProfile = userProfileMapper.queryAllByUserId(user);
//            return RespondData.success(user);
            String token = tokenService.buildToken(userProfile.getUserId());
            Map<String,Object> map = new HashMap<>();
            map.put("token",token);
            map.put("userData",userProfile);

            RespondData<Map> respondData = RespondData.success(map);
            System.out.println(respondData);
            return respondData;
        }catch(Exception e){
            e.printStackTrace();
            return RespondData.fail(GenericCode.MSG_CODE500.getCode(), GenericCode.MSG_CODE500.getMsg());
        }
    }

    /**
     * 使用手机号登录
     * @param paramUser
     * @return
     */
    @ApiOperation(value = "手机号用户登录")
    @PostMapping("/phoneLogin")
    public RespondData<Map> phoneLogin(@RequestBody User paramUser){
        User user = userMapper.queryByUserName(paramUser.getUserName());
        if (user == null){
            //用户不存在
            return RespondData.fail(GenericCode.MSG_CODE403.getCode(),"用户不存在");
        }
        boolean result = (paramUser.getUserPassword()).equals(user.getUserPassword());
        if (!result){
            //密码不匹配
            return RespondData.fail(GenericCode.MSG_CODE403.getCode(),"登陆失败");
        }
        UserProfile userProfile = userLoginService.getUserProfile(user);
        String token = tokenService.buildToken(user.getUserId());
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("userData",userProfile);
        return RespondData.success(map);
    }

    @LoginCheck
    @PostMapping("/tokenTest")
    public String tokenTest(@RequestAttribute("userId") Integer userId, @Nullable @RequestAttribute("newToken") String newToken){
        if (newToken != null){
            logger.info("token已经更新",newToken);
        }

        return userId.toString()+newToken;
    }
}

