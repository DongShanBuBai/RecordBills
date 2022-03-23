package com.lil.maven.Utils;

import com.lil.maven.common.annotation.LoginCheck;
import com.lil.maven.service.TokenService;
import com.lil.maven.service.impl.TokenServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义拦截器，注解标记方法，检查是否登录
 * @Author:lil
 * @Date: 2022-03-09
 */
public class SourceAccessInterceptor implements HandlerInterceptor{
    TokenService tokenService = new TokenServiceImpl();

    Logger logger = LogManager.getLogger(SourceAccessInterceptor.class);

    /**
     * 预处理，在Controller之前调用
     * 在DispatcherServlet类中的protected void doDispatch(HttpServletRequest request, HttpServletResponse response)
     * 方法中进行调用：if (!mappedHandler.applyPreHandle(processedRequest, response))
     * applyPreHandle()方法属于HandlerExecutionChain类(处理执行链)
     * applyPreHandle()方法中进行了调用：if (!interceptor.preHandle(request, response, this.handler))
     * @param request
     * @param response
     * @param handler HandlerExecutionChain类的一个成员
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        LoginCheck loginCheck = handlerMethod.getMethod().getAnnotation(LoginCheck.class);
        if (loginCheck == null){
            return true;
        }
        String token = request.getHeader("TOKEN");
        if (token == null){
            logger.info("token 为空！");
            return false;
        }
        Integer userId = tokenService.verifyToken(token);
        if (userId != null){
            logger.info("userId："+userId+"----token验证通过！");
            return true;
        }
        return false;
    }

    /**
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{

    }

    /**
     * @param request
     * @param response
     * @param handler
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,Object handler,Exception e) throws Exception {

    }
}
