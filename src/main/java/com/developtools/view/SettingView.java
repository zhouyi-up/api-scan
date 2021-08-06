package com.developtools.view;

import com.developtools.model.SettingModel;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author liujun
 */
public class SettingView {

    private SettingModel settingModel;

    private JPanel settingPanel;
    private JPanel tornaPanel;
    private JTextField tornaAddressText;
    private JTextField appKeyText;
    private JTextField secretText;
    private JTextField tokenText;

    public JPanel getSettingPanel() {
        return settingPanel;
    }
}
