package fruit.vendor.ims;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Panel extends JFrame {

    private final DBManager dbManager;
    private final Connection connection;

    public Panel() {
        dbManager = new DBManager();
        connection = dbManager.getConnection();
        initComponents();
    }

    private void initComponents() {
        setTitle("Fruit Processing Application");
        setLayout(new BorderLayout());

        // Create buttons for each option
        JButton addFruitButton = new JButton("Add Fruit");
        JButton removeFruitButton = new JButton("Remove Fruit");
        JButton editFruitDescButton = new JButton("Edit Fruit Description");
        JButton addUserButton = new JButton("Add User");
        JButton removeUserButton = new JButton("Remove User");
        JButton editUserProfileButton = new JButton("Edit User Profile");
        JButton displayFruitsTableButton = new JButton("Display Fruits Table");
        JButton displayUsersTableButton = new JButton("Display Users Table");
        JButton exitButton = new JButton("Exit");

        // Add action listeners to buttons
        addFruitButton.addActionListener(this::addFruit);
        removeFruitButton.addActionListener(this::removeFruit);
        editFruitDescButton.addActionListener(this::editFruitDescription);
        addUserButton.addActionListener(this::addUser);
        removeUserButton.addActionListener(this::removeUser);
        editUserProfileButton.addActionListener(this::editUserProfile);
        displayFruitsTableButton.addActionListener(this::displayFruitsTable);
        displayUsersTableButton.addActionListener(this::displayUsersTable);
        exitButton.addActionListener(this::exitApplication);

        // Create panel to hold buttons
        JPanel buttonPanel = new JPanel(new GridLayout(9, 1));
        buttonPanel.add(addFruitButton);
        buttonPanel.add(removeFruitButton);
        buttonPanel.add(editFruitDescButton);
        buttonPanel.add(addUserButton);
        buttonPanel.add(removeUserButton);
        buttonPanel.add(editUserProfileButton);
        buttonPanel.add(displayFruitsTableButton);
        buttonPanel.add(displayUsersTableButton);
        buttonPanel.add(exitButton);

        // Add button panel to main panel
        add(buttonPanel, BorderLayout.CENTER);

        // Set up the frame
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addUser(ActionEvent e) {
        String username = JOptionPane.showInputDialog(this, "Enter Username:");
        String password = JOptionPane.showInputDialog(this, "Enter Password:");

        if (checkUserExists(username)) {
            JOptionPane.showMessageDialog(this, "User already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            AddRemoveUser.addUser(connection, username, password);
            JOptionPane.showMessageDialog(this, "User added successfully.");
        }
    }

    private void addFruit(ActionEvent e) {
        String name = JOptionPane.showInputDialog(this, "Enter Fruit Name:");

        if (checkFruitExists(name)) {
            JOptionPane.showMessageDialog(this, "Fruit already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String quantityStr = JOptionPane.showInputDialog(this, "Enter Quantity:");
            String priceStr = JOptionPane.showInputDialog(this, "Enter Price:");
            String averageWeightStr = JOptionPane.showInputDialog(this, "Enter Average Weight:");

            try {
                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(priceStr);
                double averageWeight = Double.parseDouble(averageWeightStr);

                AddRemoveFruit.addFruit(connection, name, quantity, price, averageWeight);
                JOptionPane.showMessageDialog(this, "Fruit added successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values for quantity, price, and average weight.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeUser(ActionEvent e) {
        String username = JOptionPane.showInputDialog(this, "Enter Username to Remove:");

        if (!checkUserExists(username)) {
            JOptionPane.showMessageDialog(this, "User doesn't exist.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            AddRemoveUser.removeUser(connection, username);
            JOptionPane.showMessageDialog(this, "User removed successfully.");
        }
    }

    private void removeFruit(ActionEvent e) {
        String name = JOptionPane.showInputDialog(this, "Enter Fruit Name to Remove:");

        if (!checkFruitExists(name)) {
            JOptionPane.showMessageDialog(this, "Fruit doesn't exist.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            AddRemoveFruit.removeFruit(connection, name);
            JOptionPane.showMessageDialog(this, "Fruit removed successfully.");
        }
    }

    private void editUserProfile(ActionEvent e) {
        try {
            String currentUsername = JOptionPane.showInputDialog(this, "Enter Username to Edit:");
            if (currentUsername == null || currentUsername.isEmpty()) {
                return;
            }

            if (!checkUserExists(currentUsername)) {
                JOptionPane.showMessageDialog(this, "User doesn't exist.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String currentPassword = JOptionPane.showInputDialog(this, "Enter Current Password:");

            if (!validPassword(currentUsername, currentPassword)) {
                JOptionPane.showMessageDialog(this, "Invalid current password.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // You should return after showing error message
            }

            String newUsername = JOptionPane.showInputDialog(this, "Enter New Username (leave blank to keep current):");

            if (!newUsername.isEmpty() && checkUserExists(newUsername)) {
                JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // You should return after showing error message
            }

            String newPassword = JOptionPane.showInputDialog(this, "Enter New Password (leave blank to keep current):");

            // Call the method from EditUserProf class to update the user profile
            EditUserProf.editUserProf(connection, currentUsername, newUsername, newPassword);
            JOptionPane.showMessageDialog(this, "User updated successfully.");
        } catch (Exception ex) {
            ex.printStackTrace(); // Print the stack trace to debug any unexpected exceptions
            JOptionPane.showMessageDialog(this, "Error updating user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editFruitDescription(ActionEvent e) {
    try {
        String name = JOptionPane.showInputDialog(this, "Enter Fruit Name to Edit:");

        if (!checkFruitExists(name)) {
            JOptionPane.showMessageDialog(this, "Fruit doesn't exist.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double newPrice = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter New Price:"));
        int newQuantity = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter New Quantity:"));
        double newAvgWeight = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter New Average Weight:"));
        EditFruitDesc fruitDescEditor = new EditFruitDesc(connection);
        boolean success = fruitDescEditor.editFruitDescription(name, newPrice, newQuantity, newAvgWeight);

        if (success) {
            JOptionPane.showMessageDialog(this, name + " details updated successfully.");
        } else {
            JOptionPane.showMessageDialog(this, name + " not found in the inventory.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values for price, quantity, and average weight.", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void displayFruitsTable(ActionEvent e) {
        JFrame tableFrame = new JFrame("Fruits Table");
        JTextArea textArea = new JTextArea(20, 40);
        textArea.setText(getFruitsTable());
        textArea.setEditable(false);
        tableFrame.add(new JScrollPane(textArea));
        tableFrame.pack();
        tableFrame.setLocationRelativeTo(null);
        tableFrame.setVisible(true);
    }

    private void displayUsersTable(ActionEvent e) {
        JFrame tableFrame = new JFrame("Users Table");
        JTextArea textArea = new JTextArea(20, 40);
        textArea.setText(getUsersTable());
        textArea.setEditable(false);
        tableFrame.add(new JScrollPane(textArea));
        tableFrame.pack();
        tableFrame.setLocationRelativeTo(null);
        tableFrame.setVisible(true);
    }

    private String getFruitsTable() {
        StringBuilder sb = new StringBuilder();
        String query = "SELECT * FROM Fruits";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                sb.append(resultSet.getMetaData().getColumnName(i)).append("\t");
            }
            sb.append("\n");

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    sb.append(resultSet.getString(i)).append("\t");
                }
                sb.append("\n");
            }
        } catch (SQLException e) {
            sb.append("Error displaying Fruits table: ").append(e.getMessage());
        }
        return sb.toString();
    }

    private String getUsersTable() {
        StringBuilder sb = new StringBuilder();
        String query = "SELECT ID, Username FROM Users";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            sb.append("ID\tUsername\n");
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String username = resultSet.getString("Username");
                sb.append(id).append("\t").append(username).append("\n");
            }
        } catch (SQLException e) {
            sb.append("Error displaying Users table: ").append(e.getMessage());
        }
        return sb.toString();
    }

    private boolean checkUserExists(String username) {
        String query = "SELECT * FROM Users WHERE Username = '" + username + "'";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean validPassword(String username, String password) {
        String validatePasswordQuery = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(validatePasswordQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean checkFruitExists(String name) {
        String query = "SELECT * FROM Fruits WHERE Name = '" + name + "'";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void exitApplication(ActionEvent e) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public static void launchGUI() {
        SwingUtilities.invokeLater(() -> new Panel().setVisible(true));
    }
}
