package com.lil.maven.controller;

import com.lil.maven.Utils.RandomNum;
import com.lil.maven.Utils.RedisUtil;
import com.lil.maven.Utils.SendVerifyCode;
import com.lil.maven.pojo.User;
import com.lil.maven.responseformat.RespondData;
import com.lil.maven.responseformat.msgcode.GenericCode;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author:lil
 * @Date: 2022-03-31
 */
@RestController
public class GetVerifyCodeController {
    @Autowired
    private SendVerifyCode sendCode;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    RandomNum randomNum;

    Logger logger = LogManager.getLogger(GetVerifyCodeController.class);

    @ApiOperation(value = "向指定手机号发送短信验证码")
    @PostMapping("/getCode")
    public RespondData getVerifyCode(@RequestBody User user){
        String phoneNumber = user.getUserName();
        String verifyCode = randomNum.getRandomNum(5);
        //验证码过期时间
        Long expireTime = 5L;
        boolean redisResult = redisUtil.set(phoneNumber,verifyCode,expireTime, TimeUnit.MINUTES);
        if (redisResult){
            logger.info("验证码:{}在redis中缓存成功",verifyCode);
            boolean result = sendCode.sendCode(verifyCode,expireTime.toString(),phoneNumber);
            if (result){
                logger.info("验证码:{}发送成功",verifyCode);
                return RespondData.success("验证码发送成功");
            }else{
                redisUtil.remove(phoneNumber);
                logger.error("验证码发送失败");
            }
        }else{
            logger.error("验证码：{}在redis中缓存失败",verifyCode);
        }
        return RespondData.fail(GenericCode.MSG_CODE500.getCode(),"验证码发送失败");
    }
}
