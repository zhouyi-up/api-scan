package com.developtools.model;

import lombok.Data;

/**
 * @author liujun
 */
@Data
public class MappingAnnotationInfo {
    private String value;
    private String name;
    private String path;
    private String method;
    private String params;
    private String header;
    private String consumes;
    private String produces;
}
