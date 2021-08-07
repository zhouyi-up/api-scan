package com.developtools.actions;

import cn.torna.sdk.client.OpenClient;
import cn.torna.sdk.common.Booleans;
import cn.torna.sdk.param.DocItem;
import cn.torna.sdk.param.DocParamReq;
import cn.torna.sdk.request.DocPushRequest;
import cn.torna.sdk.response.DocPushResponse;
import com.developtools.converts.ClassApiConvert;
import com.developtools.model.ApiModel;
import com.developtools.model.ClassApiInfo;
import com.developtools.model.SettingModel;
import com.developtools.utils.ApiUtils;
import com.developtools.utils.DataUtils;
import com.google.common.collect.Lists;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.apache.tools.ant.types.optional.depend.ClassfileSet;

import static com.developtools.constants.TornaConstant.*;

import java.util.List;
import java.util.stream.Collectors;

public class UploadToTornaAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Project project = e.getProject();
        List<ClassApiInfo> classApiInfos = ApiUtils.getApiForModule(project);

        List<ApiModel> apiModels = ClassApiConvert.toApiModel(classApiInfos);


        DataUtils dataUtils = DataUtils.getInstance(e.getProject());

        SettingModel settingModel = new SettingModel();
        settingModel.setTornaServerAddress(dataUtils.getValue(HOST));
        settingModel.setTornaAppKey(dataUtils.getValue(APPKEY));
        settingModel.setTornaSecret(dataUtils.getValue(SECRET));
        settingModel.setTornaToken(dataUtils.getValue(TOKEN));

        OpenClient client = new OpenClient(settingModel.getTornaServerAddress(), settingModel.getTornaAppKey(), settingModel.getTornaSecret());

        DocPushRequest docPushRequest = new DocPushRequest(settingModel.getTornaToken());

        DocItem folder = new DocItem();
        folder.setIsFolder(Booleans.TRUE);
        folder.setName("支付");
        folder.setUrl("");

        List<DocItem> docItems = Lists.newArrayList();
        for (ApiModel apiModel : apiModels) {

            DocItem docItem = new DocItem();
            docItem.setName(apiModel.getName());
            docItem.setDescription(apiModel.getDesc());
            docItem.setUrl(apiModel.getPath());
            docItem.setHttpMethod(apiModel.getRequestMethod());

            List<DocParamReq> collect = apiModel.getParamList().stream().map(m -> {
                DocParamReq docParamReq = new DocParamReq();
                docParamReq.setDescription(m.getDesc());
                docParamReq.setName(m.getName());
                docParamReq.setType(m.getType());
                return docParamReq;
            }).collect(Collectors.toList());

            docItem.setRequestParams(collect);

            docItems.add(docItem);
        }
        folder.setItems(docItems);

        docPushRequest.setApis(Lists.newArrayList(folder));

        DocPushResponse execute = client.execute(docPushRequest);
        if (execute.isSuccess()){
            System.out.println(execute.getCode());
        }else {
            System.out.println(execute.getMsg());
        }
    }
}
