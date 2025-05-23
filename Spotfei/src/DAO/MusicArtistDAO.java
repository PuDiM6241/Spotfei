/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Music;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe responsável por operações relacionadas à música e artistas no banco de dados.
 * Permite buscar músicas, buscar músicas por filtro e inserir novas músicas.
 * 
 * @author pudim
 */
public class MusicArtistDAO {
    private Connection conn;

    /**
     * Construtor que inicializa o DAO com uma conexão de banco de dados existente.
     * 
     * @param conn Conexão com o banco de dados.
     */
    public MusicArtistDAO(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * Retorna uma lista das últimas músicas cadastradas, incluindo quantidade de likes e deslikes.
     * 
     * @return ArrayList de objetos Music contendo as músicas e informações relacionadas.
     * @throws SQLException Caso ocorra erro durante a operação no banco de dados.
     */
    public ArrayList<Music> getMusics() throws SQLException{
        ArrayList<Music> musics = new ArrayList();
        int like = 0, deslike = 0;
        String sqlMusics = "SELECT * FROM vPersonArtistMusic";
        String sqlLikes = "SELECT * FROM vMusicLikeQuantity WHERE musicId = ?";
        String sqlDeslikes = "SELECT * FROM vMusicDeslikeQuantity WHERE musicId = ?";
        PreparedStatement stmtMusics = null;
        ResultSet rstMusics = null;
        PreparedStatement stmtLikes = null;
        ResultSet rstLikes = null;
        PreparedStatement stmtDeslikes = null;
        ResultSet rstDeslikes = null;
        
        try {
            stmtMusics = conn.prepareStatement(sqlMusics);
            rstMusics = stmtMusics.executeQuery();

            while (rstMusics.next()){

                try{
                    //pegar like
                    stmtLikes = conn.prepareStatement(sqlLikes);
                    stmtLikes.setInt(1, rstMusics.getInt("musicId"));
                    rstLikes = stmtLikes.executeQuery();
                    if(rstLikes.next()){
                        like = rstLikes.getInt("likeQuantity");
                    } 
                    else like = 0;

                } finally{
                    if (rstLikes != null) rstLikes.close();
                    if (stmtLikes != null) stmtLikes.close();
                }

                try{
                    //pegar deslike
                    stmtDeslikes = conn.prepareStatement(sqlDeslikes);
                    stmtDeslikes.setInt(1, rstMusics.getInt("musicId"));
                    rstDeslikes = stmtDeslikes.executeQuery();
                    if(rstDeslikes.next()){
                        deslike = rstDeslikes.getInt("deslikeQuantity");
                    } 
                    else deslike = 0;

                } finally{
                    if (rstDeslikes != null) rstDeslikes.close();
                    if (stmtDeslikes != null) stmtDeslikes.close();
                }
                
                musics.add(new Music(rstMusics.getInt("MusicId"), rstMusics.getString("MusicName"), rstMusics.getInt("id"), rstMusics.getString("username"), rstMusics.getString("musicGenre"), like, deslike));

            }

        } finally {//finally mesmo pqp
            if (rstMusics != null) rstMusics.close();
            if (stmtMusics != null) stmtMusics.close();
        }
        return musics;
    }
    
    /**
     * Busca músicas pelo nome do artista, gênero da música ou nome da música.
     * A busca é case insensitive.
     * 
     * @param value Valor para busca (pode ser artista, gênero ou nome da música).
     * @return ArrayList de objetos Music que correspondem ao filtro.
     * @throws SQLException Caso ocorra erro durante a operação no banco de dados.
     */
    public ArrayList<Music> getMusic(String value) throws SQLException{
        ArrayList<Music> music = new ArrayList();
        int like = 0, deslike = 0;

        String sqlMusics = "SELECT * FROM vPersonArtistMusic WHERE LOWER(username) =  LOWER(?) OR LOWER(musicGenre) = LOWER(?) OR LOWER(musicName) = LOWER(?)";
        String sqlLikes = "SELECT * FROM vMusicLikeQuantity WHERE musicId = ?";
        String sqlDeslikes = "SELECT * FROM vMusicDeslikeQuantity WHERE musicId = ?";
        PreparedStatement stmtMusic = null;
        ResultSet rstMusic = null;
        PreparedStatement stmtLikes = null;
        ResultSet rstLikes = null;
        PreparedStatement stmtDeslikes = null;
        ResultSet rstDeslikes = null;
        
        try {
            stmtMusic = conn.prepareStatement(sqlMusics);
            stmtMusic.setString(1, value);
            stmtMusic.setString(2, value);
            stmtMusic.setString(3, value);
            rstMusic = stmtMusic.executeQuery();

            while (rstMusic.next()){

                try{
                    //pegar like
                    stmtLikes = conn.prepareStatement(sqlLikes);
                    stmtLikes.setInt(1, rstMusic.getInt("musicId"));
                    rstLikes = stmtLikes.executeQuery();
                    if(rstLikes.next()){
                        like = rstLikes.getInt("likeQuantity");
                    } 
                    else like = 0;

                } finally{
                    if (rstLikes != null) rstLikes.close();
                    if (stmtLikes != null) stmtLikes.close();
                }

                try{
                    //pegar deslike
                    stmtDeslikes = conn.prepareStatement(sqlDeslikes);
                    stmtDeslikes.setInt(1, rstMusic.getInt("musicId"));
                    rstDeslikes = stmtDeslikes.executeQuery();
                    if(rstDeslikes.next()){
                        deslike = rstDeslikes.getInt("deslikeQuantity");
                    } 
                    else deslike = 0;

                } finally{
                    if (rstDeslikes != null) rstDeslikes.close();
                    if (stmtDeslikes != null) stmtDeslikes.close();
                }

                music.add(new Music(rstMusic.getInt("MusicId"), rstMusic.getString("MusicName"), rstMusic.getInt("id"), rstMusic.getString("username"), rstMusic.getString("musicGenre"), like, deslike));

            }

        } finally {//finally mesmo pqp
            if (rstMusic != null) rstMusic.close();
            if (stmtMusic != null) stmtMusic.close();
        }
        return music;
    }

    /**
     * Insere uma nova música associada a um artista existente.
     * Antes da inserção, verifica se o artista existe no banco.
     * 
     * @param name Nome da música.
     * @param artistId ID do artista associado.
     * @param genre Gênero da música.
     * @return Mensagem indicando sucesso ou erro da operação.
     * @throws SQLException Caso ocorra erro durante a operação no banco de dados.
     */
    public String InsertMusic(String name, int artistId, String genre) throws SQLException{
        String sql = "INSERT INTO music (name, artistId, genre) VALUES (?, ?, ?)";
        String sqlArtist = "SELECT * FROM artist WHERE artistId = ?";
        boolean artistExists;
        PreparedStatement stm = null;
        PreparedStatement stmtArtist = null; 
        ResultSet rstArtist = null;

        try {
            //verifica se username existe
            stmtArtist = conn.prepareStatement(sqlArtist);
            stmtArtist.setInt(1, artistId);
            rstArtist = stmtArtist.executeQuery();
            artistExists = rstArtist.next();
        } finally {
            if (rstArtist != null) rstArtist.close();
            if (stmtArtist != null) stmtArtist.close();
        }

        if(!artistExists) return "ArtistaId não encontrado";
            
        
        try {

            stm = conn.prepareStatement(sql);
            stm.setString(1, name);
            stm.setInt(2, artistId);
            stm.setString(3, genre);
            int inserted = stm.executeUpdate();
            if (inserted > 0) {
                return "Music foi inserido";
            }
            else{
                return "Nenhum music foi inserido";
            }
        } catch (SQLException e){
            return e.toString();
        } finally {
            if (stm != null) stm.close();
        }
    }
}
