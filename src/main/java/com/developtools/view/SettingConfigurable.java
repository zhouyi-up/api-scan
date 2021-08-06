package com.developtools.view;

import com.developtools.model.SettingModel;
import com.developtools.utils.DataUtils;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author liujun
 */
public class SettingConfigurable implements SearchableConfigurable{

    private final SettingView settingView;
    private final SettingModel settingModel;
    private DataUtils dataUtils;

    public SettingConfigurable(){
        ProjectManager projectManager = ProjectManager.getInstance();
        Project defaultProject = projectManager.getDefaultProject();
        System.out.println(defaultProject.getName());

        dataUtils = DataUtils.getInstance(defaultProject);
        settingModel = new SettingModel();
        settingView = new SettingView(settingModel);
    }

    @Override
    public @NotNull @NonNls String getId() {
        return "apiScanSetting";
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "ApiScan Configurable";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return settingView.getSettingPanel();
    }

    @Override
    public boolean isModified() {
        return settingModel.isModify();
    }

    @Override
    public void apply() throws ConfigurationException {

    }
}
