package com.developtools.utils;

/**
 * @author liujun
 */
public class LogsUtils {

    private static String INFO_FORMAT = "[INFO] %s";

    public static void info(String msg){
        System.out.println(String.format(INFO_FORMAT, msg));
    }

    public static void info(String msg, Object... strings){
        if (strings.length == 0){
            info(msg);
        }else {
            for (Object string : strings) {
                msg = msg.replaceFirst("\\{\\}", string.toString());
            }
            info(msg);
        }
    }
}
