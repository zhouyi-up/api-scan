package com.developtools.converts;

import com.alibaba.fastjson.JSON;
import com.developtools.model.ApiModel;
import com.developtools.model.ClassApiInfo;
import com.developtools.model.MethodApiInfo;
import com.developtools.model.ParameterApiInfo;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * @author liujun
 */
public class ClassApiConvert {

    public static List<ApiModel> toApiModel(List<ClassApiInfo> classApiInfos){
        for (ClassApiInfo classApiInfo : classApiInfos) {
            List<MethodApiInfo> methodApiInfoList = classApiInfo.getMethodApiInfoList();
            for (MethodApiInfo methodApiInfo : methodApiInfoList) {
                List<ParameterApiInfo> parameterApiInfos = methodApiInfo.getParameterApiInfos();
                for (ParameterApiInfo parameterApiInfo : parameterApiInfos) {
                    if (parameterApiInfo.getParameters() == null || parameterApiInfo.getParameters().isEmpty()){

                    }else {
                        List<ApiModel.Param> params = toParamList(parameterApiInfo);
                        System.out.println(JSON.toJSONString(params));
                    }

                }
            }
        }
        return null;
    }


    public static List<ApiModel.Param> toParamList(ParameterApiInfo parameterApiInfo){
        List<ApiModel.Param> apis = Lists.newArrayList();
        if (parameterApiInfo.getParameters()!= null && !parameterApiInfo.getParameters().isEmpty()){
            for (ParameterApiInfo parameter : parameterApiInfo.getParameters()) {
                if (parameterApiInfo.getParameters() != null && !parameterApiInfo.getParameters().isEmpty()){
                    apis.addAll(toParamList(parameter));
                }else {
                    ApiModel.Param param = new ApiModel.Param();
                    param.setName(parameterApiInfo.getName().concat(".").concat(parameter.getName()));
                    param.setDesc(parameter.getDesc());
                    param.setType(parameter.getTypeName());
                    apis.add(param);
                }
            }
        }else {
            ApiModel.Param param = new ApiModel.Param();
            param.setName(parameterApiInfo.getName());
            param.setDesc(parameterApiInfo.getDesc());
            param.setType(parameterApiInfo.getTypeName());
            apis.add(param);
        }
        return apis;
    }
}
