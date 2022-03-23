package com.lil.maven.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小程序用户信息封装类
 * @Author:lil
 * @Date: 2022-02-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatUserInfo {
    private String code;
    //非敏感信息
    private String rawData;
    //签名信息
    private String signature;
    //加密秘钥
    private String iv;
}
