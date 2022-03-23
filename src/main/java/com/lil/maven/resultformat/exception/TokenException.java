package com.lil.maven.resultformat.exception;

/**
 * @Author:lil
 * @Date: 2022-03-08
 */
public class TokenException extends RuntimeException {
    private String code;
    private String msg;

    public TokenException(String code,String msg){
        super(msg);
        this.code = code;
    }
}
