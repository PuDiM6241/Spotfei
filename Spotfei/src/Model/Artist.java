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
 * Representa um artista, que é uma extensão de Person.
 */
public class Artist extends Person{
    private int id;
    private String genre;
    ArrayList<Album> albums;

    /**
     * Construtor que inicializa um artista com username e senha.
     * @param username nome de usuário
     * @param pass senha
     */
    public Artist(String username, String pass) {
        super(username, pass);
    }
}
