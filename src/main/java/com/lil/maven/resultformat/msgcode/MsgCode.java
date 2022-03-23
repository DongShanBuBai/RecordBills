package com.lil.maven.resultformat.msgcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义状态码
 * @Author:lil
 * @Date: 2022-02-26
 */
@Getter
@AllArgsConstructor
public enum MsgCode {
    MSG_CODE200(200,"请求成功"),
    MSG_CODE400(400,"客户端错误"),
    MSG_CODE403(403,"已拒绝该请求"),
    MSG_CODE404(404,"访问资源不存在"),
    MSG_CODE500(500,"服务器内部错误"),
    MSG_CODE501(501,"请求不支持"),

    MSG_CODE10001(10001,"请求参数出现错误");

    private Integer code;
    private String msg;
}
