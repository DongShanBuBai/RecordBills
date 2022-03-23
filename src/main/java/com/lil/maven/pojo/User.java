package com.lil.maven.pojo;

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
    private Integer userId;
    private String userName;
    private String userPassword;
    private String userWechatOpenid;
    private Date lastLoginTime;
}
