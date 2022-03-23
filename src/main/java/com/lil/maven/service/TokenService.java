package com.lil.maven.service;

/**
 * @Author:lil
 * @Date: 2022-03-04
 */
public interface TokenService {
    String buildToken(Integer userId);
    Integer verifyToken(String token);
}
