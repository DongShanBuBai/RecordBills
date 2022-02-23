package com.lil.maven;


import com.lil.maven.dao.mapper.UserLoginMapper;
import com.lil.maven.pojo.UserLogin;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 用于mybatis连接mysql数据库测试
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MapperTest {
    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;
    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

        //template模板，拿来即用
        connection.close();
    }
    @Autowired
    UserLoginMapper userLoginMapper;
    @Test
    public void toTest(){
        List<UserLogin> userLogins = userLoginMapper.queryAll();
        userLogins.forEach(e-> System.out.println(e));
    }
}
