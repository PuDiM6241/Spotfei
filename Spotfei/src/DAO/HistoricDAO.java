/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe responsável por operações relacionadas ao histórico de ações de uma pessoa no banco de dados.
 * Permite consultar e inserir valores no histórico.
 * 
 * @author pudim
 */
public class HistoricDAO {
    private Connection conn;

    /**
     * Construtor que inicializa o DAO com uma conexão de banco de dados existente.
     * 
     * @param conn Conexão com o banco de dados.
     */
    public HistoricDAO(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * Obtém os últimos 10 registros do histórico para uma pessoa específica, ordenados do mais recente para o mais antigo.
     * 
     * @param personId ID da pessoa cujo histórico será consultado.
     * @return Lista contendo os valores do histórico.
     * @throws SQLException Caso ocorra erro durante a consulta no banco de dados.
     */
    public ArrayList<String> getLastPersonHistoric(int personId) throws SQLException{
        ArrayList<String> historic = new ArrayList();
        String sql = "SELECT * FROM vPersonHistory WHERE personId = ? ORDER BY historyId DESC LIMIT 10";
        PreparedStatement stm = null; 
        ResultSet rs = null;

        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, personId);
            rs = stm.executeQuery();

            while(rs.next()){
                historic.add(rs.getString("value"));
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
        }
        return historic;
    }

     /**
     * Insere um novo valor no histórico de uma pessoa.
     * 
     * @param personId ID da pessoa para quem o valor será inserido no histórico.
     * @param value Valor a ser inserido no histórico.
     * @return Mensagem indicando sucesso ou falha na inserção.
     * @throws SQLException Caso ocorra erro durante a inserção no banco de dados.
     */
    public String InsertPersonHistoric(int personId, String value) throws SQLException{
        String sql = "INSERT INTO history (personId, value) VALUES (?, ?)";
        PreparedStatement stm = null; 
        ResultSet rs = null;

        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, personId);
            stm.setString(2, value);
            int inserted = stm.executeUpdate();
            if (inserted > 0) {
                return "Valor foi inserido no histórico";
            }
            else{
                return "Nenhum valor foi inserido no histórico";
            }
        } catch (SQLException e){
            return e.toString();
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
        }
    }
}
