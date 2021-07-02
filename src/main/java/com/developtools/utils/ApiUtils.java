package com.developtools.utils;

import com.developtools.enums.MappingEnum;
import com.developtools.model.ClassApiInfo;
import com.developtools.model.MappingAnnotationInfo;
import com.developtools.model.MethodApiInfo;
import com.developtools.model.ParameterApiInfo;
import com.google.common.collect.Lists;
import com.intellij.lang.jvm.JvmParameter;
import com.intellij.lang.jvm.annotation.JvmAnnotationAttribute;
import com.intellij.lang.jvm.annotation.JvmAnnotationAttributeValue;
import com.intellij.lang.jvm.annotation.JvmAnnotationConstantValue;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liujun
 */
public class ApiUtils {

    public static final String MAPPING_VALUE = "value";

    /**
     * 获取Java类基本API信息
     * @param psiClass
     * @return
     */
    public static ClassApiInfo toClassApiInfo(PsiClass psiClass){
        ClassApiInfo classApiInfo = new ClassApiInfo();
        classApiInfo.setPsiClass(psiClass);

        String name = psiClass.getName();
        LogsUtils.info("ClassShort {}", name);
        classApiInfo.setClassShortName(name);

        String qualifiedName = psiClass.getQualifiedName();
        LogsUtils.info("ClassName name {}", qualifiedName);
        classApiInfo.setClassName(qualifiedName);

        PsiAnnotation annotation = psiClass.getAnnotation(MappingEnum.REQUEST.getClassName());
        if (annotation != null){
            List<JvmAnnotationAttribute> attributes = annotation.getAttributes();
            List<JvmAnnotationAttribute> basePathList = attributes.stream()
                    .filter(attr -> attr.getAttributeName().contains(MAPPING_VALUE))
                    .collect(Collectors.toList());
            if (basePathList.size() > 0){
                JvmAnnotationAttributeValue attributeValue = basePathList.get(0).getAttributeValue();
                if (attributeValue instanceof JvmAnnotationConstantValue){
                    Object constantValue = ((JvmAnnotationConstantValue) attributeValue).getConstantValue();
                    classApiInfo.setBasePath(String.valueOf(constantValue));
                }
            }
        }
        PsiDocComment docComment = psiClass.getDocComment();
        if (docComment == null){
            return classApiInfo;
        }
        Map<String, Object> tags = PsiDocCommentUtils.toTagMap(docComment);
        classApiInfo.setAttr(tags);

        String desc = PsiDocCommentUtils.toDocText(docComment);
        LogsUtils.info("class desc {}", desc);
        classApiInfo.setDesc(desc);
        return classApiInfo;
    }

    /**
     * 获取每个方法的API信息
     * @param psiMethod
     * @return
     */
    public static MethodApiInfo toMethodApiInfo(PsiMethod psiMethod){
        MethodApiInfo methodApiInfo = new MethodApiInfo();
        String name = psiMethod.getName();
        LogsUtils.info("Method name {}", name);
        methodApiInfo.setMethodName(name);

        PsiDocComment docComment = psiMethod.getDocComment();
        methodApiInfo.setDesc(PsiDocCommentUtils.toDocText(docComment));
        methodApiInfo.setAttr(PsiDocCommentUtils.toTagMap(docComment));

        for (MappingEnum value : MappingEnum.values()) {
            PsiAnnotation annotation = psiMethod.getAnnotation(value.getClassName());
            if (annotation == null){
                continue;
            }
            MappingAnnotationInfo annotationInfo = PsiAnnotationUtils.toMappingAnnotationInfo(annotation);
            String path = annotationInfo.getPath();
            if (StringUtils.isEmpty(path)){
                path = annotationInfo.getValue();
            }
            methodApiInfo.setPath(path);

            String method = annotationInfo.getMethod();
            if (value != MappingEnum.REQUEST){
                method = value.getMethod();
            }
            methodApiInfo.setMethod(method);

            break;
        }

        PsiParameterList parameterList = psiMethod.getParameterList();
        PsiParameter[] parameters = parameterList.getParameters();
        for (PsiParameter parameter : parameters) {
            List<ParameterApiInfo> parameterApiInfos = toParameterApiInfo(parameter, methodApiInfo.getAttr());

        }
        return methodApiInfo;
    }

    public static List<ParameterApiInfo> toParameterApiInfo(PsiParameter parameter, Map<String,Object> attrs){
        List<ParameterApiInfo> list = Lists.newArrayList();

        PsiType type = parameter.getType();
        String className = type.getCanonicalText();
        boolean isBasicClass = ClassCheckUtils.isBasicClass(className);
        if (isBasicClass){
            ParameterApiInfo parameterApiInfo = new ParameterApiInfo();

            String name = parameter.getName();
            parameterApiInfo.setName(name);
            parameterApiInfo.setTypeName(className);

            String[] split = className.split("\\.");
            parameterApiInfo.setTypeShortName(split[split.length-1]);

            Object object = attrs.get(name);
            if (object != null){
                parameterApiInfo.setDesc(String.valueOf(object));
            }
            list.add(parameterApiInfo);
        }
        else {


        }

        return list;
    }
}
