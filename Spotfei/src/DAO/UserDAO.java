/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import Model.User;
import java.util.ArrayList;

/**
 * Classe responsável pelas operações relacionadas aos usuários no banco de dados.
 * Permite obter a lista de usuários, buscar um usuário por username e inserir novos usuários.
 * 
 * @author pudim
 */
public class UserDAO{
    private Connection conn;

    /**
     * Construtor que inicializa o DAO com uma conexão de banco de dados.
     * 
     * @param conn Conexão com o banco de dados.
     */
    public UserDAO(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * Recupera todos os usuários cadastrados.
     * 
     * @return Lista de objetos User com os dados dos usuários.
     * @throws SQLException Caso ocorra erro na consulta ao banco de dados.
     */
    public ArrayList<User> getUsers() throws SQLException{
        ArrayList<User> users = new ArrayList();
        String sql = "SELECT * FROM vUser";
        PreparedStatement stm = null; 
        ResultSet rs = null;

        try {
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()){
                users.add(new User(rs.getString("username"), rs.getString("pass")));
                users.getLast().setId(rs.getInt("id"));
                users.getLast().setName(rs.getString("name"));
                users.getLast().setAge(rs.getInt("age"));
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
        }
        return users;
    }
    
    /**
     * Busca um usuário pelo username, ignorando maiúsculas/minúsculas.
     * 
     * @param username Nome do usuário a ser buscado.
     * @return Objeto User encontrado ou null se não existir.
     * @throws SQLException Caso ocorra erro na consulta ao banco de dados.
     */
    public User getUser(String username) throws SQLException{
        User user = null;
        String sql = "SELECT * FROM vUser WHERE LOWER(username) = LOWER(?)";
        PreparedStatement stm = null; 
        ResultSet rs = null;

        try {
            stm = conn.prepareStatement(sql);
            stm.setString(1, username);
            rs = stm.executeQuery();

            if(rs.next()){
                user = new User(rs.getString("username"), rs.getString("pass"));
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
        }
        return user;
    }
    
     /**
     * Insere um novo usuário no banco, caso o username não esteja em uso.
     * 
     * @param name Nome do usuário.
     * @param age Idade do usuário.
     * @param username Nome de usuário (único).
     * @param pass Senha do usuário.
     * @return Mensagem informando o sucesso ou motivo do erro.
     * @throws SQLException Caso ocorra erro na inserção.
     */
    public String InsertUser(String name, int age, String username, String pass) throws SQLException{
        String sql = "INSERT INTO person (name, age, username, pass) VALUES (?, ?, ?, ?)";
        String sqlUsername = "SELECT * FROM person WHERE LOWER(username) = LOWER(?)";
        boolean alredyExists;
        PreparedStatement stm = null;
        PreparedStatement stmtUsername = null; 
        ResultSet rstUsername = null;

        try {
            //verifica se username existe
            stmtUsername = conn.prepareStatement(sqlUsername);
            stmtUsername.setString(1, username);
            rstUsername = stmtUsername.executeQuery();
            alredyExists = rstUsername.next();
        } finally {
            if (rstUsername != null) rstUsername.close();
            if (stmtUsername != null) stmtUsername.close();
        }

        if(alredyExists) return "Username não disponível";
            
        
        try {

            stm = conn.prepareStatement(sql);
            stm.setString(1, name);
            stm.setInt(2, age);
            stm.setString(3, username);
            stm.setString(4, pass);
            int inserted = stm.executeUpdate();
            if (inserted > 0) {
                return "User foi inserido";
            }
            else{
                return "Nenhum user foi inserido";
            }
        } catch (SQLException e){
            return e.toString();
        } finally {
            if (stm != null) stm.close();
        }
    }
}
