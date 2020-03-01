/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xpressplayer.xpressplayer.sys;

import java.util.Properties;

/**
 *
 * @author martin
 */
public class NotificationConfiguration {
    private final Properties props;

    public NotificationConfiguration() {
        props = new Properties();
    }
    
    private void init() {
        props.setProperty("notifier.implementation", "kdialog,notify-send,snarl,growl");
    }
    
    public Properties getNotificationProperties() {
        return props;
    }
    
    
}
