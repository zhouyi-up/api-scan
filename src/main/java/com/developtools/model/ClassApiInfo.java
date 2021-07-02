package com.developtools.model;

import com.google.common.collect.Maps;
import com.intellij.psi.PsiClass;
import lombok.Data;

import java.util.Map;

/**
 * @author liujun
 */
@Data
public class ClassApiInfo {
    private String basePath;
    private String desc;
    private Map<String, Object> attr = Maps.newHashMap();

    private String className;
    private String classShortName;

    private PsiClass psiClass;
}
