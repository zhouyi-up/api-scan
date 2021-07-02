package com.developtools.utils;

import com.intellij.lang.jvm.JvmClass;
import com.intellij.lang.jvm.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liujun
 */
public class AttributeValueUtils {

    public static String toValue(JvmAnnotationAttributeValue jvmAnnotationAttributeValue){
        if (jvmAnnotationAttributeValue instanceof JvmAnnotationConstantValue){
            JvmAnnotationConstantValue jvmAnnotationConstantValue = (JvmAnnotationConstantValue) jvmAnnotationAttributeValue;
            return jvmAnnotationConstantValue.getConstantValue().toString();
        }
        else if (jvmAnnotationAttributeValue instanceof JvmAnnotationEnumFieldValue){
            JvmAnnotationEnumFieldValue enumFieldValue = (JvmAnnotationEnumFieldValue) jvmAnnotationAttributeValue;
            return enumFieldValue.getFieldName();
        } else if (jvmAnnotationAttributeValue instanceof JvmAnnotationArrayValue){
            JvmAnnotationArrayValue jvmAnnotationArrayValue = (JvmAnnotationArrayValue) jvmAnnotationAttributeValue;
            List<JvmAnnotationAttributeValue> values = jvmAnnotationArrayValue.getValues();
            if (values.isEmpty()){
                return null;
            }
            return values.stream()
                    .map(AttributeValueUtils::toValue).collect(Collectors.toList()).get(0);
        }

        return null;
    }

    public static String[] toArray(JvmAnnotationAttributeValue attributeValue){
        if (attributeValue instanceof JvmAnnotationArrayValue){
            JvmAnnotationArrayValue jvmAnnotationArrayValue = (JvmAnnotationArrayValue) attributeValue;
            List<JvmAnnotationAttributeValue> values = jvmAnnotationArrayValue.getValues();
            if (values.isEmpty()){
                return null;
            }
            return values.stream()
                    .map(AttributeValueUtils::toValue).toArray(String[]::new);
        }

        return null;
    }
}
