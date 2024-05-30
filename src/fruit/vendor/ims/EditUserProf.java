/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fruit.vendor.ims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author OEM
 */
public class EditUserProf {

    public static void editUserProf(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter the username of the user to edit:");
            String currentUsername = scanner.nextLine();

            if (!userExists(connection, currentUsername)) {
                System.out.println("Username not found.");
                return;
            }

            System.out.println("Enter the current password:");
            String currentPassword = scanner.nextLine();

            if (!validPassword(connection, currentUsername, currentPassword)) {
                System.out.println("Invalid current password!");
            }

            System.out.println("Enter the new username (leave blank to keep current):");
            String newUsername = scanner.nextLine();

            if (!newUsername.isEmpty() && userExists(connection, newUsername)) {
                System.out.println("Username already exists");
            }

            System.out.println("Enter your new password (leave blank to keep current):");
            String newPassword = scanner.nextLine();

            String updateUserQuery = "UPDATE Users SET Username = COALESCE(?, Username), Password = COALESCE(?, Password) WHERE Username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery)) {
                preparedStatement.setString(1, newUsername.isEmpty() ? null : newUsername);
                preparedStatement.setString(2, newPassword.isEmpty() ? null : newPassword);
                preparedStatement.setString(3, currentUsername);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User updated successfully.");
                } else {
                    System.out.println("Failed to update user.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error updating user: " + ex.getMessage());
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

    private static boolean validPassword(Connection connection, String username, String password) throws SQLException {
        String validatePasswordQuery = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(validatePasswordQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

}
