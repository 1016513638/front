package com.jammer.www.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 对登录进行单独限制
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession= request.getSession(false);
        if(httpSession.getAttribute("name")!=null){
            //已经有人在此浏览器登录了
            return false;
        }else{
            return true;
        }
    }
}
