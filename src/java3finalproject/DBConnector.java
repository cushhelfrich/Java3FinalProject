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
 * @Last update: October 28, 2016
 * @Description: This class is responsible for initiating and returning the 
 *      connection to a database and executing queries received in String parameters.
 */

/**Start Charlotte's code**/
public class DBConnector {
    private static Connection conn = null;
    private static final String DB_USER = "champlain";
    private static final String DB_PASSWORD = "Fin8lPr0ject";
    private static final String DB_PATH = "jdbc:mysql://localhost/projectdb";
    private static int countFailed = 0;
    
    /**
     * Constructor automatically attempts to establish connection using database
     * credentials from member variables
     */
    public DBConnector()
    {
        initConnection();
    }
    
    private boolean initConnection()
    {
        boolean b = true;
        try
        {
            // load and register JDBC driver for MySQL 
            Class.forName("com.mysql.jdbc.Driver"); 

            conn = DriverManager.getConnection(DB_PATH, DB_USER, DB_PASSWORD);
        }
        catch(ClassNotFoundException | SQLException ex)
        {
            if(countFailed == 0)
            {
                Login.verify.createAlert(Alert.AlertType.WARNING, "Connection error", 
                     "Error connecting to database. Check your Internet connection "
                     + "before proceeding. Error message: " + ex.getMessage());
            }
            
            countFailed++;
            b = false;
         }
        return b;
    }
        
    public Connection getConnection()
    {
        return conn;
    }
    /**
     * General method for executing SQL queries, for use with SELECT statements     * 
     * @param query SQL statement in a string
     * @return 
     * @throws java.sql.SQLException 
     */
    public List<Map<String, Object>> retrieveRecords(String query) throws SQLException
    {
       List<Map<String, Object>> results = new ArrayList<>();
       HashMap<String, Object> row;

       if(conn != null || (conn == null && initConnection()))
       {
            countFailed = 0;
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
       }

       return results;
    }
    
    public int getCountFailed()
    {
        return countFailed;
    }
    
    public int modifyRecordsPS(String query, int numParams, ArrayList<Object> paramVals) throws SQLException
    {
       int rowsAffected = 0;
       
       if(conn != null || (conn == null && initConnection()))
       {
            try (PreparedStatement pstmt = conn.prepareStatement(query)) 
            {
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
     * @param query delete, update, or insert
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
    
    /**
     *
     * @param query
     * @return
     * @throws SQLException
     */
    public int insertUser(String query) throws SQLException
    {
        int rowsAffected = 0;
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
    
    public void closeConnection()
    {
        try
        {
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
}
