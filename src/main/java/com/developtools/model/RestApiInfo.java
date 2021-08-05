package com.developtools.model;

import lombok.Data;

/**
 * 请求信息
 * @author liujun
 */
@Data
public class RestApiInfo {

    private String className;
    private String method;
    private String requestMethod;
    private String contentType;
    private String path;
    private String desc;


    @Data
    public static class Param{
        private String type;
        private String name;
        private String desc;
    }
}
