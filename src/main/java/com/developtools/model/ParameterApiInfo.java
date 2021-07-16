package com.developtools.model;

import lombok.Data;

import java.util.List;

/**
 * @author liujun
 */
@Data
public class ParameterApiInfo {

    private String typeName;
    private String typeShortName;

    private String desc;
    private String name;

    private List<ParameterApiInfo> parameters;
}
