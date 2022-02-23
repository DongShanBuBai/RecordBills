package com.lil.maven.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    @PostMapping("/doLogin")
    public Map doLogin(String phone, String password) {
        Map map = new HashMap();
        if ((phone.equals("10086") && password.equals("123456"))) {
            map.put("code", 200);
            map.put("result", "登录成功");
            System.out.println("登录成功");
        } else {
            map.put("result", "no");
        }
        return map;
    }
}
