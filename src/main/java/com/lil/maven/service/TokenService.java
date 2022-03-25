package com.lil.maven.service;

import com.lil.maven.responseformat.RespondData;

/**
 * @Author:lil
 * @Date: 2022-03-04
 */
public interface TokenService {
    String buildToken(Integer userId);
    RespondData verifyToken(String token);
}
