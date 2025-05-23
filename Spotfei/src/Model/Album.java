/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;

/**
 * Representa um álbum musical contendo uma lista de músicas, seu dono e informações básicas.
 * 
 * @author pudim
 */
public class Album {
    private int id;
    private String name;
    private ArrayList<Music> musics;
    private String owner;
    private int ownerId;

    /**
     * Construtor da classe Album.
     * 
     * @param id Identificador do álbum.
     * @param name Nome do álbum.
     * @param musics Lista de músicas que pertencem ao álbum.
     * @param owner Nome do proprietário do álbum.
     * @param onwerId ID do proprietário do álbum.
     */
    public Album(int id, String name, ArrayList<Music> musics, String owner, int onwerId) {
        this.id = id;
        this.name = name;
        this.musics = musics;
        this.owner = owner;
        this.ownerId = onwerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public ArrayList<Music> getMusics() {
        return musics;
    }

    public void setMusics(ArrayList<Music> musics) {
        this.musics = musics;
    }

    public String getOwner() {
        return owner;
    }
    
    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public int getOwnerId() {
        return ownerId;
    }
    
    public void setOwnerId(int onwerId) {
        this.ownerId = onwerId;
    }

    @Override
    public String toString() {
        return "Album{" + "id=" + id + ", name=" + name + ", musics=" + musics + ", owner=" + owner + ", onwerId=" + ownerId + '}';
    }

}
