/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xpressplayer.xpressplayer.sys;

import dorkbox.systemTray.SystemTray;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author martin
 */
public class SystemTrayManager {
    private final SystemTray systemTray;

    public static final URL DEFAULT_IMAGE_URL = SystemTrayManager.class.getResource("/logo.png");
    public static final ImageIcon DEFAULT_IMAGE = new ImageIcon(DEFAULT_IMAGE_URL);
    
    private static final SystemTrayManager instance = new SystemTrayManager();
    
    public static SystemTrayManager getInstance() {
        return instance;
    }
    
    public SystemTrayManager() {
        systemTray = SystemTray.get();
        configureSystemTray();
    }
    
    private void configureSystemTray() {
        if (systemTray == null) {
            return;
        }
        systemTray.setImage(DEFAULT_IMAGE_URL);
    }
    
    public boolean isSupported() {
        return systemTray != null;
    }
    
    public void showTray() {
        if (isSupported()) {
            systemTray.setEnabled(true);
        }
    }
    
    public void hideTray() {
        if (isSupported()) {
            systemTray.setEnabled(false);
        }
    }

    public SystemTray getSystemTray() {
        return systemTray;
    }
    
}
