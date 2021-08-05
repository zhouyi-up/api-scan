package com.developtools.utils;

import com.alibaba.fastjson.JSON;
import com.developtools.enums.MappingEnum;
import com.developtools.model.ClassApiInfo;
import com.developtools.model.MappingAnnotationInfo;
import com.developtools.model.MethodApiInfo;
import com.developtools.model.ParameterApiInfo;
import com.google.common.collect.Lists;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.lang.jvm.JvmParameter;
import com.intellij.lang.jvm.annotation.JvmAnnotationAttribute;
import com.intellij.lang.jvm.annotation.JvmAnnotationAttributeValue;
import com.intellij.lang.jvm.annotation.JvmAnnotationConstantValue;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liujun
 */
public class ApiUtils {

    public static final String MAPPING_VALUE = "value";


    public static List<ClassApiInfo> getApiForModule(Project project){

        Module[] modules = ModuleManager.getInstance(project).getModules();

        List<ClassApiInfo> classApi = Lists.newArrayList();
        for (Module module : modules) {

            ArrayList<String> controllerAnn = Lists.newArrayList("Controller", "RestController");
            List<PsiAnnotation> controllerList = ModuleUtils.findPsiClassForAnnotationList(project, module, controllerAnn);

            List<PsiClass> psiClassList = PsiAnnotationUtils.toPsiClass(controllerList);

            List<ClassApiInfo> classApiInfos = Lists.newArrayList();
            for (PsiClass psiClass : psiClassList) {
                ClassApiInfo classApiInfo = handlePsiClass(psiClass);
                classApiInfos.add(classApiInfo);
            }

            classApi.addAll(classApiInfos);
        }

        return classApi;
    }

    private static ClassApiInfo handlePsiClass(PsiClass psiClass) {
        ClassApiInfo classApiInfo = ApiUtils.toClassApiInfo(psiClass);

        List<MethodApiInfo> methodApiInfoList = Lists.newArrayList();
        PsiMethod[] methods = psiClass.getMethods();
        for (PsiMethod method : methods) {
            MethodApiInfo methodApiInfo = ApiUtils.toMethodApiInfo(method);
            LogsUtils.info(JSON.toJSONString(methodApiInfo));
            methodApiInfoList.add(methodApiInfo);
        }
        classApiInfo.setMethodApiInfoList(methodApiInfoList);

        return classApiInfo;
    }

    /**
     * 获取Java类基本API信息
     *
     * @param psiClass
     * @return
     */
    public static ClassApiInfo toClassApiInfo(PsiClass psiClass) {
        ClassApiInfo classApiInfo = new ClassApiInfo();
        classApiInfo.setPsiClass(psiClass);

        String name = psiClass.getName();
        LogsUtils.info("{} ClassShort {}", psiClass.getName(), name);
        classApiInfo.setClassShortName(name);

        String qualifiedName = psiClass.getQualifiedName();
        LogsUtils.info("{} ClassName name {}", psiClass.getName(), qualifiedName);
        classApiInfo.setClassName(qualifiedName);

        PsiAnnotation annotation = psiClass.getAnnotation(MappingEnum.REQUEST.getClassName());
        if (annotation != null) {
            List<JvmAnnotationAttribute> attributes = annotation.getAttributes();
            List<JvmAnnotationAttribute> basePathList = attributes.stream()
                    .filter(attr -> attr.getAttributeName().contains(MAPPING_VALUE))
                    .collect(Collectors.toList());
            if (basePathList.size() > 0) {
                JvmAnnotationAttributeValue attributeValue = basePathList.get(0).getAttributeValue();
                if (attributeValue instanceof JvmAnnotationConstantValue) {
                    Object constantValue = ((JvmAnnotationConstantValue) attributeValue).getConstantValue();
                    classApiInfo.setBasePath(String.valueOf(constantValue));
                }
            }
        }
        PsiDocComment docComment = psiClass.getDocComment();
        if (docComment == null) {
            return classApiInfo;
        }
        Map<String, Object> tags = PsiDocCommentUtils.toTagMap(docComment);
        classApiInfo.setAttr(tags);

        String desc = PsiDocCommentUtils.toDocText(docComment);
        LogsUtils.info("class {} desc {}", psiClass.getName(), desc);
        classApiInfo.setDesc(desc);
        return classApiInfo;
    }

    /**
     * 获取每个方法的API信息
     *
     * @param psiMethod
     * @return
     */
    public static MethodApiInfo toMethodApiInfo(PsiMethod psiMethod) {
        MethodApiInfo methodApiInfo = new MethodApiInfo();
        String name = psiMethod.getName();
        LogsUtils.info("Method name {}", name);
        methodApiInfo.setMethodName(name);

        PsiDocComment docComment = psiMethod.getDocComment();
        methodApiInfo.setDesc(PsiDocCommentUtils.toDocText(docComment));
        methodApiInfo.setAttr(PsiDocCommentUtils.toTagMap(docComment));

        for (MappingEnum value : MappingEnum.values()) {
            PsiAnnotation annotation = psiMethod.getAnnotation(value.getClassName());
            if (annotation == null) {
                continue;
            }
            MappingAnnotationInfo annotationInfo = PsiAnnotationUtils.toMappingAnnotationInfo(annotation);
            String path = annotationInfo.getPath();
            if (StringUtils.isEmpty(path)) {
                path = annotationInfo.getValue();
            }
            methodApiInfo.setPath(path);

            String method = annotationInfo.getMethod();
            if (value != MappingEnum.REQUEST) {
                method = value.getMethod();
            }
            methodApiInfo.setMethod(method);

//            break;
        }

        PsiParameterList parameterList = psiMethod.getParameterList();
        PsiParameter[] parameters = parameterList.getParameters();

        List<ParameterApiInfo> methodParameter = Lists.newArrayList();
        for (PsiParameter parameter : parameters) {
            LogsUtils.info("方法 {} 参数 {}", psiMethod.getName(), parameter.getName());
            List<ParameterApiInfo> parameterApiInfos = toParameterApiInfo(parameter, methodApiInfo.getAttr(), psiMethod);
            LogsUtils.info("方法 {} 参数 {}", JSON.toJSONString(parameterApiInfos));

            methodParameter.addAll(parameterApiInfos);
        }
        methodApiInfo.setParameterApiInfos(methodParameter);
        return methodApiInfo;
    }

    public static List<ParameterApiInfo> toParameterApiInfo(PsiParameter parameter, Map<String, Object> attrs, PsiMethod psiMethod) {
        List<ParameterApiInfo> list = Lists.newArrayList();

        PsiType type = parameter.getType();
        String className = type.getCanonicalText();
        boolean isBasicClass = ClassCheckUtils.isBasicClass(className);
        if (isBasicClass) {
            ParameterApiInfo parameterApiInfo = new ParameterApiInfo();

            String name = parameter.getName();
            parameterApiInfo.setName(name);
            parameterApiInfo.setTypeName(className);

            String[] split = className.split("\\.");
            parameterApiInfo.setTypeShortName(split[split.length - 1]);

            Object object = attrs.get(name);
            if (object != null) {
                parameterApiInfo.setDesc(String.valueOf(object));
            }
            list.add(parameterApiInfo);
        } else {
            if (type instanceof PsiClassReferenceType) {
                PluginManager instance = PluginManager.getInstance();
                PsiClassReferenceType psiClassReferenceType = (PsiClassReferenceType) type;
                PsiClass resolve = psiClassReferenceType.resolve();
                assert resolve != null;
                List<ParameterApiInfo> parameters = getParameters(resolve);
                list.addAll(parameters);
            }
        }

        return list;
    }

    public static List<ParameterApiInfo> getParameters(PsiClass psiClass){
        List<ParameterApiInfo> list = Lists.newArrayList();
        PsiField[] allFields = psiClass.getAllFields();
        for (PsiField field : allFields) {
            PsiType type = field.getType();

            boolean isBasicClass = ClassCheckUtils.isBasicClass(type.getCanonicalText());
            if (type instanceof PsiClassReferenceType && !isBasicClass){
                PsiClassReferenceType psiClassReferenceType = (PsiClassReferenceType) type;
                PsiClass resolve = psiClassReferenceType.resolve();

                if (resolve != null){
                    ParameterApiInfo parameterApiInfo = new ParameterApiInfo();
                    parameterApiInfo.setName(field.getName());
                    parameterApiInfo.setTypeName(field.getType().getCanonicalText());
                    PsiDocComment docComment = field.getDocComment();
                    if (docComment != null) {
                        String docText = PsiDocCommentUtils.toDocText(docComment);
                        parameterApiInfo.setDesc(docText);
                    }
                    parameterApiInfo.setTypeShortName(TypeNameUtils.toShortName(parameterApiInfo.getTypeName()));

                    parameterApiInfo.setParameters(getParameters(resolve));
                    list.add(parameterApiInfo);
                }
            } else {
                ParameterApiInfo parameterApiInfo = new ParameterApiInfo();
                String name = field.getName();
                String typeName = field.getType().getCanonicalText();

                PsiDocComment docComment = field.getDocComment();
                if (docComment != null) {
                    String docText = PsiDocCommentUtils.toDocText(docComment);
                    parameterApiInfo.setDesc(docText);
                }

                parameterApiInfo.setTypeName(typeName);
                parameterApiInfo.setTypeShortName(TypeNameUtils.toShortName(typeName));
                parameterApiInfo.setName(name);
                list.add(parameterApiInfo);
            }
        }
        return list;
    }
}
