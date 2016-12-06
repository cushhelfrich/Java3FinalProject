package java3finalproject;

import java.sql.Timestamp;

/** 
 * @Course: SDEV 450 ~ Enterprise Java Programming
 * @Contributors: Charlotte Hirschberger
 * @Created Date: October 28, 2016
 * @Last update: October 30, 2016
 * @Description: This class uses the values captured during login or account
 *      account creation to create a User object. Get and set methods will later
 *      allow interfaces beside the Login screen to access and modify these
 *      values, for purposes like displaying a profile or resetting one's main
 *      password.
 */

public class User {
    private int user_id = 0;
    private String email = "";
    private String username = "";
    private String password = "";
    private String salt = "";
    private String first_name = "";
    private String last_name = "";
    private Timestamp created = null;
    private Timestamp updated = null;
    
    /**
     * Assigns the values retrieved from the database or user to the corresponding
     * member variables
     * @param user_id   integer
     * @param email
     * @param username
     * @param password  Base 64 string
     * @param salt      Base 64 string
     * @param first_name
     * @param last_name
     * @param created   YYYY-MM-DD HH:MM:SS, DATETIME
     * @param updated   YYYY-MM-DD HH:MM:SS, TIMESTAMP UTC
     */
    public User(int user_id, String email, String username, String password,
            String salt, String first_name, String last_name, Timestamp created, Timestamp updated)
    {
        this.user_id = user_id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.first_name = first_name;
        this.last_name = last_name;
        this.created = created;
        this.updated = updated;
    }
    
    /**
     * Return the current user's id, which was retrieved during login
     * @return 
     */
    public int getUserId()
    {
        return user_id;
    }    

    // Additional getter and setter methods will go here
    //CUSH

    /**
     * Return the current user's username , which was retrieved during login
     * @return username
     */
    public String getUsername () {
        
        return username;
    }
}
