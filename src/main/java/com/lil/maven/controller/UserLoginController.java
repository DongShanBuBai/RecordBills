package com.lil.maven.controller;

import com.lil.maven.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:lil
 * @Date: 2022-02-23
 */
@RestController
@RequestMapping("/login")
public class UserLoginController {
    @Autowired
    UserLoginService userLoginService;
    @PostMapping("/weChatLogin")
    public void weChatLogin(String code){
        System.out.println(code);
        userLoginService.weChatUserLoginService(code);
    }
}

