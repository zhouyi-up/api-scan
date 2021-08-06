package com.developtools.view;

import com.developtools.model.SettingModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author liujun
 */
public class SettingConfigurableDialogWrapper extends DialogWrapper {

    private final SettingModel settingModel;

    private Project project;

    public SettingConfigurableDialogWrapper(Project project, SettingModel settingModel){
        super(true);
        this.project = project;
        this.settingModel = settingModel;

        setTitle("Torna配置");

    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return new TornaSettingView(settingModel);
    }
}
