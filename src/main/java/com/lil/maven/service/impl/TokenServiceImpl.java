package com.lil.maven.service.impl;

import com.lil.maven.resultformat.exception.TokenException;
import com.lil.maven.service.TokenService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author:lil
 * @Date: 2022-03-04
 */
@Service
public class TokenServiceImpl implements TokenService {
    private final byte[] SECRET = "KdDFG2joh8JhsBojfojsLrKpuMCidYTcieSGhohi8jzXmkFmkclCkdjplaWjopeuMB".getBytes();
    private final long EXPIRE_TIME = 1000 * 60;  //token到期时间

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
            return token;
        }catch(KeyLengthException e){
            e.printStackTrace();
        } catch (JOSEException e){
            e.printStackTrace();
        }
        return null;
    }

    public Integer verifyToken(String token){
        try{
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier jwsVerifier = new MACVerifier(SECRET);

            if (!signedJWT.verify(jwsVerifier)){
                throw new TokenException("-1","token无效");
            }
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (new Date().after(expirationTime)){
                throw new TokenException("-2","token已过期");
            }
            Integer userId = signedJWT.getJWTClaimsSet().getIntegerClaim("USERID");
            if (Objects.isNull(userId)){
                throw new TokenException("-3","userId为空");
            }
            return userId;
        }catch(ParseException e){
            e.printStackTrace();
        }catch (JOSEException e){
            e.printStackTrace();
        }
        return null;
    }
}
