package com.developtools.enums;

import lombok.Getter;

/**
 * @author liujun
 */
@Getter
public enum MappingEnum {
    /**
     * 请求
     */
    REQUEST("org.springframework.web.bind.annotation.RequestMapping", "none"),
    GET("org.springframework.web.bind.annotation.GetMapping", "GET"),
    POST("org.springframework.web.bind.annotation.PostMapping", "POST"),
    PUT("org.springframework.web.bind.annotation.PutMapping", "PUT"),
    DELETE("org.springframework.web.bind.annotation.DeleteMapping", "DELETE"),
    ;

    private final String className;
    private final String method;

    MappingEnum(String className, String method){
        this.className = className;
        this.method = method;
    }
}
