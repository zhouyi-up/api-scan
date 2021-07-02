package com.developtools.utils;

import com.developtools.model.MappingAnnotationInfo;
import com.intellij.lang.jvm.annotation.JvmAnnotationArrayValue;
import com.intellij.lang.jvm.annotation.JvmAnnotationAttribute;
import com.intellij.lang.jvm.annotation.JvmAnnotationAttributeValue;
import com.intellij.lang.jvm.annotation.JvmAnnotationConstantValue;
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

    public static MappingAnnotationInfo toMappingAnnotationInfo(PsiAnnotation psiAnnotation){

        MappingAnnotationInfo annotationInfo = new MappingAnnotationInfo();

        List<JvmAnnotationAttribute> attributes = psiAnnotation.getAttributes();
        attributes.forEach(attr -> {
            String name = attr.getAttributeName();
            JvmAnnotationAttributeValue value = attr.getAttributeValue();
            if (value == null){
                return;
            }
            switch (name){
                case "name":
                    if (value instanceof JvmAnnotationConstantValue){
                        JvmAnnotationConstantValue jvmAnnotationConstantValue = (JvmAnnotationConstantValue) value;
                        String v = jvmAnnotationConstantValue.getConstantValue().toString();
                        annotationInfo.setName(v);
                        LogsUtils.info("Annotation name {}", v);
                    }
                    break;
                case "value":
                    String strings = AttributeValueUtils.toValue(value);
                    if (strings != null ){
                        annotationInfo.setValue(strings);
                        LogsUtils.info("Annotation value {}", strings);
                    }
                    break;
                case "method":
                    String methods = AttributeValueUtils.toValue(value);
                    if (methods != null){
                        annotationInfo.setMethod(methods);
                        LogsUtils.info("Annotation method {}", methods);
                    }
                    break;
                default:
                    LogsUtils.info("Annotation not find params");
            }
        });

        return annotationInfo;
    }
}
