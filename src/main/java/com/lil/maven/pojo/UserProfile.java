package com.lil.maven.pojo;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    @ApiModelProperty(value = "用户头像地址")
    private String avatarUrl;
    @ApiModelProperty(value = "用户性别")
    private Integer gender;
    @ApiModelProperty(value = "用户所属国家")
    private String country;
    @ApiModelProperty(value = "用户所属省份")
    private String province;
    @ApiModelProperty(value = "用户所属城市")
    private String city;
    @ApiModelProperty(value = "用户账号创建时间")
    private Date createTime;
    @ApiModelProperty(value = "用户的手机号码")
    private String phoneNumber;
}
