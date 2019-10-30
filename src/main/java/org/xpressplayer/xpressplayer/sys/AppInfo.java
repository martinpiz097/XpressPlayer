/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xpressplayer.xpressplayer.sys;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author martin
 */
public class AppInfo {
    public static Properties getNotificationProperties() {
        InputStream propsIn = AppInfo.class.getResourceAsStream("/send-notification.properties");
        Properties props = new Properties();
        try {
            props.load(propsIn);
        } catch (IOException ex) {
            Logger.getLogger(AppInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return props;
    }
}
