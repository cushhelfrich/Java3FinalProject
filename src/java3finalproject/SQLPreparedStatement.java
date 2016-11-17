package java3finalproject;

/**
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Bill
 * @Assignment Name: java3finalproject
 * @Date: Oct 30, 2016
 * @Subclass SQLPreparedStatement Description:
 *
 * Returns a Java PreparedStatement for use in writing to the DB Calling method
 * must take care of closing the DB and statements.
 *
 */
//Imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//Begin Subclass SQLPreparedStatement


public class SQLPreparedStatement {

    private PreparedStatement preparedStmt = null;
    // create a database connections
    private final DBConnector db = new DBConnector();  // Tanona
    private Connection connection = null;

    /**
     * createStatement returns a prepared statement
     * @param query
     * @return
     * @throws SQLException 
     */
    public PreparedStatement createStatement(String query) throws SQLException {

        try {
            connection = db.getConnection();

            preparedStmt = connection.prepareStatement(query);

        } catch (SQLException e) {
            System.out.println("Failed to connect to db");

        }

        return preparedStmt;
    }

    public void closeDB() {
        try {
            connection.close();
        } catch (SQLException e) {

        }
    }
} //End Subclass SQLPreparedStatement
