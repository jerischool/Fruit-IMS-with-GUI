package fruit.vendor.ims;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLogin {

    // maximum number of login attempts allowed
    private static final int MAX_ATTEMPTS = 3;

    // method to validate user login
    public static boolean isValidUser(Connection connection) {
        int attempts = 0;
        
        while (attempts < MAX_ATTEMPTS) {
            // prompt user for username and password
            String username = JOptionPane.showInputDialog(null, "Enter username:");
            String password = JOptionPane.showInputDialog(null, "Enter password:");

            // sql query to check if the user exists
            String query = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                // if user is found, return true
                if (resultSet.next()) {
                    return true; // valid user
                } else {
                    // if user is not found, show error message and increment attempts
                    JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    attempts++;
                }
            } catch (SQLException ex) {
                // display an error message if an sql exception occurs
                JOptionPane.showMessageDialog(null, "Error during user validation: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return false; // exit on sql error
            }
        }
        
        // if maximum attempts are reached, show error message and return false
        JOptionPane.showMessageDialog(null, "Maximum attempts reached. Exiting...", "Error", JOptionPane.ERROR_MESSAGE);
        return false; // login failed after maximum attempts
    }
}
