package com.itmxln.blog.handler;

import com.alibaba.fastjson.JSON;
import com.itmxln.blog.dao.pojo.SysUser;
import com.itmxln.blog.service.LoginService;
import com.itmxln.blog.utils.UserThreadLocal;
import com.itmxln.blog.vo.ErrorCode;
import com.itmxln.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在执行controller方法(Handler)之前进行执行
        /**
         * 1、需要判断 请求的接口路径 是否为HandlerMethod（controller方法）
         * 2、需要判断token是否为空，如果为空 未登录
         * 3、如果token不为空，登录验证loginService.checktoken
         * 4、如果认证成功，放行
         */
        if (!(handler instanceof HandlerMethod)) {
            //handler 不为HandlerMethod（controller方法）
            return true;
        }
        String token = request.getHeader("Authorization");
        //日志
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if (StringUtils.isBlank(token)) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        UserThreadLocal.put(sysUser);
        //验证结束，放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除ThreadLocal中的信息，有内存泄露的风险
        UserThreadLocal.remove();
    }
}
