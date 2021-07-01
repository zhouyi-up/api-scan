package com.developtools.actions;

import com.alibaba.fastjson.JSON;
import com.developtools.model.ClassApiInfo;
import com.developtools.utils.ApiUtils;
import com.developtools.utils.ModuleUtils;
import com.developtools.utils.PsiAnnotationUtils;
import com.google.common.collect.Lists;
import com.intellij.lang.Language;
import com.intellij.lang.jvm.annotation.JvmAnnotationAttribute;
import com.intellij.lang.jvm.annotation.JvmAnnotationAttributeValue;
import com.intellij.lang.jvm.annotation.JvmAnnotationConstantValue;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;
import com.intellij.psi.search.GlobalSearchScope;
import com.thoughtworks.qdox.model.JavaAnnotation;

import java.util.*;
import java.util.stream.Collectors;

public class RightScanBtn extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Project project = e.getProject();
        Module[] modules = ModuleManager.getInstance(project).getModules();

        for (Module module : modules) {

            ArrayList<String> controllerAnn = Lists.newArrayList("Controller", "RestController");
            List<PsiAnnotation> controllerList = ModuleUtils.findPsiClassForAnnotationList(project, module, controllerAnn);

            List<PsiClass> psiClassList = PsiAnnotationUtils.toPsiClass(controllerList);

            for (PsiClass psiClass : psiClassList) {
                handlePsiClass(psiClass);
            }
        }
    }

    private void handlePsiClass(PsiClass psiClass) {
        ClassApiInfo classApiInfo = ApiUtils.toClassApiInfo(psiClass);

        PsiMethod[] methods = psiClass.getMethods();
        for (PsiMethod method : methods) {
            PsiAnnotation[] annotations = method.getModifierList().getAnnotations();
            System.out.println(method.getName());
            for (PsiAnnotation psiAnnotation : annotations) {
                String qualifiedName = psiAnnotation.getQualifiedName();
                System.out.println(qualifiedName);
                psiAnnotation.getAttributes().forEach(a -> {
                    JvmAnnotationAttributeValue attributeValue = a.getAttributeValue();
                    if (attributeValue instanceof JvmAnnotationConstantValue){
                        JvmAnnotationConstantValue jvmAnnotationConstantValue = (JvmAnnotationConstantValue) attributeValue;
                        System.out.println("---- " + jvmAnnotationConstantValue.getConstantValue());
                    }
                    System.out.println(a.getAttributeName() + "-" + attributeValue);
                });
            }

            PsiDocComment docComment = method.getDocComment();
            String text = docComment.getText();
            System.out.println(text);
            for (PsiDocTag tag : docComment.getTags()) {
                System.out.println(tag.getName());
            }
        }
    }
}
