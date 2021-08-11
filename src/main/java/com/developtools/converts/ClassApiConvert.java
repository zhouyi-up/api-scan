package com.developtools.converts;
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

    public static List<ApiModel> toApiModel(List<ClassApiInfo> classApiInfos){
        List<ApiModel> apiModels = Lists.newArrayList();
        for (ClassApiInfo classApiInfo : classApiInfos) {
            List<MethodApiInfo> methodApiInfoList = classApiInfo.getMethodApiInfoList();
            for (MethodApiInfo methodApiInfo : methodApiInfoList) {

                List<ApiModel.Param> apis = Lists.newArrayList();
                List<ParameterApiInfo> parameterApiInfos = methodApiInfo.getParameterApiInfos();
                for (ParameterApiInfo parameterApiInfo : parameterApiInfos) {
                    if (parameterApiInfo.getParameters() == null || parameterApiInfo.getParameters().isEmpty()){
                        ApiModel.Param param = new ApiModel.Param();
                        param.setName(parameterApiInfo.getName());
                        param.setDesc(parameterApiInfo.getDesc());
                        param.setType(parameterApiInfo.getTypeName());
                        apis.add(param);
                    }else {
                        StringJoiner stringJoiner = new StringJoiner(".");
                        List<ApiModel.Param> params = toParamList(stringJoiner,parameterApiInfo);
                        apis.addAll(params);
                    }
                }
                String path = classApiInfo.getBasePath() + "/" + methodApiInfo.getPath();

                ApiModel apiModel = new ApiModel();
                apiModel.setPath(path);
                apiModel.setRequestMethod(methodApiInfo.getMethod());
                apiModel.setContentType("");
                apiModel.setName(methodApiInfo.getMethodName());
                apiModel.setDesc(methodApiInfo.getDesc());
                apiModel.setParamList(apis);


                ParameterApiInfo returnApiInfo = methodApiInfo.getReturnApiInfo();
                List<ApiModel.Param> returnParamList = Lists.newArrayList();
                if (returnApiInfo != null){
                    List<ParameterApiInfo> parameters = returnApiInfo.getParameters();
                    if (parameters == null || parameters.isEmpty()){
                        ApiModel.Param param = new ApiModel.Param();
                        param.setName(returnApiInfo.getName());
                        param.setDesc(returnApiInfo.getDesc());
                        param.setType(returnApiInfo.getTypeShortName());
                        returnParamList.add(param);
                    }else {
                        StringJoiner stringJoiner = new StringJoiner(".");
                        List<ApiModel.Param> params = toParamList(stringJoiner,returnApiInfo);
                        returnParamList.addAll(params);
                    }
                    apiModel.setReturnList(returnParamList);
                }


                apiModels.add(apiModel);
            }
        }
        return apiModels;
    }


    public static List<ApiModel.Param> toParamList(StringJoiner packName, ParameterApiInfo parameterApiInfo){
        List<ApiModel.Param> apis = Lists.newArrayList();

        packName.add(parameterApiInfo.getName());
        String string = packName.toString();
        if (parameterApiInfo.getParameters()!= null && !parameterApiInfo.getParameters().isEmpty()){
            for (ParameterApiInfo parameter : parameterApiInfo.getParameters()) {
                if (parameter.getParameters() != null && !parameter.getParameters().isEmpty()){
                    apis.addAll(toParamList(packName,parameter));
                }else {
                    ApiModel.Param param = new ApiModel.Param();
                    param.setName(string.concat(".").concat(parameter.getName()));
                    param.setDesc(parameter.getDesc());
                    param.setType(parameter.getTypeName());
                    apis.add(param);
                }
            }
        }else {
            ApiModel.Param param = new ApiModel.Param();
            param.setName(string.concat(".").concat(parameterApiInfo.getName()));
            param.setDesc(parameterApiInfo.getDesc());
            param.setType(parameterApiInfo.getTypeName());
            apis.add(param);
        }
        return apis;
    }
}
