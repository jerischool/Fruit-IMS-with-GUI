package fruit.vendor.ims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class AddRemoveFruit {
    
    //adds a new fruit to the database
    public static void addFruit(Connection connection, String name, int quantity, double price, double averageWeight) {
        String query = "INSERT INTO Fruits (Name, Quantity, Price, AverageWeight) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setDouble(3, price);
            preparedStatement.setDouble(4, averageWeight);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error adding fruit: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //removes an existing fruit from the database
    public static void removeFruit(Connection connection, String name) {
        String query = "DELETE FROM Fruits WHERE Name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error removing fruit: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
