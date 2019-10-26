/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xpressplayer.xpressplayer.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author martin
 */
public class ImageUtil {

    public static BufferedImage getBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null),
                img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        /*Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();*/

        // Return the buffered image
        return bimage;
    }
    
    public static ImageIcon resizeIcon(ImageIcon src, Dimension dim) {
        Image newImage = src.getImage().getScaledInstance(
                dim.width, dim.height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }
    
    public static ImageIcon resizeIcon(ImageIcon src, Rectangle dim) {
        Image newImage = src.getImage().getScaledInstance(
                dim.width, dim.height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }
    
    public static Rectangle getImageBounds(JLabel lblImg) {
        return getBufferedImage(((ImageIcon)lblImg.getIcon())
                .getImage()).getData().getBounds();
    }
    
    public static ImageIcon resizeIcon(byte[] imgBytes, JLabel lblImg) {
        ImageIcon sourceIcon = new ImageIcon(imgBytes);
        return resizeIcon(sourceIcon, lblImg.getBounds());
    }

}
