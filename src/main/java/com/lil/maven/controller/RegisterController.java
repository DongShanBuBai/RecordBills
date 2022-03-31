package com.lil.maven.controller;

import com.lil.maven.Utils.RedisUtil;
import com.lil.maven.dao.mapper.UserMapper;
import com.lil.maven.pojo.Register;
import com.lil.maven.pojo.User;
import com.lil.maven.responseformat.RespondData;
import com.lil.maven.responseformat.msgcode.GenericCode;
import com.lil.maven.service.UserLoginService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserMapper userMapper;

    /**
     * 用于手机号注册
     * @param register
     * @return
     */
    @ApiOperation(value = "手机号用户注册")
    @PostMapping("/register")
    public RespondData<String> phoneRegister(@RequestBody Register register){
        String userName = register.getUserName();
        String password = register.getPassword();
        String verifyCode = register.getVerifyCode();

        //判断手机号是否已经注册过
        if (userMapper.queryByUserName(userName) != null){
            //该手机号已经注册过
            return RespondData.fail(GenericCode.MSG_CODE403.getCode(),"用户已存在");
        }

        //redis中查询验证码与用户输入的验证码进行比较
        if (!(redisUtil.exists(userName) && redisUtil.get(userName).equals(verifyCode))){
            return RespondData.fail(GenericCode.MSG_CODE403.getCode(),"验证码错误");
        }

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
