package com.developtools.actions;

import cn.torna.sdk.client.OpenClient;
import cn.torna.sdk.param.DocItem;
import cn.torna.sdk.request.DocPushRequest;
import cn.torna.sdk.response.DocPushResponse;
import com.developtools.converts.ClassApiConvert;
import com.developtools.model.ClassApiInfo;
import com.developtools.model.SettingModel;
import com.developtools.utils.ApiUtils;
import com.developtools.utils.DataUtils;
import com.developtools.utils.NotificationUtils;
import com.google.common.collect.Lists;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import static com.developtools.constants.TornaConstant.*;

import java.util.List;

/**
 * @author corel
 */
public class UploadToTornaAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

        Project project = e.getProject();
        ProgressManager instance = ProgressManager.getInstance();
        List<ClassApiInfo> classApiInfos = ApiUtils.getApiForModule(project);

        Task.Backgroundable backgroundable = new Task.Backgroundable(project, "Up") {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                uploadTorna(project, classApiInfos);
            }
        };

        instance.run(backgroundable);
    }

    private void uploadTorna(Project project, List<ClassApiInfo> classApiInfos) {

        DataUtils dataUtils = DataUtils.getInstance(project);

        SettingModel settingModel = new SettingModel();
        settingModel.setTornaServerAddress(dataUtils.getValue(HOST));
        settingModel.setTornaAppKey(dataUtils.getValue(APPKEY));
        settingModel.setTornaSecret(dataUtils.getValue(SECRET));
        settingModel.setTornaToken(dataUtils.getValue(TOKEN));

        OpenClient client = new OpenClient(settingModel.getTornaServerAddress(), settingModel.getTornaAppKey(), settingModel.getTornaSecret());

        DocPushRequest docPushRequest = new DocPushRequest(settingModel.getTornaToken());

        List<DocItem> folderList = ClassApiConvert.toFolderList(classApiInfos);
        docPushRequest.setApis(folderList);
        docPushRequest.setDebugEnvs(Lists.newArrayList());
        try {
            DocPushResponse execute = client.execute(docPushRequest);
            if (execute.isSuccess()){
                NotificationUtils.getInstance(project).send("Torna", "上传成功");
            }else {
                NotificationUtils.getInstance(project).error("Torna", "上传失败");
            }
        } catch (Exception exception){
            exception.printStackTrace();
            NotificationUtils.getInstance(project).error("Upload error", exception.toString());
        }

    }
}
