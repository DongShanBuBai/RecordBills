package com.lil.maven.Utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 用于发送向手机号发送短信验证码
 * 以及拉取回复内容
 * @Author:lil
 * @Date: 2022-03-30
 */
@Component
public class SendVerifyCode {
    private int appId = 0;
    private String appKey = "";
    private int templateId = 0;
    private String smsSign = "";

    Logger logger = LogManager.getLogger(SendVerifyCode.class);

    /**
     * 向单个手机号发送短信验证码
     * @return
     */
    public boolean sendCode(String verifyCode,String expireTime,String phoneNumber){
        try{
            String[] params = {verifyCode,expireTime};
            SmsSingleSender ssender = new SmsSingleSender(appId,appKey);
            SmsSingleSenderResult result = ssender.sendWithParam("86",phoneNumber,templateId,params,smsSign,"","");
            logger.info(result);
            if(result.result == 0){
                return true;
            }
        }catch(HTTPException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        }
        return false;
    }
}
