package com.developtools.view;

import com.developtools.model.SettingModel;
import com.developtools.utils.DataUtils;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author liujun
 */
public class RequestToolsView extends SimpleToolWindowPanel {

    private Project project;
    private DataUtils dataUtils;

    public RequestToolsView(Project project) {
        super(true);
        this.project = project;
        this.dataUtils = DataUtils.getInstance(project);
        initBarBtn();
    }

    private void initBarBtn(){
        DefaultActionGroup defaultActionGroup = new DefaultActionGroup();

        //刷新
        AnAction refresh = new AnAction(AllIcons.Actions.Refresh) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }
        };
        //设置
        AnAction setting = new AnAction(AllIcons.FileTypes.Config) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent anActionEvent) {

            }
        };

        defaultActionGroup.add(refresh);
        defaultActionGroup.add(setting);

        JComponent barTools = ActionManager.getInstance().createActionToolbar("ApiScan", defaultActionGroup, true)
                .getComponent();
        setToolbar(barTools);
    }
}
