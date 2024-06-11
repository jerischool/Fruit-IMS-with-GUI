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
                    System.out.println("\nChoose an option: ");
                    System.out.println("1. Add Fruit");
                    System.out.println("2. Remove Fruit");
                    System.out.println("3. Edit Fruit Description");
                    System.out.println("4. Add User");
                    System.out.println("5. Remove User");
                    System.out.println("6. Edit User Profile");
                    System.out.println("7. Display Fruits Table");
                    System.out.println("8. Display Users Table");
                    System.out.println("9. Exit\n");

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
                            AddRemoveUser.addUser(connection);
                            break;
                        case 5:
                            AddRemoveUser.removeUser(connection);
                            break;
                        case 6:
                            EditUserProf.editUserProf(connection);
                            break;
                        case 7:
                            displayFruitsTable(connection);
                            break;
                        case 8:
                            displayUsersTable(connection);
                            break;
                        case 9:
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

    // method to display Users Table (without showing passwords)
    private static void displayUsersTable(Connection connection) {
        String query = "SELECT ID, Username FROM Users";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            System.out.println("Users Table:");
            System.out.println("ID\tUsername");
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String username = resultSet.getString("Username");
                System.out.println(id + "\t" + username);
            }
            System.out.println("Users Table displayed successfully."); // Debug print statement
        } catch (SQLException e) {
            System.out.println("Error displaying Users table: " + e.getMessage());
        }
    }

    
    // method to display Fruits Table
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
