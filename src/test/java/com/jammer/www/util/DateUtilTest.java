package com.jammer.www.util;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtilTest {

    @Test
    public void getNowTime(){
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr=sdf.format(date);
        System.out.println(timeStr);
    }

}