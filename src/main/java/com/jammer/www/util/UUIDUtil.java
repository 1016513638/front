package com.jammer.www.util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UUIDUtil {

    private UUIDUtil(){}

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
