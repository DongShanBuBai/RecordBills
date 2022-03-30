package com.lil.maven.pojo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author:lil
 * @Date: 2022-02-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "用户名，即手机号")
    private String userName;
    @ApiModelProperty(value = "用户密码")
    private String userPassword;
    @ApiModelProperty(value = "微信用户的openid")
    private String userWechatOpenid;
    @ApiModelProperty(value = "用户最近登录时间")
    private Date lastLoginTime;
}
