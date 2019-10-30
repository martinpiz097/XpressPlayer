/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xpressplayer.xpressplayer.test;

import org.xpressplayer.xpressplayer.sys.NotificationManager;

/**
 *
 * @author martin
 */
public class NotificationTest {

    public static void main(String[] args) {
        NotificationManager manager = NotificationManager.getInstance();
        manager.sendNotification("Titulo", "Mensaje");
    }
}
