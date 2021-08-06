package com.developtools.actions;

import cn.torna.sdk.client.OpenClient;
import com.developtools.converts.ClassApiConvert;
import com.developtools.model.ClassApiInfo;
import com.developtools.model.SettingModel;
import com.developtools.utils.ApiUtils;
import com.developtools.utils.DataUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.apache.tools.ant.types.optional.depend.ClassfileSet;

import static com.developtools.constants.TornaConstant.*;

import java.util.List;

public class UploadToTornaAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Project project = e.getProject();
        List<ClassApiInfo> classApiInfos = ApiUtils.getApiForModule(project);

        ClassApiConvert.toApiModel(classApiInfos);

        DataUtils dataUtils = DataUtils.getInstance(e.getProject());

        SettingModel settingModel = new SettingModel();
        settingModel.setTornaServerAddress(dataUtils.getValue(HOST));
        settingModel.setTornaAppKey(dataUtils.getValue(APPKEY));
        settingModel.setTornaSecret(dataUtils.getValue(SECRET));
        settingModel.setTornaToken(dataUtils.getValue(TOKEN));

        OpenClient client = new OpenClient(settingModel.getTornaServerAddress(), settingModel.getTornaAppKey(), settingModel.getTornaSecret());


    }
}
