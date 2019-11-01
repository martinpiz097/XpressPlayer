/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xpressplayer.xpressplayer.test;

import dorkbox.systemTray.SystemTray;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import mdlaf.MaterialLookAndFeel;
import javax.swing.ImageIcon;

/**
 *
 * @author martin
 */
public class SystemTrayTest {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(new MaterialLookAndFeel());
        URL icon = SystemTrayTest.class.getResource("/logo.png");
        
        SystemTray tray = SystemTray.get();
        if (tray == null) {
            throw new RuntimeException("SystemTray not supported");
        }
        tray.setImage(icon);
        JMenuItem item = new JMenuItem();
        item.setText("Salir");
        item.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        System.out.println("TrayMenu: "+tray.getMenu());
        item.setIcon(new ImageIcon(SystemTrayTest.class.getResource("/logo.png")));
        tray.getMenu().add(item);
        tray.getMenu().setImage(icon);
        tray.setEnabled(true);
        tray.setEnabled(false);
    }
}
