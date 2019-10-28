/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xpressplayer.xpressplayer.gui.model;

import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import org.muplayer.audio.Player;
import org.muplayer.audio.model.TrackInfo;
import org.xpressplayer.xpressplayer.sys.FormObject;
import org.xpressplayer.xpressplayer.sys.FormObjectManager;

/**
 *
 * @author martin
 */
public class TMSongs implements TableModel {

    private final List<TrackInfo> listTracks;

    public TMSongs(List<TrackInfo> listTracks) {
        this.listTracks = listTracks;
    }

    public TMSongs() {
        listTracks = ((Player)FormObjectManager.getInstance().get(FormObject.PLAYER)).getTracksInfo();
    }

    public TrackInfo getInfo(int row) {
        return listTracks.get(row);
    }
    
    @Override
    public int getRowCount() {
        return listTracks.size();
    }

    // nombre con cover
    // artista
    // duracion
    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex) {
            default:
                return "Lista de Canciones";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TrackInfo info = listTracks.get(rowIndex);
        switch(columnIndex) {
            default:
                return info.getTitle();
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }
    
}
