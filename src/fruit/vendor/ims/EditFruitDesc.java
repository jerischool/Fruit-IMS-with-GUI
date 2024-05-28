package fruit.vendor.ims;

//edit this so that it makes use of the database and have the ability to edit the descriptions of fruits within the table 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class EditFruitDesc {

    public static void editFruitDesc(Connection connection) {
        Scanner scan = new Scanner(System.in);

        try {
            System.out.println("Enter the name of the fruit to edit:");
            String name = scan.nextLine();

            System.out.println("Enter new price per Unit ($): ");
            double newPrice = scan.nextDouble();

            System.out.println("Enter new quantity: ");
            int newQuantity = scan.nextInt();

            System.out.println("Enter new average weight: ");
            double newAvgWeight = scan.nextDouble();

            String updateFruitQuery = "UPDATE Fruits SET Price = ?, Quantity = ?, AverageWeight = ? WHERE Name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateFruitQuery)) {
                preparedStatement.setDouble(1, newPrice);
                preparedStatement.setInt(2, newQuantity);
                preparedStatement.setDouble(3, newAvgWeight);
                preparedStatement.setString(4, name);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println(name + " details updated successfully.");
                } else {
                    System.out.println(name + " not found in the inventory.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error editing fruit details: " + ex.getMessage());
        }
    }
}

