package com.lil.maven.Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类用于简化redis操作
 * @Author:lil
 * @Date: 2022-03-26
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    private Logger logger = LogManager.getLogger(RedisUtil.class);

    /**
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key,Object value){
        boolean result = false;
        try{
            ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key,value);
            result = true;
        }catch(Exception e){
            logger.error(e);
        }
        return result;
    }

    /**
     * @param key
     * @param value
     * @param expireTime
     * @param timeUnit
     * @return
     */
    public boolean set(String key, Object value, Long expireTime, TimeUnit timeUnit){
        boolean result = false;
        try{
            ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key,value,expireTime,timeUnit);
            result = true;
        }catch(Exception e){
            logger.error(e);
        }
        return result;
    }

    /**
     * @param keys
     */
    public void remove(String... keys){
        for(String key: keys){
            remove(key);
        }
    }

    /**
     * @param key
     */
    public void remove(String key){
        if (exists(key)){
            redisTemplate.delete(key);
        }
    }

    /**
     * @param key
     * @return
     */
    public boolean exists(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * @param key
     * @return
     */
    public Object get(String key){
        Object result = null;
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        result = valueOperations.get(key);
        return result;
    }

    /**
     * @param key
     * @param hashKey
     * @param hashValue
     */
    public void hashSet(String key,Object hashKey,Object hashValue){
        redisTemplate.opsForHash().put(key,hashKey,hashValue);
    }

    /**
     * @param key
     * @param hashKey
     * @return
     */
    public Object hashGet(String key,Object hashKey){
        Object result = redisTemplate.opsForHash().get(key,hashKey);
        return result;
    }

    /**
     * @param k
     * @param v
     */
    public void listPush(String k,Object v){
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.rightPush(k,v);
    }

    /**
     * @param key
     * @param var1
     * @param var2
     * @return
     */
    public List<Object> listRange(String key,long var1,long var2){
        List<Object> result = redisTemplate.opsForList().range(key,var1,var2);
        return result;
    }

    /**
     * @param key
     * @param setValue
     */
    public void setAdd(String key,Object setValue){
        redisTemplate.opsForSet().add(key,setValue);
    }

    /**
     * @param key
     * @return
     */
    public Set<Object> setGet(String key){
        Set<Object> result = redisTemplate.opsForSet().members(key);
        return result;
    }

    /**
     * @param key
     * @param value
     * @param scoure
     */
    public void sortedSetAdd(String key,Object value,double scoure){
        redisTemplate.opsForZSet().add(key,value,scoure);
    }

    /**
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> sortedSetGet(String key,long scoure,long scoure1){
        Set<Object> result = redisTemplate.opsForZSet().range(key,scoure,scoure1);
        return result;
    }
}
