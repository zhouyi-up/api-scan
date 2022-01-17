package com.developtools.actions;

import com.developtools.model.ClassApiInfo;
import com.developtools.utils.ApiUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

import java.util.*;

/**
 * @author corel
 */
public class RightScanBtn extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Project project = e.getProject();
        List<ClassApiInfo> classApiInfos = ApiUtils.getApiForModule(project);

    }
}
