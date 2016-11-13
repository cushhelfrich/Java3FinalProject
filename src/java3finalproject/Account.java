package java3finalproject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** 
 * @Course: SDEV 250 ~ Java Programming III
 * @Author Name: Bill Tanona
 * @Assignment Name: java3finalproject
 * @Date: Oct 20, 2016
 * @Subclass Account Description: 
 * 
 * The Account class will represent a single acount with:
 * 
 * private members
 *    username and  password both encrypted
 *    account type 
 *    
 * 
 * 
 */
//Imports

//Begin Subclass Account
public class Account {
    private final String accountName;
    private final String username;
    private final String password;
    private final String website;
    
    
    // constructor 
    
    public Account(String name, String uname, String password){
        this.accountName=name;
        this.username=uname;
        this.password=password;
        this.website="na";
    }
   
    public String getName(){
        return accountName;
    }
    public String getPassword(){
        return password;
    }
    public String getWebsite(){
        return website;
    }
        // Create an insert method .... 

    int rowsaffected=0;
    private boolean tableInserted = false;
    private ResultSet res = null;
    SQLPreparedStatement sqlstmt = new SQLPreparedStatement();
    PreparedStatement prepstmt;
    String query;
    
    public boolean insert(String actName, String usrName, String pw) throws SQLException{
        DBConnector db = new DBConnector();
        
        

        query = "INSERT INTO account (user_id, username, password,account_name)"
                + " VALUES (?,?,?,?)";
        int user_id = Login.currUser.getUserId();
        
        ArrayList<Object> paramVals = new ArrayList<>();
        
        paramVals.add(user_id);
        paramVals.add(usrName);
        paramVals.add(pw);
        paramVals.add(actName);
        

        int rowsAffected = db.modifyRecordsPS(query, 4, paramVals);
        
        tableInserted = rowsaffected > 0;
            
        return tableInserted;
    }
} //End Subclass Account
