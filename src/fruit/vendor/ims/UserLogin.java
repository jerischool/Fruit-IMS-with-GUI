package fruit.vendor.ims;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLogin {

    //user is given 3 attempts to input the correct username and password
    private static final int MAX_ATTEMPTS = 3;

    //checks if user exists within the database
    public static boolean isValidUser(Connection connection) {
        int attempts = 0;
        
        while (attempts < MAX_ATTEMPTS) {
            String username = JOptionPane.showInputDialog(null, "Enter username:");
            String password = JOptionPane.showInputDialog(null, "Enter password:");

            String query = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return true; // Valid user
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    attempts++;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error during user validation: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Exit on SQL error
            }
        }
        
        JOptionPane.showMessageDialog(null, "Maximum attempts reached. Exiting...", "Error", JOptionPane.ERROR_MESSAGE);
        return false; // Login failed after maximum attempts
    }
}
