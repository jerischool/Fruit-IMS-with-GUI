package fruit.vendor.ims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditFruitDesc {

    private final Connection connection;

    //establishes connection to the database
    public EditFruitDesc(Connection connection) {
        this.connection = connection;
    }

    //edits an  existing fruits attributes
    public boolean editFruitDescription(String name, double newPrice, int newQuantity, double newAvgWeight) {
        String updateFruitQuery = "UPDATE Fruits SET Price = ?, Quantity = ?, AverageWeight = ? WHERE Name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateFruitQuery)) {
            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setInt(2, newQuantity);
            preparedStatement.setDouble(3, newAvgWeight);
            preparedStatement.setString(4, name);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
