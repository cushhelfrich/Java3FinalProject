
package java3finalproject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Course: SDEV 250 ~ Java Programming III
 * @Author Name: Bill Tanona
 * @Assignment Name: java3finalproject
 * @Date: Oct 20, 2016
 * @Subclass Account Description:
 *
 * The Account class will represent a single account with:
 *
 * private members accountName username password account type
 *
 *
 *
 */
//Imports
//Begin Subclass Account
public class Account implements Comparable<Account> {

    private  String accountName;
    private  String username;
    private  String password;
    private  String website;
    private final int user_id = Login.currUser.getUserId();
    private Timestamp created;
    private Timestamp updated;

    final String secretKey = "DonaTellaNobody";

    // constructor 
    /**
     *
     * @param name
     * @param uname
     * @param password
     */
    public Account(String name, String uname, String password) {
        this.accountName = name;
        this.username = uname;
        this.password = AEScrypt.encrypt(password, secretKey);
        //   this.password=password;
        this.website = "na";
        this.created = null;
        this.updated = null;
    }

    public Account(String name, String uname, String password, Timestamp created, Timestamp updated)
    {
        this.accountName=name;
        this.username=uname;
        this.password = AEScrypt.encrypt(password, secretKey);
        this.website="na";
        this.created = created;
        this.updated = updated;
    }
    /**
     * getAccount uses a prepared statement to query the DB for for account info
     *
     * @param actName
     */
    public Account(String actName) {
       // this.accountName=null;
        
        int rowsaffected = 0;
        boolean tableInserted = false;
        boolean gotAccount = false;
        ResultSet res = null;
        SQLPreparedStatement sqlstmt = new SQLPreparedStatement();
        PreparedStatement prepstmt;
        String query = "SELECT username,password FROM account WHERE account_name = ? AND user_id = ?";

        try {
            prepstmt = sqlstmt.createStatement(query);
            prepstmt.setString(1,actName);
            prepstmt.setInt(2, user_id);
            prepstmt.execute();
            ResultSet rs = prepstmt.getResultSet();
            rs.next();
            this.accountName = actName;
            this.username = rs.getString("username");
            this.password = rs.getString("password");
            this.website = "na";
            this.created = rs.getTimestamp("created");
            this.updated = rs.getTimestamp("last_update");

            
        } catch (SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    /**
     * getName(): public getter method for account name
     *
     * @return
     */
    public String getName() {
        return accountName;
    }

    public String getUserName(){
        return username;
    }
    public String getPassword() {
        return password;
    }

    public String getWebsite() {
        return website;
    }
    
    public Timestamp getCreated()
    {
        return created;
    }
    
    public Timestamp getUpdated()
    {
        return updated;
    }
    
    public void setCreated(Timestamp time)
    {
        this.created = time;
    }
    
    public void setUpdated(Timestamp time)
    {
        this.updated = time;
    }
    // Create an insert method .... 

    /**
     * insert method: Inserts a new account into the DB account table
     *
     * @param actName
     * @param usrName
     * @param pw
     * @return true on success
     */
    public boolean insert(String actName, String usrName, String pw) {

        int rowsaffected = 0;
        boolean tableInserted = false;
        boolean gotAccount = false;
        ResultSet res = null;
        SQLPreparedStatement sqlstmt = new SQLPreparedStatement();
        PreparedStatement prepstmt = null;
        // String query;
        String query = "INSERT INTO account (user_id, username, password,account_name)"
                + " VALUES (?,?,?,?)";


        try {
            prepstmt = sqlstmt.createStatement(query);

            prepstmt.setInt(1, user_id);
            prepstmt.setString(2, usrName);
            prepstmt.setString(3, pw);
            prepstmt.setString(4, actName);
            tableInserted = prepstmt.execute();
            res = prepstmt.getResultSet();
            rowsaffected = prepstmt.getUpdateCount();
            
            List<Map<String, Object>> results = Login.db.retrieveRecords(
                    "SELECT created, last_update FROM account WHERE user_id=" + user_id
            );
            
            if(results != null & !results.isEmpty())
            {
                setCreated((Timestamp)results.get(0).get("created"));
                setUpdated((Timestamp)results.get(0).get("last_update"));
                
            }
        } catch (SQLException e) {
            System.out.println("Failed to create PreparedStatement");

        } finally {
            try {
                if(prepstmt != null)
                {
                    prepstmt.close();
                } //  Close resources 
                sqlstmt.closeDB();

            } catch (SQLException ex) {
                Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        tableInserted = rowsaffected > 0;

        return tableInserted;
    }
    
    @Override
    public int compareTo(Account other)
    {
        return this.accountName.compareToIgnoreCase(other.getName());
    }
} //End Subclass Account
