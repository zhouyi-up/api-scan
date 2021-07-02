package com.developtools.model;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * @author liujun
 */
@Data
public class MethodApiInfo {

    private String methodName;
    private String methodShortName;

    private String method;
    private String path;

    private String desc;
    private Map<String, String> attr = Maps.newHashMap();
}
