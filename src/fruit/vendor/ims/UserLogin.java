package fruit.vendor.ims;

//UserLogin that will serve as a Login page for the user
//need to make sure that the users are within the table as well
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserLogin {

    private static final int MAX_ATTEMPTS = 3;

    public static boolean isValidUser(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        
         if(isUserTableEmpty(connection)){
            System.out.println("No users exist. Please create a user");
            AddRemoveUser.addUser(connection);
        }

        for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
            System.out.println("Enter username: ");
            String username = scanner.nextLine();

            System.out.println("Enter password: ");
            String password = scanner.nextLine();

            String query = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return true; // Valid user
                } else {
                    System.out.println("Invalid username or password. Attempts left: " + (MAX_ATTEMPTS - attempts - 1));
                }
            } catch (SQLException ex) {
                System.out.println("Error during user validation: " + ex.getMessage());
            }
        }
        return false; // Maximum attempts reached
    }
    
    private static boolean isUserTableEmpty(Connection connection) {
        String checkUserQuery = "SELECT COUNT(*) FROM Users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkUserQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count == 0;
        } catch (SQLException ex) {
            System.out.println("Error adding user: " + ex.getMessage());
            return false;
        }
    }
}