/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controler;

import DAO.AlbumDAO;
import DAO.Connect;
import DAO.HistoricDAO;
import DAO.LikeDAO;
import DAO.MusicArtistDAO;
import Model.Music;
import Model.MusicTableModel;
import Model.User;
import View.VSpotfei;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author unifdloureiro
 */
/**
 * Controlador principal da aplicação, gerencia interações com músicas, playlists e histórico.
 */
public class CSpotfei {
    private final VSpotfei v;
    private final User loggedUser;
    ArrayList<Music> musics;
    MusicArtistDAO maDAO;
    AlbumDAO albumDAO;
    LikeDAO likeDAO;
    HistoricDAO hDAO;

    /**
     * Construtor: inicializa a view e carrega as músicas.
     * @param v View principal
     * @param loggedUser Usuário logado
     */
    public CSpotfei(VSpotfei v, User loggedUser) {
        this.v = v;
        this.loggedUser = loggedUser;
        loadMusics();
    }
    
    /**
     * Carrega as músicas ou faz busca, atualizando a interface.
     */
    public void loadMusics(){
        Connect connect = new Connect();
        if(v.getTxtSearch().getText().length() == 0){
            try{
                Connection conn = connect.getConnection();
                maDAO = new MusicArtistDAO(conn);
                musics = maDAO.getMusics();
            }catch(SQLException e){
                System.out.println(e.toString());
            }
        } else{
            try{
                Connection conn = connect.getConnection();
                try {
                    hDAO = new HistoricDAO(conn);
                    hDAO.InsertPersonHistoric(loggedUser.getId(), v.getTxtSearch().getText());
                } catch(SQLException e){
                System.out.println(e.toString());
                }
                maDAO = new MusicArtistDAO(conn);
                musics = maDAO.getMusic(v.getTxtSearch().getText());
            }catch(SQLException e){
                System.out.println(e.toString());
            }
        }
        MusicTableModel home = new MusicTableModel(musics);
        v.getSpMain().setViewportView(new JTable(home));
        v.getSpMain().revalidate();
        v.getSpMain().repaint();
    }
    
    /**
     * Adiciona uma música a uma playlist.
     */
    public void addMusicAlbum(){
        Connect connect = new Connect();
        try{
            Connection conn = connect.getConnection();
            albumDAO = new AlbumDAO(conn);
            if((int)v.getsMusicIdPlaylist().getValue() <= 0){
                v.getTxtaInfo().setText("MusicId Inválido");
                return;
            }
            if (v.getTxtPlaylistName().getText().length() == 0){
                v.getTxtaInfo().setText("PlaylistName Inválido");
                return;
            }
            v.getTxtaInfo().setText(albumDAO.insertMusicAlbum(v.getTxtPlaylistName().getText(), (int)v.getsMusicIdPlaylist().getValue(), loggedUser.getId()));
        }catch(SQLException e){
            System.out.println(e.toString());
        }
    }

    /**
     * Registra um like em uma música.
     */
    public void likeMusic() {
        Connect connect = new Connect();
        try{
            Connection conn = connect.getConnection();
            likeDAO = new LikeDAO(conn);
            if((int)v.getsMusicIdLike().getValue() < 0){
                v.getTxtaInfo().setText("MusicId Inválido");
                return;
            }
            v.getTxtaInfo().setText(likeDAO.InsertLike((int)v.getsMusicIdLike().getValue(), loggedUser.getId(), true));
        }catch(SQLException e){
            System.out.println(e.toString());
        }
    }
    
    /**
     * Registra um dislike em uma música.
     */
    public void deslikeMusic() {
        Connect connect = new Connect();
        try{
            Connection conn = connect.getConnection();
            likeDAO = new LikeDAO(conn);
            if((int)v.getsMusicIdLike().getValue() < 0){
                v.getTxtaInfo().setText("MusicId Inválido");
                return;
            }
            v.getTxtaInfo().setText(likeDAO.InsertLike((int)v.getsMusicIdLike().getValue(), loggedUser.getId(), false));
        }catch(SQLException e){
            System.out.println(e.toString());
        }
    }

    /**
     * Exibe o histórico de buscas do usuário.
     */
    public void showSearchHistory() {
        Connect connect = new Connect();
        try{
            Connection conn = connect.getConnection();
            hDAO = new HistoricDAO(conn);
            ArrayList<String> historic = hDAO.getLastPersonHistoric(loggedUser.getId());
            String a = "historico de pesquisa:\n";
            for(String his : historic){
                a += his + "\n";
            }
            v.getTxtaInfo().setText(a);
        }catch(SQLException e){
            System.out.println(e.toString());
        }
    }
}
