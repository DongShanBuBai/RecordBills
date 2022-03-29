package com.lil.maven.Utils;

import com.lil.maven.common.annotation.LoginCheck;
import com.lil.maven.responseformat.RespondData;
import com.lil.maven.responseformat.msgcode.TokenCode;
import com.lil.maven.service.TokenService;
import com.lil.maven.service.impl.TokenServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 定义拦截器，注解标记方法，检查是否登录
 * @Author:lil
 * @Date: 2022-03-09
 */
public class SourceAccessInterceptor implements HandlerInterceptor{
    @Autowired
    private TokenService tokenService;

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
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        String token = request.getHeader("TOKEN");
        if (token == null){
            logger.error("token 为空！");
            respondWriter(RespondData.fail(TokenCode.MSG_CODE401.getCode(),TokenCode.MSG_CODE400.getMsg()),response);
            return false;
        }
        RespondData<Map> tokenServiceRespondData = tokenService.verifyToken(token);
        if ((tokenServiceRespondData != null) && (tokenServiceRespondData.getStatus() == 200)){
            Map map = tokenServiceRespondData.getData();
            Integer userId = (Integer) map.get("userId");
            request.setAttribute("userId",userId);
            if (map.containsKey("newToken")){
                String newToken = map.get("newToken").toString();
                if (newToken != null){
                    //token在redis中存在
                    //将更新后的token放到请求域中
                    request.setAttribute("newToken",newToken);
                    return true;
                }else{
                    respondWriter(RespondData.fail(TokenCode.MSG_CODE401.getCode(),TokenCode.MSG_CODE403.getMsg()),response);
                    return false;
                }
            }else{
                logger.info("userId："+tokenServiceRespondData+"----token验证通过！");
                return true;
            }

        }
        respondWriter(RespondData.fail(TokenCode.MSG_CODE401.getCode(),TokenCode.MSG_CODE401.getMsg()),response);
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

    private void respondWriter(RespondData respondData,HttpServletResponse response){
        PrintWriter printWriter = null;
        try{
            printWriter = response.getWriter();
            printWriter.print(respondData);
        }catch(IOException e){
            logger.error("拦截器输出流异常！"+e);
        }finally {
            if (printWriter != null){
                printWriter.close();
            }
        }
    }
}
