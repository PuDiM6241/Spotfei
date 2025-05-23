/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controler;

import DAO.AlbumDAO;
import DAO.Connect;
import DAO.HistoricDAO;
import Model.Album;
import Model.AlbumTableModel;
import Model.MusicTableModel;
import Model.User;
import View.VPlaylists;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTable;

/**
 *
 * @author pudim
 */
/**
 * Controlador das playlists, conecta a View e os DAOs.
 */
public class CPlaylists {
    /** Usuário logado. */
    User loggedUser;
    
    VPlaylists v;
    
    /** Álbum usado nas buscas. */
    Album album;
    ArrayList<Album> albums;
    AlbumDAO albumDAO;
    HistoricDAO hDAO;
    
    /**
     * Construtor: inicializa view, usuário e carrega playlists.
     * @param v View
     * @param loggedUser Usuário logado
     */
    public CPlaylists(VPlaylists v, User loggedUser) {
        this.v = v;
        this.loggedUser = loggedUser;
        loadPlaylists();
    }
    

    /** Carrega playlists ou busca específica. */
    public void loadPlaylists() {
        Connect connect = new Connect();
        if(v.getTxtSearch().getText().length() == 0){
            try{
                Connection conn = connect.getConnection();
                albumDAO = new AlbumDAO(conn);
                albums = albumDAO.getAlbums();
            }catch(SQLException e){
                System.out.println(e.toString());
            }
            AlbumTableModel home = new AlbumTableModel(albums);
            v.getSpMain().setViewportView(new JTable(home));
            v.getSpMain().revalidate();
            v.getSpMain().repaint();
        } else{
            try{
                Connection conn = connect.getConnection();
                try {
                    hDAO = new HistoricDAO(conn);
                    hDAO.InsertPersonHistoric(loggedUser.getId(), v.getTxtSearch().getText());
                } catch(SQLException e){
                System.out.println(e.toString());
                }
                albumDAO = new AlbumDAO(conn);
                album = albumDAO.getAlbum(v.getTxtSearch().getText());
                System.out.println(album.getMusics().size());
            }catch(SQLException e){
                System.out.println(e.toString());
            }
            
            MusicTableModel home = new MusicTableModel(album.getMusics());
            v.getSpMain().setViewportView(new JTable(home));
            v.getSpMain().revalidate();
            v.getSpMain().repaint();
        }
        
    }
    
    /** Mostra histórico de buscas do usuário. */
    public void showSearchHistory() {
        Connect connect = new Connect();
        try{
            Connection conn = connect.getConnection();
            hDAO = new HistoricDAO(conn);
            ArrayList<String> historic = hDAO.getLastPersonHistoric(loggedUser.getId());
            String a = "";
            for(String his : historic){
                a += his + "\n";
            }
            v.getTxtaInfo().setText(a);
        }catch(SQLException e){
            System.out.println(e.toString());
        }
    }
    
    /** Deleta uma playlist do usuário. */
    public void deletePlaylist() {
        Connect connect = new Connect();
        try{
            Connection conn = connect.getConnection();
            v.getTxtaInfo().setText(albumDAO.deletePlaylist(v.getTxtPlaylistName().getText(), loggedUser.getId()));
        }catch(SQLException e){
            System.out.println(e.toString());
        }
    }

    /** Deleta música de uma playlist do usuário. */
    public void deleteMusicPlaylist() {
        if(v.getTxtPlaylistName().getText().length() > 0){
            Connect connect = new Connect();
            try{
                Connection conn = connect.getConnection();
                v.getTxtaInfo().setText(albumDAO.deleteMusicPlaylist(Integer.parseInt(v.getTxtMusicId().getText()),v.getTxtPlaylistName().getText(), loggedUser.getId()));
            }catch(SQLException e){
                System.out.println(e.toString());
            }
        }
        else{
            v.getTxtaInfo().setText("é preciso do nome da playlist e ser o dono da playlist para deletar uma musica de uma playlist");
        }
        
    }
}
