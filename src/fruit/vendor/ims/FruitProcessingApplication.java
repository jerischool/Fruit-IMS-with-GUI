package fruit.vendor.ims;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FruitProcessingApplication {
    
    public static void main(String[] args) {
        DBManager dbManager = new DBManager();
        Connection connection = dbManager.getConnection();

        if (!UserLogin.isValidUser(connection)) {
            System.out.println("Maximum attempts reached. Exiting...");
        } else {
            FruitTable fruitTable = new FruitTable();
            fruitTable.createFruitTable();

            UserTable userTable = new UserTable();
            userTable.createUserTable();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                    System.out.println("Choose an option: ");
                    System.out.println("1. Add Fruit");
                    System.out.println("2. Remove Fruit");
                    System.out.println("3. Edit Fruit Description");
                    System.out.println("4. Display Fruits Table");
                    System.out.println("5. Exit");

                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            AddRemoveFruit.addFruit(connection);
                            break;
                        case 2:
                            AddRemoveFruit.removeFruit(connection);
                            break;
                        case 3:
                            EditFruitDesc.editFruitDesc(connection);
                            break;
                        case 4:
                            displayFruitsTable(connection);
                            break;
                        case 5:
                            dbManager.closeConnections();
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Clear the invalid input from the scanner
                }
            }
        }
        dbManager.closeConnections();
    }

    private static void displayFruitsTable(Connection connection) {
        String query = "SELECT * FROM Fruits";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Table: Fruits");
            int columnCount = resultSet.getMetaData().getColumnCount();

            // Print column names
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getMetaData().getColumnName(i) + "\t");
            }
            System.out.println();

            // Print rows
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error displaying Fruits table: " + e.getMessage());
        }
    }
}
