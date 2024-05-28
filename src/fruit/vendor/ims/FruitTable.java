package fruit.vendor.ims;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FruitTable {

    private final DBManager dbManager;
    private final Connection conn;
    private Statement statement;

    public FruitTable() {
        dbManager = new DBManager();
        conn = dbManager.getConnection();
        try {
            statement = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createFruitTable() {
        try (Connection conn = dbManager.getConnection();
             Statement statement = conn.createStatement()) {
            // Check if table already exists
            ResultSet resultSet = conn.getMetaData().getTables(null, null, "FRUITS", null);
            if (resultSet.next()) {
                System.out.println("Fruits table already exists.");
                return;
            }

            String createFruitTableQuery = "CREATE TABLE Fruits ("
                    + "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                    + "Name VARCHAR(100) NOT NULL,"
                    + "Quantity INT,"
                    + "Price DOUBLE,"
                    + "AverageWeight DOUBLE)";
            statement.executeUpdate(createFruitTableQuery);
            System.out.println("Fruits table created successfully.");

            // Insert initial data
            String insertFruitsQuery = "INSERT INTO Fruits (Name, Quantity, Price, AverageWeight) VALUES "
                    + "('Apple', 200, 1.0, 150), "
                    + "('Strawberry', 500, 0.18, 15), "
                    + "('Watermelon', 50, 10, 1000)";
            statement.executeUpdate(insertFruitsQuery);
            System.out.println("Initial fruits data inserted successfully.");
        } catch (SQLException ex) {
            System.out.println("Error creating Fruits table: " + ex.getMessage());
        }
    }

    public void closeConnection() {
        this.dbManager.closeConnections();
    }
}
