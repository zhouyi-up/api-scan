package com.developtools.view;

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

    public RequestToolsView(Project project) {
        super(true);
        this.project = project;
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

        defaultActionGroup.add(refresh);

        JComponent barTools = ActionManager.getInstance().createActionToolbar("ApiScan", defaultActionGroup, true)
                .getComponent();
        setToolbar(barTools);
    }
}
