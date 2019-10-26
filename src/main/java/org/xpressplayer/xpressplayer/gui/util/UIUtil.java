/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xpressplayer.xpressplayer.gui.util;

import java.awt.Color;
import java.awt.FontMetrics;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 *
 * @author martin
 */
public class UIUtil {
    public static void setBackgrounds(Color color, JComponent... components) {
        if (components != null && components.length > 0) {
            for (int i = 0; i < components.length; i++) {
                components[i].setBackground(color);
            }
        }
    }
    
    public static void setForegrounds(Color color, JComponent... components) {
        if (components != null && components.length > 0) {
            for (int i = 0; i < components.length; i++) {
                components[i].setForeground(color);
            }
        }
    }
    
    public static void setBorders(Border border, JComponent... components) {
        if (components != null && components.length > 0) {
            for (int i = 0; i < components.length; i++) {
                components[i].setBorder(border);
            }
        }
    }
    
    public static String getScaledText(String text, JLabel lblText) {
        char[] chars = text.toCharArray();
        final FontMetrics fontMetrics = lblText.getFontMetrics(lblText.getFont());
        final int lblWidth = lblText.getWidth();

        int finalTextLength = chars.length;
        for (int i = chars.length; i > -1; i--) {
            int charsWidth = fontMetrics.charsWidth(chars, 0, i);
            if (charsWidth <= lblWidth) {
                finalTextLength = i;
                break;
            }
        }
        return new String(chars, 0, finalTextLength);
    }

}
