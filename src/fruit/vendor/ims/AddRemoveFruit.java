package fruit.vendor.ims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class AddRemoveFruit {

    // method to add a fruit to the database
    public static void addFruit(Connection connection, String name, int quantity, double price, double averageWeight) {
        // sql query to insert a new fruit record
        String query = "INSERT INTO Fruits (Name, Quantity, Price, AverageWeight) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // setting the parameters for the prepared statement
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setDouble(3, price);
            preparedStatement.setDouble(4, averageWeight);
            // executing the update
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            // displaying an error message if an sql exception occurs
            JOptionPane.showMessageDialog(null, "Error adding fruit: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to remove a fruit from the database
    public static void removeFruit(Connection connection, String name) {
        // sql query to delete a fruit record
        String query = "DELETE FROM Fruits WHERE Name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // setting the parameter for the prepared statement
            preparedStatement.setString(1, name);
            // executing the update
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            // displaying an error message if an sql exception occurs
             JOptionPane.showMessageDialog(null, "Error removing fruit: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
