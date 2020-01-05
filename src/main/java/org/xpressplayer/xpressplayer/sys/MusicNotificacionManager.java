/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xpressplayer.xpressplayer.sys;

import org.muplayer.audio.model.TrackInfo;

/**
 *
 * @author martin
 */
public class MusicNotificacionManager extends NotificationManager {

    public MusicNotificacionManager() {
        super();
    }
    
    public void sendTrackNotification(TrackInfo track) {
        final int maxInfoSize = 40;
        
        String title = track.getTitle();
        String artist = track.getArtist();

        if (title.length() > maxInfoSize) {
            title = title.substring(0, maxInfoSize);
        }
        if (artist == null) {
            artist = "Artista Desconocido";
        }
        else if (artist.length() > maxInfoSize) {
            artist = artist.substring(0, maxInfoSize);
        }
        sendNotification(title, artist);
    }
    
    
}
