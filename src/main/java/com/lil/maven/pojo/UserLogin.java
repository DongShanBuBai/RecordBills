package com.lil.maven.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于mapper数据库连接测试
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserLogin {
    private int userid;
    private String username;
    private String password;
}

