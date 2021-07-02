package com.developtools.utils;


import com.google.common.collect.Maps;
import com.intellij.psi.PsiElement;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;
import com.intellij.psi.javadoc.PsiDocTagValue;

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
    private static Pattern DOC_PATTERN = Pattern.compile("\\/\\*\\* {0,}\\*{0,} {0,}(.{0,}) {0,}\\*{0,} {0,}@.{0,}\\*\\/");
    private static Pattern DOC_PATTERN_ALL = Pattern.compile("\\/\\*\\* {0,}\\*{0,} {0,}(.{0,}) {0,}\\*{0,} {0,}@{0,}.{0,}\\*\\/");

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
                    return t.getValueElement().getText();
                }));
        return map;
    }

    public static String toDocText(PsiDocComment psiDocComment){
        if (psiDocComment == null){
            return "";
        }
        String text = psiDocComment.getText().replaceAll("\n", "");
        Matcher matcher = DOC_PATTERN.matcher(text);
        if (!matcher.find()){
            Matcher matcherAll = DOC_PATTERN_ALL.matcher(text);
            if (matcherAll.find()){
                if (matcherAll.groupCount() >= 1){
                    return matcherAll.group(1);
                }
            }
            return "";
        }
        int num = matcher.groupCount();
        if (num >= 1){
            return matcher.group(1);
        }
        return "";
    }
}