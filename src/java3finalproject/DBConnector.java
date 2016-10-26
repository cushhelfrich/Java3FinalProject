package java3finalproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author charl
 */
public class DBConnector {
    private Connection connection;
    private String dbUser = "champlain";
    private String dbPassword = "Fin8lPr0ject";
    private String dbPath = "jdbc:mysql://localhost/projectdb";
    
    public DBConnector()
    {
            try {
                // Load the JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                
                // Establish connection to database
                connection = DriverManager.getConnection (
                    dbPath,
                    dbUser,
                    dbPassword
                );
            }
            catch (ClassNotFoundException | SQLException ex)
            {
                System.out.println("A connection to the database could not be established. "
                        + "Error message: " + ex.getMessage());
            }
    }
    
    public Connection getConnection()
    {
        return connection;
    }
    
    public ResultSet retrieveRecordPS(String query)
    {
                
    }
    public ResultSet retrieveSaltedPW(String user) throws SQLException
    {
        boolean b = false;
        Statement stmt;
        ResultSet rs = null;
        
        try 
        {
             stmt = connection.createStatement();
             // Modify these parameters according to group discussion
             rs = stmt.executeQuery("SELECT email, password, salt FROM user WHERE email = '" + user + "'");
        }
        catch (SQLException ex)
        {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        
        return rs;
    }
}
