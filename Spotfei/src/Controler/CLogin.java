/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controler;

import DAO.Connect;
import DAO.UserDAO;
import Model.User;
import View.VLogin;
import View.VSpotfei;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author unifdloureiro
 */
/**
 * Controlador responsável pelo login de usuários.
 */
public class CLogin {
    private VLogin v;
    private UserDAO userDAO;
    private User loggedUser;
    
    /**
     * Construtor: inicializa a view de login.
     * @param v View de login
     */
    public CLogin(VLogin v) {
        this.v = v;
    }
    
    /**
     * Tenta realizar o login com os dados informados.
     * Se for bem-sucedido, abre a view principal.
     */
    public void tryLogin(){
        loggedUser = new User( v.getTxtUsername().getText(), v.getTxtPassword().getText());
        Connect connect = new Connect();
        try{
            Connection conn = connect.getConnection();
            userDAO = new UserDAO(conn);
            if(loggedUser.auth(userDAO.getUsers())){
                VSpotfei vS = new VSpotfei(loggedUser);
                vS.setVisible(true);
                v.setVisible(false);
            }
            else{
                v.getLblStatus().setText("usuario e senha não conferem");
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
    }
    
}
