package fruit.vendor.ims;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FruitTable {

    // dbmanager and connection objects
    private final DBManager dbManager;
    private final Connection conn;

    // constructor to initialize dbmanager and connection
    public FruitTable(DBManager dbManager) {
        this.dbManager = dbManager;
        this.conn = dbManager.getConnection();
    }

    // method to create the fruits table
    public void createFruitTable() {
        try (Statement statement = conn.createStatement()) {
            // check if table already exists
            ResultSet resultSet = conn.getMetaData().getTables(null, null, "FRUITS", null);
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Fruits table already exists.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // sql query to create the fruits table
            String createFruitTableQuery = "CREATE TABLE Fruits ("
                    + "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                    + "Name VARCHAR(100) NOT NULL,"
                    + "Quantity INT,"
                    + "Price DOUBLE,"
                    + "AverageWeight DOUBLE)";
            statement.executeUpdate(createFruitTableQuery);
            JOptionPane.showMessageDialog(null, "Fruits table created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            // display an error message if an sql exception occurs
            JOptionPane.showMessageDialog(null, "Error creating Fruits table: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to close the database connection
    public void closeConnection() {
        this.dbManager.closeConnections();
    }
}
