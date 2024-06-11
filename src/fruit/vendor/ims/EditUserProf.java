package fruit.vendor.ims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class EditUserProf {

    // method to edit the profile of a user
    public static void editUserProf(Connection connection, String currentUsername, String newUsername, String newPassword) {
        // sql query to update user details, using coalesce to keep old values if new ones are not provided
        String updateUserQuery = "UPDATE Users SET Username = COALESCE(?, Username), Password = COALESCE(?, Password) WHERE Username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery)) {
            // setting the parameters for the prepared statement
            preparedStatement.setString(1, newUsername.isEmpty() ? null : newUsername);
            preparedStatement.setString(2, newPassword.isEmpty() ? null : newPassword);
            preparedStatement.setString(3, currentUsername);

            // executing the update and checking how many rows were affected
            int rowsAffected = preparedStatement.executeUpdate();

            // displaying a message based on the result of the update
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "User updated successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            // displaying an error message if an sql exception occurs
            JOptionPane.showMessageDialog(null, "Error updating user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
