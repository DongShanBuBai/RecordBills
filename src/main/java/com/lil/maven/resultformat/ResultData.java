package com.lil.maven.resultformat;

import com.lil.maven.resultformat.msgcode.MsgCode;
import lombok.Data;

/**
 * 自定义响应体
 * @Author:lil
 * @Date: 2022-02-26
 */
@Data
public class ResultData<T> {
    private int status;
    private String message;
    private String token;
    private T data;
    private long timestamp;

    public ResultData(){
        //获得的是自1970-1-01 00:00:00.000 到当前时刻的时间距离（时间戳）,单位为毫秒
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 泛型方法
     * 泛型类中的静态方法不能使用类的泛型，而应该将该方法定义为泛型方法
     * static方法优先进行加载
     * 就像静态方法中调用了非静态方法
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultData<T> success(T data){
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(MsgCode.MSG_CODE200.getCode());
        resultData.setMessage(MsgCode.MSG_CODE200.getMsg());
        resultData.setData(data);
        return resultData;
    }

    /**
     * @param data
     * @param token 登录验证成功生成的令牌
     * @param <T>
     * @return
     */
    public static <T> ResultData<T> success(T data,String token){
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(MsgCode.MSG_CODE200.getCode());
        resultData.setMessage(MsgCode.MSG_CODE200.getMsg());
        resultData.setToken(token);
        resultData.setData(data);
        return resultData;
    }

    /**
     * 请求失败时调用的方法
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ResultData<T> fail(int code, String msg) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(code);
        resultData.setMessage(msg);
        return resultData;
    }
}
