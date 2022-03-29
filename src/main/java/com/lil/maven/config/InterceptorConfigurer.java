package com.lil.maven.config;

import com.lil.maven.Utils.SourceAccessInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 把拦截器添加到拦截器链
 * @Author:lil
 * @Date: 2022-03-09
 */
@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {
    /**
     * 为了能在拦截器中注入依赖，否则自动注入将为空
     * @return
     */
    @Bean
    public SourceAccessInterceptor getSourceAccessInterceptor(){
        return new SourceAccessInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //      一个*：只匹配字符，不匹配路径（/）
        //      两个**：匹配字符，和路径（/）
        //      /**： 匹配所有路径
        //      /admin/**：匹配 /admin/ 下的所有路径
        //      /secure/*：只匹配 /secure/user，不匹配 /secure/user/info
        registry.addInterceptor(getSourceAccessInterceptor()).addPathPatterns("/**");
    }
}
