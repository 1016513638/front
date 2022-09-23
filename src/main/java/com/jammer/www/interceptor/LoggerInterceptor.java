package com.jammer.www.interceptor;

import com.jammer.www.util.DateUtil;
import com.jammer.www.util.Log4jInitUtil;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 对所有请求进行记录
 */
public class LoggerInterceptor implements HandlerInterceptor {


    static {
        Log4jInitUtil.init();
    }


    /**
     * 拦截所有的请求，并记录请求路径日志
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String uri=request.getRequestURI();
        Logger logger=Logger.getLogger(LoggerInterceptor.class);
        logger.info(DateUtil.getNowTime()+"-- 请求了："+uri);
    }
}
