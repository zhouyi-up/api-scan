package com.developtools.utils;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liujun
 */
public class PsiAnnotationUtils {

    public static List<PsiClass> toPsiClass(List<PsiAnnotation> psiAnnotations){
        return psiAnnotations.stream().map(c -> {
            PsiElement parent = c.getParent();
            PsiElement parentClass = parent.getParent();

            if (parentClass instanceof PsiClass) {
                return (PsiClass) parentClass;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
