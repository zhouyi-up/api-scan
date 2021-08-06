package com.developtools.model;

import lombok.Data;

/**
 * @author liujun
 */
@Data
public class SettingModel {

    private String tornaServerAddress;
    private String tornaAppKey;
    private String tornaSecret;
    private String tornaToken;

    private boolean isModify = false;
}
