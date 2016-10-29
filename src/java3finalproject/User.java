/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java3finalproject;

/**
 *
 * @author Charlotte
 */
public class User {
    private String email = "";
    private String username = "";
    private String password = "";
    private String salt = "";
    private String first_name = "";
    private String last_name = "";
    private String created = "";
    private String updated = "";
    
    public User(String email, String username, String password, String salt, 
        String first_name, String last_name,String created, String updated)
    {
        this.email = email;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.first_name = first_name;
        this.last_name = last_name;
        this.created = created;
        this.updated = updated;
    }
    
    // Getter and setter methods will go here
    
}
