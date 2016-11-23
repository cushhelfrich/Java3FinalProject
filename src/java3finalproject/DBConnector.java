package java3finalproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Alert;

/** 
 * @Course: SDEV 450 ~ Enterprise Java Programming
 * @Contributors: Charlotte Hirschberger, Wayne Riley
 * @Created Date: October 16, 2016
 * @Last update: Nov 20, 2016
 * @Description: This class is responsible for initiating and closing the
 *      connection to a database and executing queries received in String parameters.
 */

/**Start Charlotte's code**/
public class DBConnector {
    /* Maintain Connection in attribute to minimize costly re-instantiation of the connection*/
    private static Connection conn = null;
    
    /* Credentials for connecting to local database*/
    private static final String DB_USER = "champlain";
    private static final String DB_PASSWORD = "Fin8lPr0ject";
    private static final String DB_PATH = "jdbc:mysql://localhost/projectdb";
    
    // Maintain a count of failed connections, so program can respond dynamically to Exceptions
    private static int countFailed = 0;
    
    public DBConnector()
    {
        initConnection();
        System.out.println("successful init");
    }
    
    /**
     * Used by constructor and query methods.
     * Attempts to establish connection to a local database using credentials maintained
     * in constants.
     * @return      boolean to indicate whether Connection was established
     */
    private boolean initConnection()
    {
        boolean b = true;
        
        try
        {
            // load and register JDBC driver for MySQL 
            Class.forName("com.mysql.jdbc.Driver");

            // try to establish the connection
            conn = DriverManager.getConnection(DB_PATH, DB_USER, DB_PASSWORD);
        }
        
        // Connection failed
        catch(ClassNotFoundException | SQLException ex)
        {
            /* If countFailed is not greater than 0, display an alert and increment countFailed.
            If countFailed comes to exceed 0, the calling function will know that the program 
            should abort.
            */
            if(countFailed == 0)
            {
                Login.verify.createAlert(Alert.AlertType.WARNING, "Connection error", 
                "Error connecting to database. Check your Internet connection "
                + "before proceeding. Error message: " + ex.getMessage());
            }
            else
            {
                Login.verify.createAlert(Alert.AlertType.ERROR, "Connection error",
                "The program is still unable to establish a connection to the database."
                        + "The program will now exit. Error message: " + ex.getMessage());
                System.exit(1);
            }
            

            countFailed++;
            b = false;
         }
        return b;
    }
        
    /**
     * Ideally this will be eliminated in the final iteration to prevent resource leaks
     * @return 
     */
    public Connection getConnection()
    {
        return conn;
    }
    
    /**
     * General method for executing SQL queries, for use with SELECT statements.
     * Loads the ResultSet into a List of Maps. Each Map contains one record, with a String, Object pair
     * representing the column name and value found in each of that record's fields.
     * @param query     SQL statement in a string
     * @return 
     * @throws java.sql.SQLException 
     */
    public List<Map<String, Object>> retrieveRecords(String query) throws SQLException
    {
        List<Map<String, Object>> results = null;

       /*If Connection is already established or can be re-established, get the ResultSet
       and populate the List. Otherwise, initConnection will throw an Exception.*/
       if(conn != null || (conn == null && initConnection()))
       {       
            results = new ArrayList<>();
            HashMap<String, Object> row;
            countFailed = 0;    // Reset
            
            /* try-with-resources closes resources automatically*/
            try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query))
            {       
                ResultSetMetaData metaData = rs.getMetaData();
                int colCount = metaData.getColumnCount();
                while(rs.next())
                {
                    row = new HashMap<>();  // Create a Map for this record's values
                    for(int i = 1; i <= colCount; i++)
                    {
                        // Put each column name/value pair in the current Map
                        row.put(metaData.getColumnName(i), rs.getObject(i));
                    }
                results.add(row); // Add the most recent Map to the List
                }
            }
       }
       return results;
    }
    
    /**
     * Returns countFailed() to calling function, so that Exception behavior can be handled dynamically.
     * @return 
     */
    public int getCountFailed()
    {
        return countFailed;
    }
    
    /**
     * Generic method for creating and executing a PreparedStatement
     * @param query         a query string with parameterized values
     * @param numParams     the number of parameters in the query, represented by ?s
     * @param paramVals     the values for the parameters as objects
     * @return              int: number of rows inserted, deleted, or updated
     * @throws SQLException 
     */
    public int modifyRecordsPS(String query, int numParams, ArrayList<Object> paramVals) throws SQLException
    {
       int rowsAffected = 0;
       
       if(conn != null || (conn == null && initConnection()))
       {
            try (PreparedStatement pstmt = conn.prepareStatement(query)) 
            {
                // Supply an Object for each parameter in the PreparedStatement
                for(int i = 1; i <= numParams; i++)
                {
                    pstmt.setObject(i, paramVals.get(i - 1));
                }
            rowsAffected = pstmt.executeUpdate();
            }
       }

       return rowsAffected;
    }
        
    
    /**
     * Accepts an SQL statement in a string and returns an integer, representing
     * the number of rows affected by the query. 0 indicates a query that
     * affects 0 rows
     * @param query     delete, update, or insert statement
     * @return
     * @throws java.sql.SQLException 
     */
    public int modifyRecords(String query) throws SQLException
    {
        int rowsAffected = 0;
        
        if(conn != null || (conn == null && initConnection()))
        {
            try(Statement stmt = conn.createStatement())
            {
                rowsAffected = stmt.executeUpdate(query);
            }
        }
        return rowsAffected; 
    }
    
    /**
     * To be replaced with modifyRecordsPS in next iteration
     * Creates a PreparedStatement for use when inserting a new record in the User table
     * @param query
     * @return
     * @throws SQLException
     */
    public int insertUser(String query) throws SQLException
    {
        int rowsAffected = 0;
        
        /* get current time, for insertion in created and last_update fields*/
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        if(conn != null || (conn == null && initConnection()))
        {
            try(PreparedStatement prepStmt = conn.prepareStatement(query))
            {            
                prepStmt.setTimestamp(1, datetime);
                prepStmt.setTimestamp(2, datetime);
            
                rowsAffected = prepStmt.executeUpdate();    
            } 
        }
       return rowsAffected; 
    }
    
    /**
     * Closes the Connection to the database.
     * Called in shutdown hook upon System.exit.
     */
    public void closeConnection()
    {
        try
        {
            /* Verify that the connection is open before trying to close it*/
            if(conn != null)
            {
                conn.close();  
            }
        }
        catch(Exception ex)
        {
            System.out.println("There was a problem closing the connection to the database."
                + " Error message: " + ex.getMessage());
        }
    }
    /*End Charlotte's code*/
    
    
    //**************************wayne*************************************8
    /**
     * Queries account table and returns to dashboard and printed in Textarea.
     *
     * @param query
     * @return
     * @throws java.sql.SQLException
     */
    public List<String> retrieveAccts(String query) throws SQLException
    {
       List<String> results = new ArrayList<>();
       if(conn != null || (conn == null && initConnection()))
       {
            try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query))
            {                  
                rs.last();
                int rowCount = rs.getRow();
                rs.first();
           
                for(int i = 1; i <= rowCount; i++)
                {
                    results.add(rs.getString("account_name"));
                    rs.next();
                }
            }
        }
        return results;
    }
}