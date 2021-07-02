package com.developtools.utils;

import java.util.Arrays;

/**
 * @author liujun
 */
public class ClassCheckUtils {

    private static String[] BASIC = new String[]{
            "java.lang.String",
            "java.lang.Integer",
            "java.lang.Long",
            "java.lang.Short",
            "java.lang.Double",
            "int",
            "long",
            "double",
            "short"
    };

    public static boolean isBasicClass(String className){
        return Arrays.asList(BASIC).contains(className);
    }
}
