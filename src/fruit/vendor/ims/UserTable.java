package fruit.vendor.ims;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserTable {

    private final DBManager dbManager;
    private final Connection conn;
    private Statement statement;

    public UserTable() {
        dbManager = new DBManager();
        conn = dbManager.getConnection();
        try {
            statement = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createUserTable() {
        try (Connection conn = dbManager.getConnection();
             Statement statement = conn.createStatement()) {
            // Check if table already exists
            ResultSet resultSet = conn.getMetaData().getTables(null, null, "USERS", null);
            if (resultSet.next()) {
                System.out.println("Users table already exists.");
                return;
            }

            String createUserTableQuery = "CREATE TABLE Users ("
                    + "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                    + "Username VARCHAR(100) NOT NULL,"
                    + "Password VARCHAR(100) NOT NULL)";
            statement.executeUpdate(createUserTableQuery);

        } catch (SQLException ex) {
            System.out.println("Error creating Users table: " + ex.getMessage());
        }
    }

    public void closeConnection() {
        this.dbManager.closeConnections();
    }
}
