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

/** 
 * @Course: SDEV 450 ~ Enterprise Java Programming
 * @Contributors: Charlotte Hirschberger, Wayne Riley
 * @Created Date: October 16, 2016
 * @Last update: October 28, 2016
 * @Description: This class is responsible for initiating and returning the 
 *      connection to a database and executing queries received in String parameters.
 */

/**Start Charlotte's code**/
public class DBConnector {
    private static Connection conn;
    private static String dbUser = "champlain";
    private static String dbPassword = "Fin8lPr0ject";
    private static String dbPath = "jdbc:mysql://localhost/projectdb";
    
    /**
     * Constructor automatically attempts to establish connection using database
     * credentials from member variables
     */
    public DBConnector()
    {
        try
        {
            conn = DriverManager.getConnection(dbPath, dbUser, dbPassword);
        }
         catch(Exception ex)
         {
             System.out.println("Error connecting to database."
                     + "Error message: " + ex.getMessage());
         }
    }
        
        public Connection getConnection()
    {
        return conn;
    }
    /**
     * General method for executing SQL queries, for use with SELECT statements     * 
     * @param query SQL statement in a string
     * @return 
     */
    public List<Map<String, Object>> retrieveRecords(String query)
    {
       List<Map<String, Object>> results = new ArrayList<>();
       HashMap<String, Object> row;

       try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query))
       {       
           ResultSetMetaData metaData = rs.getMetaData();
           int colCount = metaData.getColumnCount();
            while(rs.next())
            {
                row = new HashMap<>();
                for(int i = 1; i <= colCount; i++)
                {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                results.add(row);
            }
       }
       catch(Exception ex)
       {
            System.out.println("A problem occurred while accessing records. "
                    + "Error message: " + ex.getMessage());
       }
       return results;
    }
    
    /**
     * Accepts an SQL statement in a string and returns an integer, representing
     * the number of rows affected by the query. 0 indicates a query that
     * affects 0 rows
     * @param query delete, update, or insert
     * @return 
     */
    public int modifyRecords(String query)
    {
        int rowsAffected = 0;
        
        try(Statement stmt = conn.createStatement())
        {
            rowsAffected = stmt.executeUpdate(query);
        } 
       catch(Exception ex)
        {
            System.out.println("A problem occurred while updating records. "
                    + "Error message: " + ex.getMessage());
        }
       return rowsAffected; 
    }
    /*End Charlotte's code*/
    
    //**************************wayne*************************************8
    /**
     * Queries account table and returns to dashboard and printed in Textarea.
     *
     * @return
     */
     public List<String> retrieveAccts(String query)
    {
       List<String> results = new ArrayList<>();

       try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query))
       {                  
           rs.last();
           int rowCount = rs.getRow();
           rs.beforeFirst();
           
            for(int i = 1; i <= rowCount; i++)
            {
                results.add(rs.getString("account_name"));
            }
       }
       catch(Exception ex)
       {
            System.out.println("A problem occurred while retrieving account details. "
                    + "Error message: " + ex.getMessage());
       }
       return results;
    }
    
    public int insertUser(String query)
    {
        int rowsAffected = 0;
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try(PreparedStatement prepStmt = conn.prepareStatement(query))
        {            
            prepStmt.setTimestamp(1, datetime);
            prepStmt.setTimestamp(2, datetime);
            
            rowsAffected = prepStmt.executeUpdate();
                     
        } 
       catch(Exception ex)
        {
            System.out.println("A problem occurred while inserting the user record. "
                    + "Error message: " + ex.getMessage());
        }
        
       return rowsAffected; 
    }
    
    public void closeConnection()
    {
        try
        {
            conn.close();
        }
        catch(Exception ex)
        {
            System.out.println("There was a problem closing the connection to the database."
                + "Error message: " + ex.getMessage());
        }
    }
}

