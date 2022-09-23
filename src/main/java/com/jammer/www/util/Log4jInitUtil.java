package com.jammer.www.util;

import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Log4jInitUtil {
    public static void init(){
        FileInputStream fis=null;
        String path=Thread.currentThread().getContextClassLoader().getResource("log4j-config.properties").getPath();
        try{
            fis=new FileInputStream(path);
            Properties p=new Properties();
            p.load(fis);
            PropertyConfigurator.configure(p);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
