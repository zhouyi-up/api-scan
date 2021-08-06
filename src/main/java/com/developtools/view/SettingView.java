package com.developtools.view;

import com.developtools.model.SettingModel;

import javax.swing.*;

/**
 * @author liujun
 */
public class SettingView {

    private SettingModel settingModel;

    public SettingView(SettingModel settingModel){
        this.settingModel = settingModel;
    }

    private JPanel settingPanel;
    private JPanel tornaPanel;
    private JTextField tornaAddressText;
    private JTextField appKeyText;
    private JTextField secretText;
    private JTextField tokenText;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public JPanel getSettingPanel() {
        return settingPanel;
    }
}
