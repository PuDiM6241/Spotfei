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
 * Representa um usuário do sistema, que é uma pessoa com funcionalidades de autenticação e playlists.
 */
public class User extends Person implements Authentication{
    private ArrayList<Album> playlists;
    
    /**
     * Construtor que cria um usuário com nome de usuário e senha.
     * 
     * @param username nome de usuário
     * @param pass senha
     */
    public User(String username, String pass){
        super(username, pass);
    }
    
    /**
     * Autentica o usuário comparando com uma lista de usuários.
     * 
     * @param users lista de usuários para verificação
     * @return true se as credenciais forem válidas, false caso contrário
     */
    @Override
    public boolean auth(ArrayList<User> users){
        for(User user : users){
            if(user.username.equalsIgnoreCase(this.username) && user.pass.equals(this.pass)){
                this.username = user.username;
                this.id = user.id;
                this.name = user.name;
                this.age = user.age;
                return true;
            }
        }
        return false;
    }

    public ArrayList<Album> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Album> playlists) {
        this.playlists = playlists;
    }

    @Override
    public String toString() {
        return "User{" + super.toString() + "likes=" + ", desLikes=" + ", playlists=" + playlists.size() + '}';
    }
}
