package com.lil.maven.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author:lil
 * @Date: 2022-03-31
 */
@Data
public class Register {
    @ApiModelProperty(value = "用户名即手机号")
    private String userName;
    @ApiModelProperty(value = "用户密码")
    private String password;
    @ApiModelProperty(value = "手机验证码")
    private String verifyCode;
}
