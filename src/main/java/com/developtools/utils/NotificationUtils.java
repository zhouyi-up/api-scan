package com.developtools.utils;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

/**
 * 通知
 */
public class NotificationUtils {

    private static NotificationUtils notificationUtils;

    private NotificationGroup notificationGroup;
    private Project project;

    private NotificationUtils(Project project){
        this.project = project;
        notificationGroup = NotificationGroupManager.getInstance().getNotificationGroup("API_SCAN_NOTIFICATION");
    }

    public static NotificationUtils getInstance(Project project){
        if (notificationUtils == null){
            notificationUtils = new NotificationUtils(project);
        }
        return notificationUtils;
    }

    public void send(String title, String content){
        Notification notification = notificationGroup.createNotification(NotificationType.INFORMATION);
        notification.setTitle(title);
        notification.setContent(content);
        notification.notify(project);
    }

    public void error(String title, String content){
        Notification notification = notificationGroup.createNotification(NotificationType.ERROR);
        notification.setTitle(title);
        notification.setContent(content);
        notification.notify(project);
    }
}
