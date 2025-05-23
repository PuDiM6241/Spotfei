/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar as operações de "like" para músicas no banco de dados.
 * Permite inserir ou atualizar o tipo de "like" dado por uma pessoa a uma música.
 * 
 * @author pudim
 */
public class LikeDAO {
    private Connection conn;

    /**
     * Construtor que inicializa o DAO com uma conexão de banco de dados existente.
     * 
     * @param conn Conexão com o banco de dados.
     */
    public LikeDAO(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * Insere um "like" ou atualiza o tipo de "like" para uma música dada por uma pessoa.
     * Caso já exista um registro para o par (musicId, personId), o tipo será atualizado.
     * 
     * @param musicId ID da música que receberá o "like".
     * @param personId ID da pessoa que está dando o "like".
     * @param type Tipo do "like" (true ou false).
     * @return Mensagem indicando se a operação foi concluída com sucesso ou não.
     * @throws SQLException Caso ocorra erro durante a operação no banco de dados.
     */

    public String InsertLike(int musicId, int personId, boolean type) throws SQLException{
        String sql = "INSERT INTO musicLike (musicId, personId, likeType) VALUES (?, ?, ?) ON CONFLICT (musicId, personId) DO UPDATE SET likeType = EXCLUDED.likeType;";
        PreparedStatement stm = null;
        
        try {

            stm = conn.prepareStatement(sql);
            stm.setInt(1, musicId);
            stm.setInt(2, personId);
            stm.setBoolean(3, type);
            int inserted = stm.executeUpdate();
            if (inserted > 0) {
                return "Mudança foi concluida";
            }
            else{
                return "Nenhuma Mudança foi concluida";
            }
        } catch (SQLException e){
            return e.toString();
        } finally {
            if (stm != null) stm.close();
        }
    }
}
