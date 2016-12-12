package java3finalproject;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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
public class Account implements Comparable<Account>  {

    private String accountName;
    private String username;
    private String password;
    private String website;
    private final int user_id = Login.currUser.getUserId();
    private Timestamp created;
    private Timestamp updated;

    // constructor 
    /**
     *
     * @param name
     * @param uname
     * @param password
     */
    public Account(String name, String uname, String password) 
            throws NoSuchAlgorithmException, KeyStoreException, IOException, NoSuchPaddingException, InvalidKeyException, 
            CertificateException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException 
    {
        this.accountName = name;
        this.username = uname;
        this.password = Dashboard.getAES().encrypt(password, accountName);// Base64 string, includes IV bytes
        //   this.password=password;
        this.website = "na";
        this.created = null;
        this.updated = null;
    }
    
        /****Charlotte's code*****/
    /**
     * Constructor used to create Account from existing data (vs. newly inserted)
     * Existing data includes Timestamps, whereas there are no Timestamps when an Account
     *      is created before record insertion.
     * @param name          account name
     * @param uname
     * @param password
     * @param created       Timestamp: date/time of account creation
     * @param updated       Timestamp: date/time of last account update
     */
    public Account(String name, String uname, String password, Timestamp created, Timestamp updated)
    {
        this.accountName=name;
        this.username=uname;
        this.password = password;
        this.website="na";
        this.created = created;
        this.updated = updated;
    }
    /*****End Charlotte's code*****/
    
    
    /*****Bill's code*****/

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
        String query = "SELECT * FROM account WHERE account_name = ? AND user_id = ?";

        try {
            prepstmt = sqlstmt.createStatement(query);
            prepstmt.setString(1, actName);
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

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getWebsite() {
        return website;
    }
    /*****End Bill's code*****/
    
    /*****Charlotte's code*****/
    public Timestamp getCreated()
    {
        return created;
    }
    
    public Timestamp getUpdated()
    {
        return updated;
    }
    /**
     * Sets created value; expected to be used following record insertion
     * @param time
     */
    public void setCreated(Timestamp time)
    {
        this.created = time;
    }
    
    /**
     * Sets updated value; expected to be used following record insertion
     * @param time
     */
    public void setUpdated(Timestamp time)
    {
        this.updated = time;
    }
    /*****End Charlotte's code*****/
    
    /*****Bill's code*****/
    // Create an insert method .... 

    /**
     * insert method: Inserts a new account into the DB account table
     *
     * @param actName
     * @param usrName
     * @param pw
     * @return true on success
     */
    public boolean insert(String actName, String usrName, String pw) throws SQLException {

        int rowsaffected = 0;
        boolean tableInserted = false;
        boolean gotAccount = false;
        ResultSet res = null;
        
        SQLPreparedStatement sqlstmt = new SQLPreparedStatement();
        String query = "INSERT INTO account (user_id, username, password,account_name)"
                + " VALUES (?,?,?,?)";
        
        try(PreparedStatement prepstmt = sqlstmt.createStatement(query))
        {
            prepstmt.setInt(1, user_id);
            prepstmt.setString(2, usrName);
            prepstmt.setString(3, pw);
            prepstmt.setString(4, actName);
            tableInserted = prepstmt.execute();
            res = prepstmt.getResultSet();
            rowsaffected = prepstmt.getUpdateCount();
            
            /*****Charlotte's code****/
            // Get the Timestamps just created during record insertion
            List<Map<String, Object>> results = Login.db.retrieveRecords(
                    "SELECT created, last_update FROM account WHERE user_id=" + user_id + " AND account_name ='" + actName + "'"
            );
            
            if(results != null & !results.isEmpty())
            {
                setCreated((Timestamp)results.get(0).get("created"));
                setUpdated((Timestamp)results.get(0).get("last_update"));
            }
            /*****End Charlotte's code*****/
            
        }
        
        tableInserted = rowsaffected > 0;

        return tableInserted;
    }
    
    /*****Charlotte's code*****/
    
    /**
    * Used to sort Accounts by name
    * @param other      an Account
    * @return           integer indicating results of comparison
    */
    @Override
    public int compareTo(Account other)
    {
        return this.accountName.compareTo(other.getName());
    }
    
    /**
     * Sort Accounts by created date, using Comparator
     */
    public static Comparator<Account> CreatedComp = (Account a1, Account a2) -> {
        
        // Convert Timestamps to a number, in order to make comparison
        long time1 = a1.getCreated().getTime();
        long time2 = a2.getCreated().getTime();
        
        if(time1 > time2) // a1 created more recently
        {
            return -1;
        }
        else if(time2 > time1) // a2 created more recently
        {
            return 1;
        }
        else // accounts created at same time
        {
            return 0;
        }
    };
    
     /**
     * Sort Accounts by date of last update, using Comparator
     */
    public static Comparator<Account> UpdatedComp = (Account a1, Account a2) ->
    {
        // Convert Timestamps to a number, in order to make comparison
        long time1 = a1.getUpdated().getTime();
        long time2 = a2.getUpdated().getTime();
        
        if(time1 > time2) // a1 created more recently
        {
            return -1;
        }
        else if(time2 > time1) // a2 created more recently
        {
            return 1;
        }
        else // accounts created at same time
        {
            return 0;
        }
    };
    /*****End Charlotte's code*****/

} //End Subclass Account
