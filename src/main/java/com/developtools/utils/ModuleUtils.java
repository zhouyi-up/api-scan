package com.developtools.utils;

import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.openapi.module.Module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author liujun
 */
public class ModuleUtils {

    public static Collection<PsiAnnotation> findPsiClassForAnnotation(Project project, Module module, String annotationName){
        GlobalSearchScope moduleScope = module.getModuleScope();
        JavaAnnotationIndex javaAnnotationIndex = JavaAnnotationIndex.getInstance();
        return javaAnnotationIndex.get(annotationName, project, moduleScope);
    }

    public static List<PsiAnnotation> findPsiClassForAnnotationToList(Project project, Module module, String annotationName){
        Collection<PsiAnnotation> psiAnnotation = findPsiClassForAnnotation(project, module, annotationName);
        return Lists.newArrayList(psiAnnotation);
    }

    public static List<PsiAnnotation> findPsiClassForAnnotationList(Project project, Module module, List<String> annotationNameList){
        List<PsiAnnotation> result = Lists.newArrayList();
        annotationNameList.stream().map(annotation -> findPsiClassForAnnotation(project, module, annotation))
                .forEach(result::addAll);
        return result;
    }
}
