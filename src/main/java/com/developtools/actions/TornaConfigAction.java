package com.developtools.actions;

import com.developtools.model.SettingModel;
import com.developtools.view.SettingConfigurableDialogWrapper;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class TornaConfigAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here

        SettingModel settingModel = new SettingModel();
        SettingConfigurableDialogWrapper dialogWrapper = new SettingConfigurableDialogWrapper(e.getProject(), settingModel);
        boolean b = dialogWrapper.showAndGet();
        if (b){

        }
    }
}
