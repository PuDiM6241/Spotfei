/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Album;
import Model.Music;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Classe responsável pelo acesso e manipulação de dados relacionados a álbuns,
 * músicas e artistas no banco de dados.
 * 
 * Métodos principais incluem:
 * - Recuperação de álbuns
 * - Inserção de álbuns e músicas
 * - Remoção de playlists e músicas de playlists
 * 
 * Utiliza PreparedStatements para evitar SQL Injection.
 * 
 * @author pudim
 */
public class AlbumDAO {
    private Connection conn;

    /**
     * Construtor da classe AlbumDAO.
     * 
     * @param conn Conexão com o banco de dados.
     */
    public AlbumDAO(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * Recupera todos os álbuns disponíveis, incluindo suas músicas, informações
     * do artista e quantidades de likes e dislikes.
     * 
     * @return Lista de álbuns.
     * @throws SQLException Se ocorrer erro de acesso ao banco de dados.
     */
    public ArrayList<Album> getAlbums() throws SQLException{
        ArrayList<Album> albums = new ArrayList();
        ArrayList<Music> musics = new ArrayList();
        int like = 0, deslike = 0;
        String artist = "Não encontrado";
        String sql = "SELECT * FROM vAlbumArtistPersonMusic ORDER BY username, albumName";
        String sqlMusics = "SELECT * FROM vAlbumMusic WHERE albumId = ?";
        String sqlArtist = "SELECT * FROM person WHERE id = ?";
        String sqlLikes = "SELECT * FROM vMusicLikeQuantity WHERE musicId = ?";
        String sqlDeslikes = "SELECT * FROM vMusicDeslikeQuantity WHERE musicId = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        PreparedStatement stmtMusics = null;
        ResultSet rstMusics = null;
        PreparedStatement stmtArtist = null;
        ResultSet rstArtist = null;
        PreparedStatement stmtLikes = null;
        ResultSet rstLikes = null;
        PreparedStatement stmtDeslikes = null;
        ResultSet rstDeslikes = null;
        
        try {
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()){
                try {
                    stmtMusics = conn.prepareStatement(sqlMusics);
                    stmtMusics.setInt(1, rs.getInt("albumId"));
                    rstMusics = stmtMusics.executeQuery();
                    
                    while (rstMusics.next()){
                        
                        try{
                            stmtArtist = conn.prepareStatement(sqlArtist);
                            stmtArtist.setInt(1, rstMusics.getInt("artistId"));
                            rstArtist = stmtArtist.executeQuery();
                            if(rstArtist.next()){
                                artist = rstArtist.getString("username");
                            } 
                            else System.out.println("NÃO FOI ENCONTRADO ARTISTA PROBLEMAAA");
                            
                        } finally{
                            if (rstArtist != null) rstArtist.close();
                            if (stmtArtist != null) stmtArtist.close();
                        }
                        
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
                        
                        musics.add(new Music(rstMusics.getInt("MusicId"), rstMusics.getString("MusicName"), rstMusics.getInt("artistId"), artist, rstMusics.getString("MusicGenre"), like, deslike));

                    }
                    
                } finally {//finally mesmo pqp
                    if (rstMusics != null) rstMusics.close();
                    if (stmtMusics != null) stmtMusics.close();
                }
                albums.add(new Album(rs.getInt("albumId"), rs.getString("AlbumName"), new ArrayList<>(musics), rs.getString("username"), rs.getInt("id")));
                musics.clear();
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
        }
        return albums;
    }
    
    
    /**
     * Recupera um álbum específico com base no nome fornecido.
     * Inclui as músicas associadas, informações do artista e estatísticas de
     * likes e dislikes.
     * 
     * @param value Nome do álbum.
     * @return Objeto Album correspondente, ou null se não encontrado.
     * @throws SQLException Se ocorrer erro de acesso ao banco de dados.
     */
    public Album getAlbum(String value) throws SQLException{
        Album album = null;
        ArrayList<Music> musics = new ArrayList();
        int like = 0, deslike = 0;
        String artist = "Não encontrado";
        String sql = "SELECT * FROM vAlbumArtistPersonMusic WHERE LOWER(albumName) = LOWER(?)";
        String sqlMusics = "SELECT * FROM vAlbumMusic WHERE albumId = ?";
        String sqlArtist = "SELECT * FROM person WHERE id = ?";
        String sqlLikes = "SELECT * FROM vMusicLikeQuantity WHERE musicId = ?";
        String sqlDeslikes = "SELECT * FROM vMusicDeslikeQuantity WHERE musicId = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        PreparedStatement stmtMusics = null;
        ResultSet rstMusics = null;
        PreparedStatement stmtArtist = null;
        ResultSet rstArtist = null;
        PreparedStatement stmtLikes = null;
        ResultSet rstLikes = null;
        PreparedStatement stmtDeslikes = null;
        ResultSet rstDeslikes = null;
        
        try {
            stm = conn.prepareStatement(sql);
            stm.setString(1, value);
            rs = stm.executeQuery();

            if (rs.next()){
                try {
                    stmtMusics = conn.prepareStatement(sqlMusics);
                    stmtMusics.setInt(1, rs.getInt("albumId"));
                    rstMusics = stmtMusics.executeQuery();
                    
                    while (rstMusics.next()){
                        
                        try{
                            stmtArtist = conn.prepareStatement(sqlArtist);
                            stmtArtist.setInt(1, rstMusics.getInt("artistId"));
                            rstArtist = stmtArtist.executeQuery();
                            if(rstArtist.next()){
                                artist = rstArtist.getString("username");
                            } 
                            else System.out.println("NÃO FOI ENCONTRADO ARTISTA PROBLEMAAA");
                            
                        } finally{
                            if (rstArtist != null) rstArtist.close();
                            if (stmtArtist != null) stmtArtist.close();
                        }
                        
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
                        
                        musics.add(new Music(rstMusics.getInt("MusicId"), rstMusics.getString("MusicName"), rstMusics.getInt("artistId"), artist, rstMusics.getString("MusicGenre"), like, deslike));

                    }
                    
                } finally {//finally mesmo pqp
                    if (rstMusics != null) rstMusics.close();
                    if (stmtMusics != null) stmtMusics.close();
                }
                album = new Album(rs.getInt("albumId"), rs.getString("AlbumName"), new ArrayList<Music>(musics), rs.getString("username"), rs.getInt("Id"));
                musics.clear();
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
        }
        return album;
    }
    
    
    /**
     * Insere um novo álbum no banco de dados após verificar se o proprietário
     * (owner) existe e se o nome do álbum está disponível.
     * 
     * @param ownerId ID do proprietário do álbum.
     * @param albumName Nome do álbum a ser inserido.
     * @return Mensagem indicando o sucesso ou falha da operação.
     * @throws SQLException Se ocorrer erro de acesso ao banco de dados.
     */
    public String insertAlbum(int ownerId, String albumName) throws SQLException{
        String sql = "INSERT INTO album (ownerId, name) VALUES (?, ?)";
        String sqlOwner = "SELECT * FROM person WHERE id = ?";
        String sqlAlbum = "SELECT * FROM album WHERE LOWER(name) = LOWER(?)";
        boolean ownerExists, albumExists;
        PreparedStatement stm = null;
        PreparedStatement stmtOwner = null; 
        ResultSet rstOwner = null;
        PreparedStatement stmtAlbum = null; 
        ResultSet rstAlbum = null;

        try {
            //verifica se owner(person) existe
            stmtOwner = conn.prepareStatement(sqlOwner);
            stmtOwner.setInt(1, ownerId);
            rstOwner = stmtOwner.executeQuery();
            ownerExists = rstOwner.next();
        } finally {
            if (rstOwner != null) rstOwner.close();
            if (stmtOwner != null) stmtOwner.close();
        }

        if(!ownerExists) return "OwnerId não encontrado";
            
        try {
            //verifica se owner(person) existe
            stmtAlbum = conn.prepareStatement(sqlAlbum);
            stmtAlbum.setString(1, albumName);
            rstAlbum = stmtAlbum.executeQuery();
            albumExists = rstAlbum.next();
        } finally {
            if (rstAlbum != null) rstAlbum.close();
            if (stmtAlbum != null) stmtAlbum.close();
        }

        if(albumExists) return "nome de album não disponivel";
        
        try {

            stm = conn.prepareStatement(sql);
            stm.setInt(1, ownerId);
            stm.setString(2, albumName);
            int inserted = stm.executeUpdate();
            if (inserted > 0) {
                return "Album foi inserido";
            }
            else{
                return "Nenhum album foi inserido";
            }
        } catch (SQLException e){
            return e.toString();
        } finally {
            if (stm != null) stm.close();
        }
    }
    
    
    /**
     * Insere uma música em um álbum. Caso o álbum não exista, ele será criado.
     * Também verifica se o usuário é o proprietário do álbum antes da inserção.
     * 
     * @param albumName Nome do álbum.
     * @param musicId ID da música a ser inserida.
     * @param userId ID do usuário que está realizando a operação.
     * @return Mensagem indicando o sucesso ou falha da operação.
     * @throws SQLException Se ocorrer erro de acesso ao banco de dados.
     */
    public String insertMusicAlbum(String albumName, int musicId, int userId) throws SQLException{
        int albumId = -1;
        String sql = "INSERT INTO albumMusic (albumId, musicId) VALUES (?, ?)";
        String sqlMusic = "SELECT * FROM music WHERE id = ?";
        String sqlAlbum = "SELECT * FROM album WHERE LOWER(name) = LOWER(?)";
        boolean musicExists, albumExists, albumOwner = false;
        PreparedStatement stm = null;
        PreparedStatement stmtMusic = null; 
        ResultSet rstMusic = null;
        PreparedStatement stmtAlbum = null; 
        ResultSet rstAlbum = null;

        try {
            //verifica se musicId existe
            stmtMusic = conn.prepareStatement(sqlMusic);
            stmtMusic.setInt(1, musicId);
            rstMusic = stmtMusic.executeQuery();
            musicExists = rstMusic.next();
        } finally {
            if (rstMusic != null) rstMusic.close();
            if (stmtMusic != null) stmtMusic.close();
        }

        if(!musicExists) return "musicId não encontrado";
        
        try {
            //verifica se albumId existe e se o dono que etsa tentando alterar caso exista
            stmtAlbum = conn.prepareStatement(sqlAlbum);
            stmtAlbum.setString(1, albumName);
            rstAlbum = stmtAlbum.executeQuery();
            albumExists = rstAlbum.next();
            if(albumExists){
                albumId = rstAlbum.getInt("id");
                albumOwner = rstAlbum.getInt("ownerId") == userId;
            }
            else this.insertAlbum(userId, albumName);
        } finally {
            if (rstAlbum != null) rstAlbum.close();
            if (stmtAlbum != null) stmtAlbum.close();
        }
        
        try {
            //verifica se albumId existe e se o dono que etsa tentando alterar caso exista
            stmtAlbum = conn.prepareStatement(sqlAlbum);
            stmtAlbum.setString(1, albumName);
            rstAlbum = stmtAlbum.executeQuery();
            albumExists = rstAlbum.next();
            if(albumExists){
                albumId = rstAlbum.getInt("id");
                albumOwner = rstAlbum.getInt("ownerId") == userId;
            }
            else return "album não encontrado";
        } finally {
            if (rstAlbum != null) rstAlbum.close();
            if (stmtAlbum != null) stmtAlbum.close();
        }
        
        if(!albumOwner) return "você não pode modificar esse album";
        
        try {

            stm = conn.prepareStatement(sql);
            stm.setInt(1, albumId);
            stm.setInt(2, musicId);
            int inserted = stm.executeUpdate();
            if (inserted > 0) {
                return "AlbumMusic foi inserido";
            }
            else{
                return "Nenhum albumMusic foi inserido";
            }
        } catch (SQLException e){
            return e.toString();
        } finally {
            if (stm != null) stm.close();
        }
    }

    /**
     * Deleta uma playlist (álbum) com base no nome, desde que o usuário seja o
     * proprietário.
     * 
     * @param playlistName Nome da playlist.
     * @param userId ID do usuário que está tentando deletar.
     * @return Mensagem indicando o sucesso ou falha da operação.
     * @throws SQLException Se ocorrer erro de acesso ao banco de dados.
     */
    public String deletePlaylist(String playlistName, int userId) throws SQLException {
        String sql = "DELETE FROM album WHERE LOWER(name) = LOWER(?);";
        String sqlOwner = "Select * FROM album where LOWER(name) = LOWER(?)";
        boolean owner = false;
        PreparedStatement stm = null;
        PreparedStatement stmtOwner = null; 
        ResultSet rstOwner = null;

        try {
            //verifica se pode alterar a playlist
            stmtOwner = conn.prepareStatement(sqlOwner);
            stmtOwner.setString(1, playlistName);
            rstOwner = stmtOwner.executeQuery();
            if(rstOwner.next()) owner = rstOwner.getInt("ownerId") == userId;
            else return "playlist não encontrada";
        } finally {
            if (rstOwner != null) rstOwner.close();
            if (stmtOwner != null) stmtOwner.close();
        }

        if(!owner) return "Não pode modificar playlist não criada por você";
        
        try {

            stm = conn.prepareStatement(sql);
            stm.setString(1, playlistName);
            int deleted = stm.executeUpdate();
            if (deleted > 0) {
                return "deletado";
            }
            else{
                return "Nenhum deletado";
            }
        } catch (SQLException e){
            return e.toString();
        } finally {
            if (stm != null) stm.close();
        }
    }

    /**
     * Remove uma música de uma playlist (álbum), desde que o usuário seja o
     * proprietário da playlist.
     * 
     * @param musicId ID da música a ser removida.
     * @param playlistName Nome da playlist.
     * @param userId ID do usuário que está tentando remover a música.
     * @return Mensagem indicando o sucesso ou falha da operação.
     * @throws SQLException Se ocorrer erro de acesso ao banco de dados.
     */
    public String deleteMusicPlaylist(int musicId, String playlistName, int userId) throws SQLException {
        String sql = "DELETE FROM albumMusic WHERE musicId = ? AND albumId = ?;";
        String sqlAlbum = "Select * FROM album where LOWER(name) = LOWER(?)";
        int albumId = 0;
        boolean owner = false;
        PreparedStatement stm = null;
        PreparedStatement stmtAlbum = null; 
        ResultSet rstAlbum = null;

        try {
            //verifica se existe a playlist e pega o albumId
            stmtAlbum = conn.prepareStatement(sqlAlbum);
            stmtAlbum.setString(1, playlistName);
            rstAlbum = stmtAlbum.executeQuery();
            if(rstAlbum.next()){
                albumId = rstAlbum.getInt("id");
                owner = rstAlbum.getInt("ownerId") == userId;
            }
            else return "playlist não encontrada";
        } finally {
            if (rstAlbum != null) rstAlbum.close();
            if (stmtAlbum != null) stmtAlbum.close();
        }

        if(!owner) return "Não pode modificar playlist não criada por você";
        
        try {

            stm = conn.prepareStatement(sql);
            stm.setInt(1, musicId);
            stm.setInt(2, albumId);
            int deleted = stm.executeUpdate();
            if (deleted > 0) {
                return "deletado";
            }
            else{
                return "Nenhum deletado";
            }
        } catch (SQLException e){
            return e.toString();
        } finally {
            if (stm != null) stm.close();
        }
    }
}
