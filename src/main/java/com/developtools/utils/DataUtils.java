package com.developtools.utils;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;

/**
 * @author liujun
 */
public class DataUtils {

    private static DataUtils dataUtils;

    private final PropertiesComponent propertiesComponent;

    private DataUtils(Project project){
        propertiesComponent = PropertiesComponent.getInstance(project);
    }

    public static DataUtils getInstance(Project project){
        if (dataUtils == null){
            dataUtils = new DataUtils(project);
        }
        return dataUtils;
    }

    public void setValue(String key, String value){
        propertiesComponent.setValue(key, value);
    }

    public String getValue(String key){
        return propertiesComponent.getValue(key);
    }
}
