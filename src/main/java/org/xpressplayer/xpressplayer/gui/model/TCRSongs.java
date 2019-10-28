/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xpressplayer.xpressplayer.gui.model;

import java.awt.Component;
import java.awt.Dimension;
import java.net.URL;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import org.muplayer.audio.model.TrackInfo;
import javax.swing.ImageIcon;
import javax.swing.plaf.metal.MetalLabelUI;
import mdlaf.utils.MaterialBorders;
import mdlaf.utils.MaterialColors;
import org.xpressplayer.xpressplayer.util.ImageUtil;

/**
 *
 * @author martin
 */
public class TCRSongs implements TableCellRenderer {
    
    private ImageIcon getIcon(TrackInfo trackInfo) {
        ImageIcon icon;
        URL defaultIcon = getClass().getResource("/img/cover.png");
        Dimension imgDim = new Dimension(48, 48);
        
        if (trackInfo.hasCover()) {
           icon = ImageUtil.resizeIcon(new ImageIcon(trackInfo.getCoverData()), imgDim); 
        }
        else {
           icon = ImageUtil.resizeIcon(new ImageIcon(defaultIcon), imgDim);
        }
        return icon;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel lblCell = new JLabel();
        TrackInfo song = ((TMSongs)table.getModel()).getInfo(row);
        
        lblCell.setOpaque(true);
        lblCell.setUI(new MetalLabelUI());
        lblCell.setText(song.getTitle());
        lblCell.setVerticalAlignment(JLabel.CENTER);
        lblCell.setHorizontalTextPosition(JLabel.RIGHT);
        
        
        if (isSelected) {
            lblCell.setBackground(MaterialColors.LIGHT_BLUE_200);
            lblCell.setForeground(MaterialColors.WHITE);
        }
        else {
            lblCell.setBackground(MaterialColors.WHITE);
            lblCell.setForeground(MaterialColors.BLACK);
        }

        ImageIcon icon = getIcon(song);
        lblCell.setIcon(icon);
        lblCell.setVisible(true);
        
        //lblCell.updateUI();
        
        return lblCell;
    }
    
}
