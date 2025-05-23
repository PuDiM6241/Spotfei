/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Classe responsável por gerenciar a conexão com o banco de dados PostgreSQL.
 * 
 * @author pudim
 */
public class Connect {
    
    /**
     * Estabelece e retorna uma conexão com o banco de dados PostgreSQL.
     * 
     * @return Objeto Connection representando a conexão com o banco de dados.
     * @throws SQLException Caso ocorra erro ao tentar conectar ao banco.
     */
    public Connection getConnection() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "postegres");
        return connection;
    }
}
