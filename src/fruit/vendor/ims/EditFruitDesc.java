package fruit.vendor.ims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditFruitDesc {

    // connection object to interact with the database
    private final Connection connection;

    // constructor to initialize the connection
    public EditFruitDesc(Connection connection) {
        this.connection = connection;
    }

    // method to edit the description of a fruit
    public boolean editFruitDescription(String name, double newPrice, int newQuantity, double newAvgWeight) {
        // sql query to update fruit details
        String updateFruitQuery = "UPDATE Fruits SET Price = ?, Quantity = ?, AverageWeight = ? WHERE Name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateFruitQuery)) {
            // setting the parameters for the prepared statement
            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setInt(2, newQuantity);
            preparedStatement.setDouble(3, newAvgWeight);
            preparedStatement.setString(4, name);
            // executing the update and checking how many rows were affected
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            // printing the stack trace if an sql exception occurs
            ex.printStackTrace();
            return false;
        }
    }
}
