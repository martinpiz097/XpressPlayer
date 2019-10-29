/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xpressplayer.xpressplayer.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import mdlaf.MaterialLookAndFeel;
import mdlaf.animation.MaterialUIMovement;
import static mdlaf.utils.MaterialColors.*;
import org.muplayer.audio.Player;
import org.muplayer.audio.Track;
import org.muplayer.audio.interfaces.PlayerListener;
import org.xpressplayer.xpressplayer.gui.util.UIUtil;
import org.xpressplayer.xpressplayer.util.TrackUtil;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import mdlaf.utils.MaterialFontFactory;
import org.muplayer.audio.model.TrackInfo;
import org.xpressplayer.xpressplayer.gui.model.TCRSongs;
import org.xpressplayer.xpressplayer.gui.model.TMSongs;
import org.xpressplayer.xpressplayer.sys.FormObject;
import org.xpressplayer.xpressplayer.sys.FormObjectManager;
import org.xpressplayer.xpressplayer.util.ImageUtil;



/**
 *
 * @author martin
 */
public class FormPlayer extends javax.swing.JFrame {

    private JFileChooser musicChooser;
    
    private Player player;
    
    public FormPlayer() {
        initComponents();
        musicChooser = new JFileChooser();
        
        player = new Player();
        //player.start();
        
        FormObjectManager.getInstance().add(FormObject.PLAYER, player);
        configurePlayer();
        configureUI();
        configFileChooser();
        
    }
    
    private void configureTheme(Color color) {
        trackBar.setForeground(color);
    }
    
    private void configureUI() {
        configureTheme(LIGHT_BLUE_300);

        tblSongs.setBackground(WHITE);
        tblSongs.setRowHeight(30);
        setLocationRelativeTo(null);

        UIUtil.setBackgrounds(WHITE, trackBar);
        UIUtil.setBackgrounds(GRAY_300, panelFooter, btnPlay, btnNext, btnPrev, btnMute, btnLoadMusic, lblTitleFooter);
        //UIUtil.setBorders(MaterialBorders.DEFAULT_SHADOW_BORDER, btnPlay, btnNext, btnPrev);

        lblTitle.setFont(new Font(MaterialFontFactory.REGULAR, Font.PLAIN, 18));
        lblArtist.setFont(new Font(MaterialFontFactory.REGULAR, Font.PLAIN, 16));
        lblAlbum.setFont(new Font(MaterialFontFactory.REGULAR, Font.PLAIN, 14));
        configureTransitions();
    }
    
    private void configureTransitions() {
        MaterialUIMovement.add(btnPlay, LIGHT_BLUE_200);
        MaterialUIMovement.add(btnNext, LIGHT_BLUE_200);
        MaterialUIMovement.add(btnPrev, LIGHT_BLUE_200);
    }
    
    private void setAllFonts(Component component) {
        //component.setFont(MaterialFonts.REGULAR);
        if (component instanceof JComponent) {
            Component[] components = ((JComponent)component).getComponents();
            for (int i = 0; i < components.length; i++) {
                setAllFonts(components[i]);
            }
        }
    }
    
    private void loadTrackInfo(TrackInfo track) {
        String title = TrackUtil.parseTitle(track.getTitle());
        String compoundedTitle;
        String artist = track.getArtist();
        String album = track.getAlbum();
        byte[] coverData = track.getCoverData();

        if (artist == null) {
            artist = "Artista Desconocido";
        }
        if (album == null) {
            album = "Álbum Desconocido";
        }
        compoundedTitle = title.concat("  -  ").concat(artist);
        lblTitle.setText(UIUtil.getScaledText(title, lblTitle));
        lblTitleFooter.setText(UIUtil.getScaledText(compoundedTitle, lblTitleFooter));
        lblArtist.setText(UIUtil.getScaledText(artist, lblArtist));
        lblAlbum.setText(UIUtil.getScaledText(album, lblAlbum));

        ImageIcon cover;
        //Image blurredCover;
        //Toolkit toolkit = Toolkit.getDefaultToolkit();
        
        if (coverData == null || coverData.length == 0) {
            cover = new ImageIcon(getClass().getResource("/img/cover256.png"));
            //blurredCover = toolkit.createImage(getClass().getResource("/img/cover256.png"));
        }
        else {
            cover = ImageUtil.resizeIcon(new ImageIcon(coverData), new Dimension(256, 256));
            //blurredCover = toolkit.createImage(coverData);
        }
        lblCover.setIcon(cover);
        //Graphics graphics = panelSong.getGraphics();
        //graphics.drawImage(blurredCover, 0, 0, null);
        List<String> listSoundPaths = player.getListSoundPaths();
        String currentPath = player.getCurrent().getDataSource().getPath();
        int indexOf = listSoundPaths.indexOf(currentPath);
        

        // cosas imbeciles que no se por que funcionan asi
        while (tblSongs.getRowCount() == 0) {}
        
        if (indexOf != -1) {
            tblSongs.setRowSelectionInterval(indexOf, indexOf);
        }
        
        trackBar.setValue(0);
        trackBar.setMinimum(0);
        trackBar.setMaximum((int) track.getDuration());
    }
    
    private void configurePlayer() {
        player.addPlayerListener(new PlayerListener() {
            @Override
            public void onSongChange(Track track) {
                loadTrackInfo(track);
            }

            @Override
            public void onPlayed(Track track) {
                loadTrackInfo(track);
            }

            @Override
            public void onPlaying(Track track) {
                trackBar.setString(track.getFormattedProgress());
                trackBar.setValue((int) Math.round(track.getProgress()));
            }

            @Override
            public void onResumed(Track track) {
            }

            @Override
            public void onPaused(Track track) {
            }

            @Override
            public void onStarted() {
                tblSongs.setModel(new TMSongs());
                tblSongs.setDefaultRenderer(String.class, new TCRSongs());
                btnPlay.setIcon(new ImageIcon(getClass().getResource("/img/pause.png")));
            }

            @Override
            public void onStopped(Track track) {
            }

            @Override
            public void onSeeked(Track track) {
            }

            @Override
            public void onShutdown() {
            }
        });
    }
    
     private void configFileChooser() {
        musicChooser.setMultiSelectionEnabled(false);
        musicChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        musicChooser.setCurrentDirectory(new File("/home/martin/Escritorio/Música"));
        musicChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() && f.list() != null;
            }

            @Override
            public String getDescription() {
                return "Solo carpetas";
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFooter = new javax.swing.JPanel();
        btnPrev = new javax.swing.JButton();
        btnPlay = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        trackBar = new javax.swing.JProgressBar();
        lblTitleFooter = new javax.swing.JLabel();
        btnLoadMusic = new javax.swing.JButton();
        btnMute = new javax.swing.JButton();
        panelCenter = new javax.swing.JPanel();
        panelSong = new javax.swing.JPanel();
        lblCover = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        lblArtist = new javax.swing.JLabel();
        lblAlbum = new javax.swing.JLabel();
        panelList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSongs = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/prev.png"))); // NOI18N
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/play.png"))); // NOI18N
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/next.png"))); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        trackBar.setString("00:00");
        trackBar.setStringPainted(true);
        trackBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trackBarMouseClicked(evt);
            }
        });

        lblTitleFooter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleFooter.setText("Titulo - Artista");

        btnLoadMusic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/open-music.png"))); // NOI18N
        btnLoadMusic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadMusicActionPerformed(evt);
            }
        });

        btnMute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/unmute.png"))); // NOI18N
        btnMute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMuteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFooterLayout = new javax.swing.GroupLayout(panelFooter);
        panelFooter.setLayout(panelFooterLayout);
        panelFooterLayout.setHorizontalGroup(
            panelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFooterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPrev)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPlay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNext)
                .addGap(18, 18, 18)
                .addGroup(panelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(trackBar, javax.swing.GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
                    .addComponent(lblTitleFooter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLoadMusic, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(btnMute, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelFooterLayout.setVerticalGroup(
            panelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFooterLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnMute, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLoadMusic, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelFooterLayout.createSequentialGroup()
                        .addComponent(lblTitleFooter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trackBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnNext)
                        .addComponent(btnPlay)
                        .addComponent(btnPrev)))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        getContentPane().add(panelFooter, java.awt.BorderLayout.SOUTH);

        lblCover.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cover256.png"))); // NOI18N

        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Nada en Reproducción");

        lblArtist.setFont(new java.awt.Font("Droid Sans", 0, 16)); // NOI18N
        lblArtist.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblArtist.setText("Nada en Reproducción");

        lblAlbum.setFont(new java.awt.Font("Droid Sans", 0, 14)); // NOI18N
        lblAlbum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAlbum.setText("Nada en Reproducción");

        javax.swing.GroupLayout panelSongLayout = new javax.swing.GroupLayout(panelSong);
        panelSong.setLayout(panelSongLayout);
        panelSongLayout.setHorizontalGroup(
            panelSongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSongLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(panelSongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblCover, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAlbum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblArtist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        panelSongLayout.setVerticalGroup(
            panelSongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSongLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblCover, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblTitle)
                .addGap(18, 18, 18)
                .addComponent(lblArtist)
                .addGap(18, 18, 18)
                .addComponent(lblAlbum)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        tblSongs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Canciones"
            }
        ));
        tblSongs.setRowHeight(25);
        tblSongs.setRowMargin(10);
        tblSongs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblSongs.setShowGrid(false);
        tblSongs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSongsMouseClicked(evt);
            }
        });
        tblSongs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblSongsKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblSongs);

        javax.swing.GroupLayout panelListLayout = new javax.swing.GroupLayout(panelList);
        panelList.setLayout(panelListLayout);
        panelListLayout.setHorizontalGroup(
            panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelListLayout.setVerticalGroup(
            panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout panelCenterLayout = new javax.swing.GroupLayout(panelCenter);
        panelCenter.setLayout(panelCenterLayout);
        panelCenterLayout.setHorizontalGroup(
            panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCenterLayout.createSequentialGroup()
                .addComponent(panelSong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelCenterLayout.setVerticalGroup(
            panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCenterLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        getContentPane().add(panelCenter, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoadMusicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadMusicActionPerformed
        int showOpenDialog = musicChooser.showOpenDialog(this);
        
        if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
            File folder = musicChooser.getSelectedFile();
            
            if (folder != null) {
                player.addMusic(folder);
                if (!player.isAlive()) {
                    player.start();
                }
                TMSongs model = (TMSongs) tblSongs.getModel();
                model.setListTracks(player.getTracksInfo());
                tblSongs.setModel(model);
                tblSongs.updateUI();
                tblSongs.setDefaultRenderer(String.class, new TCRSongs());
            }
        }
        
    }//GEN-LAST:event_btnLoadMusicActionPerformed

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        if (player.isActive()) {
            if (player.isPlaying()) {
                player.pause();
                btnPlay.setIcon(new ImageIcon(getClass().getResource("/img/play.png")));
            }
            else {
                player.resumeTrack();
                btnPlay.setIcon(new ImageIcon(getClass().getResource("/img/pause.png")));
            }
            
        }
    }//GEN-LAST:event_btnPlayActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (player.isActive()) {
            player.playNext();
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if (player.isActive()) {
            player.playPrevious();
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void trackBarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trackBarMouseClicked
        if (player.isActive() && player.isPlaying()) {
            Rectangle bounds = trackBar.getBounds();
            final double minX = bounds.getMinX();
            final double maxX = bounds.getMaxX();
            final double width = maxX-minX;
            final double evtX = evt.getPoint().getX();
            final long duration = player.getCurrent().getDuration();
            final double result = (duration*evtX) / width;
            player.gotoSecond(result);
        }
    }//GEN-LAST:event_trackBarMouseClicked

    private void btnMuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMuteActionPerformed
        if (player.isActive() && player.isPlaying()) {
            if (player.isMute()) {
                player.unmute();
                btnMute.setIcon(new ImageIcon(getClass().getResource("/img/unmute.png")));
            }
            else {
                player.mute();
                btnMute.setIcon(new ImageIcon(getClass().getResource("/img/mute.png")));
            }
        }
    }//GEN-LAST:event_btnMuteActionPerformed

    private void tblSongsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSongsMouseClicked
        if (player.isActive() && player.hasSounds()) {
            if (evt.getClickCount() == 2) {
                int selectedRow = tblSongs.getSelectedRow();
                player.play(selectedRow);
            }
        }
    }//GEN-LAST:event_tblSongsMouseClicked

    private void tblSongsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblSongsKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (player.isActive() && player.hasSounds()) {
                int selectedRow = tblSongs.getSelectedRow();
                player.play(selectedRow);
            }
        }
    }//GEN-LAST:event_tblSongsKeyPressed

    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new MaterialLookAndFeel());
        
        java.awt.EventQueue.invokeLater(() -> {
            new FormPlayer().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoadMusic;
    private javax.swing.JButton btnMute;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnPrev;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAlbum;
    private javax.swing.JLabel lblArtist;
    private javax.swing.JLabel lblCover;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleFooter;
    private javax.swing.JPanel panelCenter;
    private javax.swing.JPanel panelFooter;
    private javax.swing.JPanel panelList;
    private javax.swing.JPanel panelSong;
    private javax.swing.JTable tblSongs;
    private javax.swing.JProgressBar trackBar;
    // End of variables declaration//GEN-END:variables
}
