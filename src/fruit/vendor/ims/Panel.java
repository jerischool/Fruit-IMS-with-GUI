/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fruit.vendor.ims;

/**
 *
 * @author jeril
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Panel extends JPanel {
    private FruitTable fruitTable;
    private UserTable userTable;
    private DBManager dbManager;
    private Connection connection;

    public Panel() {
        initComponents();
        dbManager = new DBManager();
        connection = dbManager.getConnection();
        fruitTable = new FruitTable();
        userTable = new UserTable();
        fruitTable.createFruitTable();
        userTable.createUserTable();
    }

    private void initComponents() {
        // Create GUI components such as buttons, labels, text fields, etc.
        // Add action listeners to buttons to handle user interactions
        // Set up layout manager to arrange components
    }

    private void addFruit() {
        // Implement logic to add fruit to the database
        AddRemoveFruit.addFruit(connection);
    }

    private void removeFruit() {
        // Implement logic to remove fruit from the database
        AddRemoveFruit.removeFruit(connection);
    }

    private void editFruitDescription() {
        // Implement logic to edit fruit description in the database
        EditFruitDesc.editFruitDesc(connection);
    }

    private void addUser() {
        // Implement logic to add user to the database
        AddRemoveUser.addUser(connection);
    }

    private void removeUser() {
        // Implement logic to remove user from the database
        AddRemoveUser.removeUser(connection);
    }

    private void editUserProfile() {
        // Implement logic to edit user profile in the database
        EditUserProf.editUserProf(connection);
    }

    private void displayFruitsTable() {
        // Implement logic to display fruits table in the GUI
        // You may use a JTable to display the data
        // Retrieve data from the database using FruitTable class
    }

    private void displayUsersTable() {
        // Implement logic to display users table in the GUI
        // You may use a JTable to display the data
        // Retrieve data from the database using UserTable class
    }

    private void exitApplication() {
        // Implement logic to close database connections and exit the application
        dbManager.closeConnections();
        System.exit(0);
    }
}
