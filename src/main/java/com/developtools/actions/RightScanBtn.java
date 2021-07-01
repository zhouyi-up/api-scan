package com.developtools.actions;

import com.alibaba.fastjson.JSON;
import com.intellij.lang.Language;
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
        System.out.println(e.getClass());
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        if (psiFile != null){
            System.out.println(psiFile.getFileType().getName());
            return;
        }
        Project project = e.getProject();
        Module[] modules = ModuleManager.getInstance(project).getModules();
        for (Module module : modules) {

            System.out.println(module.getClass().getName());
            GlobalSearchScope moduleScope = module.getModuleScope();
            JavaAnnotationIndex javaAnnotationIndex = JavaAnnotationIndex.getInstance();
            Collection<PsiAnnotation> controllers = javaAnnotationIndex
                    .get("Controller", project, moduleScope);

            Collection<PsiAnnotation> restController = javaAnnotationIndex.get("RestController", project, moduleScope);

            List<PsiAnnotation> controllerList = new ArrayList<>();
            controllerList.addAll(controllers);
            controllerList.addAll(restController);

            List<PsiClass> psiClassList = controllerList.stream()
                    .map(c -> {
                        PsiElement parent = c.getParent();
                        PsiElement parentClass = parent.getParent();

                        if (parentClass instanceof PsiClass) {
                            return (PsiClass) parentClass;
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            for (PsiClass psiClass : psiClassList) {

                PsiAnnotation annotation = psiClass.getAnnotation("org.springframework.web.bind.annotation.RequestMapping");
                if (annotation != null){
                    System.out.println(annotation.getQualifiedName());
                }

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
    }
}
