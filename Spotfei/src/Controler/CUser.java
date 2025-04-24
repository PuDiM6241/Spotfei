/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controler;

import Model.User;

/**
 *
 * @author unifdloureiro
 */
public class CUser {
    //main controller
    private User user;
    private CLogin cLogin;
    private CSpotfei cSpotfei;

    public CUser() {
        this.cLogin = new CLogin();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CLogin getCLogin() {
        return cLogin;
    }

    public void setCLogin(CLogin cLogin) {
        this.cLogin = cLogin;
    }

    public CSpotfei getCSpotfei() {
        return cSpotfei;
    }

    public void setCSpotfei(CSpotfei cSpotfei) {
        this.cSpotfei = cSpotfei;
    }
    
    
}
