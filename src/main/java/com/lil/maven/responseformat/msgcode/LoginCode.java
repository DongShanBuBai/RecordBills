package com.lil.maven.responseformat.msgcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author:lil
 * @Date: 2022-03-29
 */
@Getter
@AllArgsConstructor
public enum LoginCode {
    MSG_CODE200(200,"请求成功"),
    MSG_CODE403(403,"已拒绝该请求");
    private Integer code;
    private String msg;
}
