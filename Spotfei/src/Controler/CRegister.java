/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controler;

import DAO.Connect;
import DAO.UserDAO;
import View.VRegister;
import View.VSpotfei;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JLabel;

/**
 *
 * @author pudim
 */
/**
 * Controlador responsável pelo registro de novos usuários.
 */
public class CRegister {
    private VRegister v;
    private UserDAO userDAO;
    
    /**
     * Construtor: inicializa a view de registro.
     * @param v View de registro
     */
    public CRegister(VRegister v) {
        this.v = v;
    }
    
    /**
     * Tenta inserir um novo usuário após validar os dados.
     * Exibe o resultado na view.
     */
    public void tryInsertUser(){
        
        if((int) v.getsAge().getValue() <= 0) v.getLblStatus().setText("idade não pode ser 0 ou menor");
        else if(v.getTxtName().getText().length() == 0) v.getLblStatus().setText("Nome não pode ser vazio");
        else if(v.getTxtPassword().getText().length() == 0) v.getLblStatus().setText("Senha não pode ser vazia");
        else{
            if(v.getTxtPassword().getText().equals(v.getTxtConfirmPassword().getText())){
                String awnser = "null";
                Connect connect = new Connect();

                try{
                    Connection conn = connect.getConnection();
                    userDAO = new UserDAO(conn);
                    awnser = userDAO.InsertUser( v.getTxtName().getText(), (int)v.getsAge().getValue(), v.getTxtUsername().getText(), v.getTxtPassword().getText());
                }catch(SQLException e){
                    System.out.println(e.toString());
                }

                v.getLblStatus().setText(awnser);

            } else{
                v.getLblStatus().setText("Senhas não conferem");
            }
        }
        v.getLblStatus().repaint();
    }
    
}
