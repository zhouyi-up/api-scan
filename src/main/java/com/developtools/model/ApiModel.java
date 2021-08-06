package com.developtools.model;

import lombok.Data;

import java.util.List;

/**
 * @author liujun
 */
@Data
public class ApiModel {

    private String path;
    private String requestMethod;
    private String contentType;
    private String name;
    private String desc;
    private List<Param> paramList;

    @Data
    public static class Param{
        private String name;
        private String desc;
        private String type;
    }
}
