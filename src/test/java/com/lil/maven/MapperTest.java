package com.lil.maven;

import com.lil.maven.dao.mapper.UserMapper;
import com.lil.maven.pojo.User;
import com.lil.maven.responseformat.RespondData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:lil
 * @Date: 2022-02-24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MapperTest {
    Logger logger = LogManager.getLogger(MapperTest.class);

    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    public void test(){
        User result = userMapper.queryByUserName("123456");
        System.out.println(result);
    }
    @Test
    public void resultDataTest(){
        RespondData<String> data = RespondData.success("Hello");
        System.out.println(data);
    }
    @Test
    public void testLog4j(){
        logger.debug("log4j debug");
        logger.info("log4j2测试");
        logger.warn("log4j warn");
        logger.error("error test!");
        logger.fatal("log4j fatal");
        logger.error("error-[{}]","testLogger");
    }
    @Test
    public void redisTest(){
        String string = "redis demo1";
        Map<String,Object> map = new HashMap<>();
        Integer integer = new Integer(3);
        String string2 = "your name!";
        map.put("name",string2);
        map.put("id",integer);
        redisTemplate.opsForValue().set("demo1",map);
        logger.warn(redisTemplate.opsForValue().get("demo1"));
    }
}
