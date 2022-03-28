package com.lil.maven.responseformat.msgcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回token验证的状态码信息
 * @Author:lil
 * @Date: 2022-03-23
 */
@Getter
@AllArgsConstructor
public enum TokenCode {
    MSG_CODE(200,"token"),

    MSG_CODE400(400,"请求错误，验证信息为空"),
    MSG_CODE401(401,"请求已被拦截，请进行登录"),
    MSG_CODE403(403,"已拦截，验证信息已过期，请进行登录"),
    MSG_CODE404(404,"访问资源不存在"),

    MSG_CODE20001(20001,"过期token已经更新");

    private Integer code;
    private String msg;
}
