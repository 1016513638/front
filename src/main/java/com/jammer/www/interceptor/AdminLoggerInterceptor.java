package com.jammer.www.interceptor;

import com.jammer.www.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 管理员操作拦截器
 */
public class AdminLoggerInterceptor implements HandlerInterceptor {

    protected static final ThreadLocal<String> tl=new ThreadLocal<>();

    /**
     * 对登录的成功与否进行记录
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String uri= request.getRequestURI();
        String methodPath=uri.substring(uri.lastIndexOf('/')+1);
        if("login.do".equals(methodPath)){
            Logger logger=Logger.getLogger(LoggerInterceptor.class);
            HttpSession httpSession= request.getSession(false);
            String name= (String) httpSession.getAttribute("name");
            if(name!=null){
                logger.info(DateUtil.getNowTime()+"--管理员"+name+"进行了登录操作，状态true");
            }else{
                logger.info(DateUtil.getNowTime()+"--管理员进行了登录操作，状态false");
            }
        }
        AdminLoggerInterceptor.tl.remove();
    }

    /**
     * 对标明admin的控制器进行拦截，验证登录
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession=request.getSession(false);
        if(httpSession!=null&&httpSession.getAttribute("name")!=null){
            tl.set((String)httpSession.getAttribute("name"));
            return true;
        }else {
            return false;
        }
    }

}
