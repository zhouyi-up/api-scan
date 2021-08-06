package com.developtools.view;

import com.developtools.model.SettingModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

/**
 * @author liujun
 */
public class TornaSettingView {
    private JPanel settingPanel;
    private JTextField hostText;
    private JTextField appKeyText;
    private JTextField secretText;
    private JTextField tokenText;

    private final SettingModel settingModel;

    public TornaSettingView(SettingModel settingModel){
        this.settingModel= settingModel;
        init();
        initListener();
    }

    public JPanel getSettingPanel(){
        return settingPanel;
    }

    public void init(){
        hostText.setText(settingModel.getTornaServerAddress());
        appKeyText.setText(settingModel.getTornaAppKey());
        secretText.setText(settingModel.getTornaSecret());
        tokenText.setText(settingModel.getTornaToken());
    }

    public void initListener(){
        hostText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                settingModel.setTornaServerAddress(hostText.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                settingModel.setTornaServerAddress(hostText.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                settingModel.setTornaServerAddress(hostText.getText());
            }
        });
        appKeyText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                settingModel.setTornaAppKey(appKeyText.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                settingModel.setTornaAppKey(appKeyText.getText());

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                settingModel.setTornaAppKey(appKeyText.getText());

            }
        });
        secretText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                settingModel.setTornaSecret(secretText.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                settingModel.setTornaSecret(secretText.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                settingModel.setTornaSecret(secretText.getText());
            }
        });
        tokenText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                settingModel.setTornaToken(tokenText.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                settingModel.setTornaToken(tokenText.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                settingModel.setTornaToken(tokenText.getText());
            }
        });
    }
}
