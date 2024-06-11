package fruit.vendor.ims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class AddRemoveUser {
    
    //adds a new user to the database
    public static void addUser(Connection connection, String username, String password) {
        String query = "INSERT INTO Users (Username, Password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error adding user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //removes an existing user from the database
    public static void removeUser(Connection connection, String username) {
        String query = "DELETE FROM Users WHERE Username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error removing user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
