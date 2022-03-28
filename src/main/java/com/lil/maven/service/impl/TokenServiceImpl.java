package com.lil.maven.service.impl;

import com.lil.maven.Utils.RedisUtil;
import com.lil.maven.responseformat.RespondData;
import com.lil.maven.responseformat.exception.TokenException;
import com.lil.maven.service.TokenService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author:lil
 * @Date: 2022-03-04
 */
@Service
public class TokenServiceImpl implements TokenService {
    private final byte[] SECRET = "KdDFG2joh8JhsBojfojsLrKpuMCidYTcieSGhohi8jzXmkFmkclCkdjplaWjopeuMB".getBytes();
    private final long EXPIRE_TIME = 1000 * 60;  //token到期时间
    private Map<String,Object> map = new HashMap<>();
    @Autowired
    private RedisUtil redisUtil;
    Logger logger = LogManager.getLogger(TokenServiceImpl.class);

    /**
     *
     * @param userId
     * @return
     */
    @Override
    public String buildToken(Integer userId){

        try{
            MACSigner macSigner = new MACSigner(SECRET);

            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject("")
                    .issuer("")
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                    .claim("USERID",userId)
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256),jwtClaimsSet);
            signedJWT.sign(macSigner);

            String token = signedJWT.serialize();
            String key = "login:token"+userId;
            if (redisUtil.set(key,token,1L, TimeUnit.HOURS)){
                logger.info("用户id--->[{}]的token已经成功缓存到redis中",userId);
            }else{
                logger.warn("用户id--->[{}]的token缓存失败",userId);
            }
            return token;
        }catch(KeyLengthException e){
            e.printStackTrace();
        } catch (JOSEException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查token是否合法
     * @param token
     * @return
     */
    @Override
    public RespondData<Map> verifyToken(String token){
        try{
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier jwsVerifier = new MACVerifier(SECRET);

            if (!signedJWT.verify(jwsVerifier)){
                logger.error("token无效！");
                return null;
            }

            //从token中获取userId
            Integer userId = signedJWT.getJWTClaimsSet().getIntegerClaim("USERID");

            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (new Date().after(expirationTime)){
                if (!isTokenRealExpird(userId,token)){
                    //该token在redis中未过期,创建新的token
                    String newToken = buildToken(userId);
                    map.put("token",newToken);
                }else{
                    logger.error("token已经彻底过期！");
                    return null;
                }
            }

            if (Objects.isNull(userId)){
                logger.error("userId不存在！");
                return null;
            }

            map.put("userId",userId);
            return RespondData.success(map);
        }catch(ParseException e){
            e.printStackTrace();
        }catch (JOSEException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 到redis中查询token是否存在
     * @param userId
     * @param token
     * @return
     */
    @Override
    public boolean isTokenRealExpird(Integer userId,String token){
        String key = "login:token"+userId;
        String value = redisUtil.get(key).toString();
        if (token.equals(value)){
            //在redis中存在用户的token
            return false;
        }
        return true;
    }
}
