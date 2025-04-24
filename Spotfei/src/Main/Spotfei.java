/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Controler.CUser;
import View.VLogin;

/**
 *
 * @author unifdloureiro
 */
public class Spotfei {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CUser cUser = new CUser();
        VLogin vLogin = new VLogin(cUser.getCLogin());
        vLogin.setVisible(true);
    }
    
}
