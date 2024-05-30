package fruit.vendor.ims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OEM
 */
public class AddRemoveUser {

    public static void addUser(Connection connection) {
        Scanner scan = new Scanner(System.in);

        try {
            System.out.println("Enter the username of the new user: ");
            String username = scan.nextLine();

            if (userExists(connection, username)) {
                System.out.println("The user already exists in the inventory.");
                return;
            }

            System.out.println("Enter the password of the new user: ");
            String password = scan.nextLine();

            String insertUserQuery = "INSERT INTO Users (Username, Password) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("User " + username + " added successfully!");
                }
                connection.commit();
            }
        } catch (SQLException ex) {
            System.out.println("Error adding user: " + ex.getMessage());
        }
    }

    private static boolean userExists(Connection connection, String username) throws SQLException {
        String checkUserQuery = "SELECT COUNT(*) FROM Users WHERE Username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkUserQuery)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        }
    }

    public static void removeUser(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the username of the user to be removed:");
            String username = scanner.nextLine();

            String deleteUserQuery = "DELETE FROM Users WHERE Username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteUserQuery)) {
                preparedStatement.setString(1, username);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User " + username + " removed successfully!");
                } else {
                    System.out.println("User " + username + " not found in the inventory.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error removing fruit: " + ex.getMessage());
        }
    }
}
