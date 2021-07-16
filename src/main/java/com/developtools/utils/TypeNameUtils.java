package com.developtools.utils;

/**
 * @author liujun
 */
public class TypeNameUtils {

    public static String toShortName(String typeName){
        boolean contains = typeName.contains(".");
        if (!contains){
            return typeName;
        }
        String[] split = typeName.split("\\.");
        return split[split.length-1];
    }
}
