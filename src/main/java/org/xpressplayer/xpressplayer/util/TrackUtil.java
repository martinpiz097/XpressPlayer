/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xpressplayer.xpressplayer.util;

/**
 *
 * @author martin
 */
public class TrackUtil {
    public static String parseTitle(String title) {
        if (title.contains(".")) {
            StringBuilder sbTitle = new StringBuilder();
               
            byte pointChar = '.';
            boolean pointFound = false;
            for (int i = title.length()-1; i > -1; i--) {
                if (pointFound) {
                    sbTitle.insert(0, title.charAt(i));
                }
                else {
                    pointFound = title.charAt(i) == pointChar;
                }
            }
            return sbTitle.toString();
            
        }
        else {
            return title;
        }
    }
}
