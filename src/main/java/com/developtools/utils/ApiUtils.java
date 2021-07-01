package com.developtools.utils;

import com.developtools.model.ClassApiInfo;
import com.intellij.lang.jvm.annotation.JvmAnnotationAttribute;
import com.intellij.lang.jvm.annotation.JvmAnnotationAttributeValue;
import com.intellij.lang.jvm.annotation.JvmAnnotationConstantValue;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liujun
 */
public class ApiUtils {

    public static final String REQUEST_MAPPING_CLASS_NAME = "org.springframework.web.bind.annotation.RequestMapping";
    public static final String MAPPING_VALUE = "value";

    public static ClassApiInfo toClassApiInfo(PsiClass psiClass){
        ClassApiInfo classApiInfo = new ClassApiInfo();

        PsiAnnotation annotation = psiClass.getAnnotation(REQUEST_MAPPING_CLASS_NAME);
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

        PsiDocTag[] tags = docComment.getTags();
        Arrays.stream(tags).forEach(tag -> {
            System.out.println(tag.getValueElement().getText());
        });

        return classApiInfo;
    }
}
