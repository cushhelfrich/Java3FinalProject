package java3finalproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Course: SDEV 450 ~ Enterprise Java Programming
 * @Contributors: Charlotte Hirschberger, Wayne Riley
 * @Created Date: October 16, 2016
 * @Last update: October 28, 2016
 * @Description: This class is responsible for initiating and returning the
 * connection to a database and executing queries received in String parameters.
 */
/**
 * Start Charlotte's code*
 */
public class DBConnector {

    private Connection connection;
    private String dbUser = "champlain";
    private String dbPassword = "Fin8lPr0ject";
    private String dbPath = "jdbc:mysql://localhost/projectdb";

    /**
     * Constructor automatically attempts to establish connection using database
     * credentials from member variables
     */
    public DBConnector() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Establish connection to database
            connection = DriverManager.getConnection(
                    dbPath,
                    dbUser,
                    dbPassword
            );
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("A connection to the database could not be established. "
                    + "Error message: " + ex.getMessage());
        }
    }

    /**
     * Returns connection established in constructor
     *
     * @return
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * General method for executing SQL queries, for use with SELECT statements
     *
     *
     * @param query SQL statement in a string
     * @return
     */
    public ResultSet retrieveRecords(String query) {
        Statement stmt;
        ResultSet rs = null;

        try {
            stmt = connection.createStatement();

            rs = stmt.executeQuery(query);
        } catch (Exception ex) {
            System.out.println("A problem occurred while accessing records. "
                    + "Error message: " + ex.getMessage());
        }

        return rs;
    }

    /**
     * Accepts an SQL statement in a string and returns an integer, representing
     * the number of rows affected by the query. 0 indicates a query that
     * affects 0 rows
     *
     * @param query delete, update, or insert
     * @return
     */
    public int modifyRecords(String query) {
        Statement stmt;
        int rowsAffected = 0;

        try {
            stmt = connection.createStatement();

            rowsAffected = stmt.executeUpdate(query);
        } catch (Exception ex) {
            System.out.println("A problem occurred while accessing records. "
                    + "Error message: " + ex.getMessage());
        }

        return rowsAffected;
    }
    /*End Charlotte's code*/
}
