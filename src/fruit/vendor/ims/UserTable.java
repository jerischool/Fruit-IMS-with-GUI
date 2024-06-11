package fruit.vendor.ims;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserTable {

    // dbmanager and connection objects
    private final DBManager dbManager;
    private final Connection conn;

    // constructor to initialize dbmanager and connection
    public UserTable(DBManager dbManager) {
        this.dbManager = dbManager;
        this.conn = dbManager.getConnection();
    }

    // method to create the users table
    public void createUserTable() {
        try (Statement statement = conn.createStatement()) {
            // check if table already exists
            ResultSet resultSet = conn.getMetaData().getTables(null, null, "USERS", null);
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Users table already exists.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // sql query to create the users table
            String createUserTableQuery = "CREATE TABLE Users ("
                    + "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                    + "Username VARCHAR(100) NOT NULL,"
                    + "Password VARCHAR(100) NOT NULL)";
            statement.executeUpdate(createUserTableQuery);
            JOptionPane.showMessageDialog(null, "Users table created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            // display an error message if an sql exception occurs
            JOptionPane.showMessageDialog(null, "Error creating Users table: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to close the database connection
    public void closeConnection() {
        this.dbManager.closeConnections();
    }
}
