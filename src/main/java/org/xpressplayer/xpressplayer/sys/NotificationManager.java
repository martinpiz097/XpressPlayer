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
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author martin
 */
public class NotificationManager {
    private final SendNotification sendNotification;
    private Notifier notifier;
    
    private static NotificationManager instance = new NotificationManager();
    
    public static NotificationManager getInstance(Class<? extends NotificationManager> managerClass) {
        try {
            instance = managerClass.getConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException 
                | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(NotificationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return instance;
    }
    
    public static <T extends NotificationManager> T getInstance() {
        return (T) instance;
    }

    public NotificationManager() {
        sendNotification = new SendNotification()
                .setApplication(getApplication())
                .addConfigurationProperties(new NotificationConfiguration().getNotificationProperties());
        notifier = sendNotification.initNotifier();
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
        Notification notification = Notification.builder()
                .icon(getIcon())
                .level(Notification.Level.INFO)
                .message(msg)
                .title(title)
                .build();
        notifier.send(notification);
    }
    
}
