package com.lil.maven.common.annotation;

import org.springframework.web.servlet.DispatcherServlet;

import java.lang.annotation.*;

/**
 * 自定义登录拦截注解
 * @Author:lil
 * @Date: 2022-03-09
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginCheck {
}
