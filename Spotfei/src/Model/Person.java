/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author unifdloureiro
 */
/**
 * Classe abstrata que representa uma pessoa genérica com atributos básicos de identificação e autenticação.
 */
public abstract class Person {
    protected int id;
    protected String name;
    protected int age;
    protected String username;
    protected String pass;

    /**
     * Construtor que inicializa a pessoa com nome de usuário e senha.
     * 
     * @param username nome de usuário
     * @param pass senha
     */
    public Person(String username, String pass) {
        this.username = username;
        this.pass = pass;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
 
        @Override
    public String toString() {
        return "Person{" + "id=" + id + ", name=" + name + ", age=" + age + ", username=" + username + ", pass=" + pass + '}';
    }
}
