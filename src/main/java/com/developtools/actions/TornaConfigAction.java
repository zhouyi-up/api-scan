package com.developtools.actions;

import com.developtools.model.SettingModel;
import com.developtools.utils.DataUtils;
import com.developtools.view.SettingConfigurableDialogWrapper;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import static com.developtools.constants.TornaConstant.*;

public class TornaConfigAction extends AnAction {



    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here

        DataUtils dataUtils = DataUtils.getInstance(e.getProject());

        SettingModel settingModel = new SettingModel();
        settingModel.setTornaServerAddress(dataUtils.getValue(HOST));
        settingModel.setTornaAppKey(dataUtils.getValue(APPKEY));
        settingModel.setTornaSecret(dataUtils.getValue(SECRET));
        settingModel.setTornaToken(dataUtils.getValue(TOKEN));

        SettingConfigurableDialogWrapper dialogWrapper = new SettingConfigurableDialogWrapper(e.getProject(), settingModel);
        boolean b = dialogWrapper.showAndGet();
        if (b){
            dataUtils.setValue(HOST, settingModel.getTornaServerAddress());
            dataUtils.setValue(APPKEY, settingModel.getTornaAppKey());
            dataUtils.setValue(SECRET, settingModel.getTornaSecret());
            dataUtils.setValue(TOKEN, settingModel.getTornaToken());
        }
    }
}
