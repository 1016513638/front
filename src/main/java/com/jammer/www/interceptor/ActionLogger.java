package com.jammer.www.interceptor;
import com.jammer.www.util.DateUtil;
import com.jammer.www.util.Log4jInitUtil;
import org.apache.log4j.Logger;

public class ActionLogger {

    static {
        Log4jInitUtil.init();
    }

    /**
     * 管理员查询简历记录
     */
    public void adminSelectFormLogger(){
        String adminName=AdminLoggerInterceptor.tl.get();
        if(adminName!=null){
            Logger logger=Logger.getLogger(ActionLogger.class);
            logger.info(DateUtil.getNowTime()+"-- 管理员"+adminName+"进行了查询简历操作");
        }
    }

    /**
     * 管理员更新简历记录
     */
    public void adminUpdateFormLogger(){
        String adminName=AdminLoggerInterceptor.tl.get();
        if(adminName!=null){
            Logger logger=Logger.getLogger(ActionLogger.class);
            logger.info(DateUtil.getNowTime()+"-- 管理员"+adminName+"进行了更新简历操作");
        }
    }

    /**
     * 管理员更新其他管理员记录
     */
    public void adminUpdateAdminLogger(){
        String adminName=AdminLoggerInterceptor.tl.get();
        System.out.println(adminName);
        if(adminName!=null){
            Logger logger=Logger.getLogger(ActionLogger.class);
            logger.info(DateUtil.getNowTime()+"-- 管理员"+adminName+"进行了更新管理员操作");
        }
    }

}
