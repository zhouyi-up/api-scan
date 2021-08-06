package com.developtools.view;

import com.developtools.model.SettingModel;
import com.developtools.utils.DataUtils;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author liujun
 */
public class SettingConfigurableDialogWrapper extends DialogWrapper {

    private final SettingView settingView;
    private final SettingModel settingModel;

    private DataUtils dataUtils;
    private Project project;

    public SettingConfigurableDialogWrapper(Project project){
        super(true);
        this.project = project;
        dataUtils = DataUtils.getInstance(project);
        settingModel = new SettingModel();
        settingView = new SettingView();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return settingView.getSettingPanel();
    }
}
