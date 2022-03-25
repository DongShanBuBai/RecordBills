package com.lil.maven.responseformat;

import com.lil.maven.responseformat.msgcode.GenericCode;
import lombok.Data;

/**
 * 自定义响应体
 * @Author:lil
 * @Date: 2022-02-26
 */
@Data
public class RespondData<T> {
    private int status;
    private String message;
    private T data;
    private long timestamp;

    public RespondData(){
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
    public static <T> RespondData<T> success(T data){
        RespondData<T> respondData = new RespondData<>();
        respondData.setStatus(GenericCode.MSG_CODE200.getCode());
        respondData.setMessage(GenericCode.MSG_CODE200.getMsg());
        respondData.setData(data);
        return respondData;
    }

    /**
     * 请求失败时调用的方法
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> RespondData<T> fail(int code, String msg) {
        RespondData<T> respondData = new RespondData<>();
        respondData.setStatus(code);
        respondData.setMessage(msg);
        return respondData;
    }
}
