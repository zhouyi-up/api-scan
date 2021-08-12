package com.developtools.enums;

import lombok.Getter;

/**
 * @author liujun
 */
@Getter
public enum ContentTypeEnum {
    /**
     * json
     */
    REQUEST_BODY("application/json", "org.springframework.web.bind.annotation.RequestBody")
    ;

    private final String contentType;
    private final String annotationClassName;

    ContentTypeEnum(String contentType, String annotationClassName){
        this.contentType = contentType;
        this.annotationClassName = annotationClassName;
    }
}
