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
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
    
    public static ImageIcon getBlurred(byte[] imgData) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(imgData));
        } catch (IOException ex) {
            Logger.getLogger(ImageUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        float[] matrix = new float[400];
        for (int i = 0; i < 400; i++) {
            matrix[i] = 1.0f / 400.0f;
        }

        BufferedImageOp op = new ConvolveOp(new Kernel(20, 20, matrix), ConvolveOp.EDGE_NO_OP, null);
        BufferedImage blurred = op.filter(image, null);
        return new ImageIcon(blurred);

    }
    
    public static ImageIcon getBlurred(InputStream in) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(in);
        } catch (IOException ex) {
            Logger.getLogger(ImageUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        float[] matrix = new float[400];
        for (int i = 0; i < 400; i++) {
            matrix[i] = 1.0f / 400.0f;
        }

        BufferedImageOp op = new ConvolveOp(new Kernel(20, 20, matrix), ConvolveOp.EDGE_NO_OP, null);
        BufferedImage blurred = op.filter(image, null);
        return new ImageIcon(blurred);

    }

}
