/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xpressplayer.xpressplayer.sys;

import fr.jcgay.notification.Application;
import fr.jcgay.notification.Icon;
import fr.jcgay.notification.Notification;
import fr.jcgay.notification.Notifier;
import fr.jcgay.notification.SendNotification;

/**
 *
 * @author martin
 */
public class NotificationManager {
    private final SendNotification sendNotification;
    private Notifier notifier;
    
    private static final NotificationManager instance = new NotificationManager();
    
    public static NotificationManager getInstance() {
        return instance;
    }

    public NotificationManager() {
        sendNotification = new SendNotification()
                .setApplication(getApplication());
    }

    private void initNotifier() {
        notifier = sendNotification.initNotifier();
    }
    
    private Icon getIcon() {
        return Icon.create(getClass().getResource("/logo.png"), "iconXpress");
    }
    
    private Application getApplication() {
        return Application.builder("xp", "XpressPlayer", getIcon()).build();
    }
    
    public void sendNotification(String title, String msg) {
        initNotifier();
        Notification notification = Notification.builder()
                .icon(getIcon())
                .level(Notification.Level.INFO)
                .message(msg)
                .title(title)
                .build();
        notifier.send(notification);
        notifier.close();
    }
    
}
