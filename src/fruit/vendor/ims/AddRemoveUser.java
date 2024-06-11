package fruit.vendor.ims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class AddRemoveUser {

    // method to add a user to the database
    public static void addUser(Connection connection, String username, String password) {
        // sql query to insert a new user record
        String query = "INSERT INTO Users (Username, Password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // setting the parameters for the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            // executing the update
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            // displaying an error message if an sql exception occurs
             JOptionPane.showMessageDialog(null, "Error adding user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to remove a user from the database
    public static void removeUser(Connection connection, String username) {
        // sql query to delete a user record
        String query = "DELETE FROM Users WHERE Username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // setting the parameter for the prepared statement
            preparedStatement.setString(1, username);
            // executing the update
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            // displaying an error message if an sql exception occurs
             JOptionPane.showMessageDialog(null, "Error removing user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
