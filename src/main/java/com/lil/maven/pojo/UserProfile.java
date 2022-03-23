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
public class UserProfile {
    private Integer userId;
    private String nickName;
    private String avatarUrl;
    private Integer gender;
    private String country;
    private String province;
    private String city;
    private Date createTime;
    private String phoneNumber;
}
