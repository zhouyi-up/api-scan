package com.developtools.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiManager;
import com.intellij.util.keyFMap.KeyFMap;
import org.jetbrains.annotations.NotNull;

public class ScanAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        System.out.println("ddddd");
        Project project = e.getProject();
        PsiManager psiManager = PsiManager.getInstance(project);
        KeyFMap keyFMap = psiManager.get();
        @NotNull Key[] keys = keyFMap.getKeys();
        for (Key key : keys) {
            String s = key.toString();
            System.out.println(s);
        }
    }
}
