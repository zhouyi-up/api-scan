package com.developtools.view;

import com.developtools.model.SettingModel;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author liujun
 */
public class TornaSettingView extends JBPanel {

    private JTextField host;

    private SettingModel settingModel;

    public TornaSettingView(SettingModel settingModel){
        this.settingModel = settingModel;
        add(new JLabel("Host"));

        setLayout(new GridLayout(4,2));

        add(new JLabel("Host"));
    }

}
