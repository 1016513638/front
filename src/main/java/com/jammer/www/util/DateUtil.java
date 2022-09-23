package com.jammer.www.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private DateUtil(){}

    public static String getNowTime(){
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr=sdf.format(date);
        return timeStr;
    }

}
