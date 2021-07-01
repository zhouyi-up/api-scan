package com.developtools.utils;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;

/**
 * @author liujun
 */
public class PsiClassUtils {

    public static PsiAnnotation findPsiAnnotation(PsiClass psiClass, String annotationName){
        PsiAnnotation annotation = psiClass.getAnnotation(annotationName);
        return annotation;
    }
}
