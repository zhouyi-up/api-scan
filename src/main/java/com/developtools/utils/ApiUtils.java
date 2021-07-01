package com.developtools.utils;

import com.developtools.model.ClassApiInfo;
import com.intellij.psi.PsiClass;

/**
 * @author liujun
 */
public class ApiUtils {

    public static ClassApiInfo toClassApiInfo(PsiClass psiClass){
        ClassApiInfo classApiInfo = new ClassApiInfo();
        return classApiInfo;
    }
}
