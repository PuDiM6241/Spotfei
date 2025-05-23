/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author unifdloureiro
 */
/**
 * Representa uma música com informações sobre o artista, gênero e avaliações.
 */
public class Music {
    private int id;
    private String name;
    private int artistid;
    private String artistName;
    private String genre;
    private int likes;
    private int desLikes;

    /**
     * Construtor para criar uma instância de Music.
     * 
     * @param id Identificador da música
     * @param name Nome da música
     * @param artistid Identificador do artista
     * @param artistName Nome do artista
     * @param genre Gênero musical
     * @param likes Quantidade de likes
     * @param desLikes Quantidade de deslikes
     */
    public Music(int id, String name, int artistid, String artistName, String genre, int likes, int desLikes) {
        this.id = id;
        this.name = name;
        this.artistid = artistid;
        this.artistName = artistName;
        this.genre = genre;
        this.likes = likes;
        this.desLikes = desLikes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getArtistid() {
        return artistid;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getGenre() {
        return genre;
    }

    public int getLikes() {
        return likes;
    }

    public int getDesLikes() {
        return desLikes;
    }

    @Override
    public String toString() {
        return "Music{" + "id=" + id + ", name=" + name + ", artistid=" + artistid + ", artistName=" + artistName + ", genre=" + genre + ", likes=" + likes + ", desLikes=" + desLikes + '}';
    }
}
