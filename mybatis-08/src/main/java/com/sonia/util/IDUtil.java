package com.sonia.util;

import java.util.UUID;

@SuppressWarnings("all")
public class IDUtil {
    public static String genId() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
