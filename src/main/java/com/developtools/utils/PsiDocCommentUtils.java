package com.developtools.utils;


import com.google.common.collect.Maps;
import com.intellij.psi.PsiElement;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;
import com.intellij.psi.javadoc.PsiDocTagValue;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author liujun
 */
public class PsiDocCommentUtils {

    public static final String PARAM = "param";

    public static Map<String, Object> toTagMap(PsiDocComment psiDocComment){
        if (psiDocComment == null){
            return Maps.newHashMap();
        }
        PsiDocTag[] tags = psiDocComment.getTags();
        if (tags.length == 0){
            return Maps.newHashMap();
        }
        Map<String, Object> map = Arrays.stream(tags)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(t -> {
                    if (t.getName().equals(PARAM)){
                        PsiElement[] dataElements = t.getDataElements();
                        if (dataElements.length > 0){
                            return dataElements[0].getText();
                        }
                    }
                    return t.getName();
                }, t -> {
                    if (t.getName().equals(PARAM)){
                        PsiElement[] dataElements = t.getDataElements();
                        if (dataElements.length > 1){
                            return dataElements[1].getText();
                        }else {
                            return "";
                        }
                    }
                    PsiDocTagValue valueElement = t.getValueElement();
                    if (valueElement == null){
                        return "";
                    }
                    return valueElement.getText();
                }));
        return map;
    }

    public static String toDocText(PsiDocComment psiDocComment){
        if (psiDocComment == null){
            return "";
        }
        String text = psiDocComment.getText().replaceAll("\n", "");
        int index = text.indexOf("/**");
        if (index == 0){
            text = text.substring(2);
        }
        int in = text.indexOf("*/");
        if (in == text.length() -2){
            text = text.substring(0, text.length() -2);
        }

        String[] strArr = text.split("\\*");
        if (strArr.length == 0){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String str : strArr) {
            if (StringUtils.isEmpty(str)) {
                continue;
            }
            if (str.contains("/**")) {
                continue;
            }
            if (str.contains("*/")) {
                continue;
            }
            if (!str.contains("@")){
                builder.append(str);
            }
        }
        return builder.toString();
    }
}