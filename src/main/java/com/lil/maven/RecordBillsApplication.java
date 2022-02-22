package com.lil.maven;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@MapperScan("com.lil.maven.dao.mapper")     //扫描mapper接口
@SpringBootApplication
public class RecordBillsApplication
{
    public static void main( String[] args )
    {

        SpringApplication.run(RecordBillsApplication.class,args);
    }
}
