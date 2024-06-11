package fruit.vendor.ims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class EditUserProf {

    //edits an exisitng user profile 
    public static void editUserProf(Connection connection, String currentUsername, String newUsername, String newPassword) {
        String updateUserQuery = "UPDATE Users SET Username = COALESCE(?, Username), Password = COALESCE(?, Password) WHERE Username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery)) {
            preparedStatement.setString(1, newUsername.isEmpty() ? null : newUsername);
            preparedStatement.setString(2, newPassword.isEmpty() ? null : newPassword);
            preparedStatement.setString(3, currentUsername);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "User updated successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error updating user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
