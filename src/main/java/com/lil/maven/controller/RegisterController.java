package com.lil.maven.controller;

import com.lil.maven.pojo.User;
import com.lil.maven.responseformat.RespondData;
import com.lil.maven.responseformat.msgcode.GenericCode;
import com.lil.maven.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author:lil
 * @Date: 2022-03-29
 */
@RestController
public class RegisterController {
    @Autowired
    UserLoginService userLoginService;

    /**
     * @param userName 用户名，即手机号
     * @param password 用户密码
     * @param verifyCode 短信验证码
     * @return
     */
    public RespondData<String> phoneRegister(String userName,String password,String verifyCode){
        //这里应该验证短信验证码
        User user = new User();
        user.setUserName(userName);
        user.setUserPassword(password);
        user.setLastLoginTime(new Date());
        boolean result = userLoginService.register(user);
        if (result){
            return RespondData.success("注册成功");
        }
        return RespondData.fail(GenericCode.MSG_CODE403.getCode(),"注册失败");
    }
}
