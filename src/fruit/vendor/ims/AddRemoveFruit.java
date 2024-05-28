package fruit.vendor.ims;

//edit this so that it makes use of the database and has the ability to add to the table and remove fruits from the table
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;

public class AddRemoveFruit {

    public static void addFruit(Connection connection) {
        Scanner scan = new Scanner(System.in);

        try {
            System.out.println("Enter the name of the new Fruit: ");
            String name = scan.nextLine();

            // Check if the fruit already exists in the table
            if (isFruitExists(connection, name)) {
                System.out.println("The fruit already exists in the inventory.");
                return;
            }

            System.out.println("Enter the amount available: ");
            int quantity = scan.nextInt();

            System.out.println("Enter the price per unit: ");
            double price = scan.nextDouble();

            System.out.println("Enter the average weight per unit: ");
            double averageWeight = scan.nextDouble();

            String insertFruitQuery = "INSERT INTO Fruits (Name, Quantity, Price, AverageWeight) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertFruitQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, quantity);
                preparedStatement.setDouble(3, price);
                preparedStatement.setDouble(4, averageWeight);
                preparedStatement.executeUpdate();
                System.out.println(name + " added successfully!");
            }
        } catch (SQLException ex) {
            System.out.println("Error adding fruit: " + ex.getMessage());
        }
    }

    private static boolean isFruitExists(Connection connection, String fruitName) throws SQLException {
        String checkFruitQuery = "SELECT COUNT(*) FROM Fruits WHERE Name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkFruitQuery)) {
            preparedStatement.setString(1, fruitName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        }
    }

    public static void removeFruit(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the name of the fruit to remove:");
            String name = scanner.nextLine();

            String deleteFruitQuery = "DELETE FROM Fruits WHERE Name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteFruitQuery)) {
                preparedStatement.setString(1, name);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println(name + " removed successfully!");
                } else {
                    System.out.println(name + " not found in the inventory.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error removing fruit: " + ex.getMessage());
        }
    }
}
