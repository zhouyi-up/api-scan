package com.developtools.converts;
import cn.torna.sdk.common.Booleans;
import cn.torna.sdk.param.DubboInfo;
import cn.torna.sdk.param.DocParamResp;
import cn.torna.sdk.param.EnumInfoParam;
import cn.torna.sdk.param.DocItem;
import cn.torna.sdk.param.DocParamReq;
import com.google.common.collect.Lists;

import com.alibaba.fastjson.JSON;
import com.developtools.model.ApiModel;
import com.developtools.model.ClassApiInfo;
import com.developtools.model.MethodApiInfo;
import com.developtools.model.ParameterApiInfo;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author liujun
 */
public class ClassApiConvert {


    public static List<DocItem> toFolderList(List<ClassApiInfo> classApiInfos){
        List<DocItem> docItemList = Lists.newArrayList();
        //类循环
        for (ClassApiInfo classApiInfo : classApiInfos) {
            List<DocItem> docItems = convertForClassApiInfo(classApiInfo);

            DocItem folder = new DocItem();
            folder.setIsFolder(Booleans.TRUE);
            folder.setName(classApiInfo.getDesc());
            folder.setUrl(classApiInfo.getBasePath());

            folder.setItems(docItems);

            docItemList.add(folder);
        }
        return docItemList;
    }

    public static List<DocItem> toDocItemList(List<ClassApiInfo> classApiInfos){
        List<DocItem> docItemList = Lists.newArrayList();
        //类循环
        for (ClassApiInfo classApiInfo : classApiInfos) {
            List<DocItem> docItems = convertForClassApiInfo(classApiInfo);
            docItemList.addAll(docItems);
        }
        return docItemList;
    }

    private static  List<DocItem> convertForClassApiInfo(ClassApiInfo classApiInfo) {
        List<DocItem> docItemList = Lists.newArrayList();
        //方法集合
        List<MethodApiInfo> methodApiInfoList = classApiInfo.getMethodApiInfoList();
        //方法循环
        for (MethodApiInfo methodApiInfo : methodApiInfoList) {
            String path = classApiInfo.getBasePath() + "/" + methodApiInfo.getPath();

            //参数集合
            List<ParameterApiInfo> parameterApiInfos = methodApiInfo.getParameterApiInfos();
            //取参数集合
            List<DocParamReq> docParamReqs =  toDocParamReq(parameterApiInfos);

            ParameterApiInfo returnApiInfo = methodApiInfo.getReturnApiInfo();
            //取返回值集合
            List<DocParamResp> docParamResps = toDocParamResp(returnApiInfo);

            DocItem docItem = new DocItem();
            docItem.setName(methodApiInfo.getDesc());
            docItem.setDescription(methodApiInfo.getDesc());
            docItem.setAuthor("");
            docItem.setUrl(path);
            docItem.setHttpMethod(methodApiInfo.getMethod());
            docItem.setContentType("");
            docItem.setRequestParams(docParamReqs);
            docItem.setResponseParams(Lists.newArrayList(docParamResps));
            docItemList.add(docItem);
        }

        return docItemList;
    }

    private static List<DocParamResp> toDocParamResp(ParameterApiInfo parameterApiInfo){
        List<ParameterApiInfo> parameters = parameterApiInfo.getParameters();
        if (parameters!= null && !parameters.isEmpty()){
            List<DocParamResp> docParamRespList = loopBuildDocParamResq(parameters);
            return docParamRespList;
        }else {
            List<DocParamResp> docParamResps = Lists.newArrayList();
            DocParamResp docParamResp = new DocParamResp();
            docParamResp.setName(parameterApiInfo.getName());
            docParamResp.setType(parameterApiInfo.getTypeShortName());
            docParamResp.setRequired((byte)0);
            docParamResp.setDescription(parameterApiInfo.getDesc());

            docParamResps.add(docParamResp);
            return docParamResps;
        }
    }

    private static List<DocParamResp> loopBuildDocParamResq(List<ParameterApiInfo> parameterApiInfos){

        List<DocParamResp> list = Lists.newArrayList();

        for (ParameterApiInfo parameterApiInfo : parameterApiInfos) {
            DocParamResp docParamResp = new DocParamResp();
            docParamResp.setName(parameterApiInfo.getName());
            docParamResp.setType(parameterApiInfo.getTypeShortName());
            docParamResp.setRequired((byte)0);
            docParamResp.setDescription(parameterApiInfo.getDesc());

            //参数子类
            List<ParameterApiInfo> parameters = parameterApiInfo.getParameters();
            //判断是否存在子类
            if (parameters != null && !parameters.isEmpty()){
                List<DocParamResp> ch = loopBuildDocParamResq(parameters);
                docParamResp.setChildren(ch);
                list.add(docParamResp);
            }else {
                //没有子类则是基本类型参数直接处理
                list.add(docParamResp);
            }
        }
        return list;
    }

    /**
     * 将参数转化为 torna参数
     * @param parameterApiInfos
     * @return
     */
    private static List<DocParamReq> toDocParamReq(List<ParameterApiInfo> parameterApiInfos){
        return loopBuildDocParamReq(parameterApiInfos);
    }

    private static List<DocParamReq> loopBuildDocParamReq(List<ParameterApiInfo> parameterApiInfos){
        List<DocParamReq> docParamReqs = Lists.newArrayList();
        for (ParameterApiInfo parameterApiInfo : parameterApiInfos) {
            DocParamReq docParamReq = new DocParamReq();
            docParamReq.setName(parameterApiInfo.getName());
            docParamReq.setType(parameterApiInfo.getTypeShortName());
            docParamReq.setRequired((byte)0);
            docParamReq.setDescription(parameterApiInfo.getDesc());

            //参数子类
            List<ParameterApiInfo> parameters = parameterApiInfo.getParameters();
            //判断是否存在子类
            if (parameters != null && !parameters.isEmpty()){
                List<DocParamReq> ch = loopBuildDocParamReq(parameters);
                docParamReq.setChildren(ch);
                docParamReqs.add(docParamReq);
            }else {
                //没有子类则是基本类型参数直接处理
                docParamReqs.add(docParamReq);
            }
        }
        return docParamReqs;
    }
}
