package fruit.vendor.ims;

import java.sql.Connection;
import javax.swing.JOptionPane;

public class FruitProcessingApplication {

    public static void main(String[] args) {
        // initialize dbmanager and connection
        DBManager dbManager = new DBManager();
        Connection connection = dbManager.getConnection();

        // ensure valid user login before proceeding
        if (!UserLogin.isValidUser(connection)) {
            JOptionPane.showMessageDialog(null, "Maximum attempts reached. Exiting...", "Error", JOptionPane.ERROR_MESSAGE);
            return; // exit if the user login is invalid
        }

        // create the tables if they do not exist
        FruitTable fruitTable = new FruitTable(dbManager);
        fruitTable.createFruitTable();

        UserTable userTable = new UserTable(dbManager);
        userTable.createUserTable();

        // launch the gui panel
        Panel.launchGUI();

        // close the database connection when the application exits
        dbManager.closeConnections();
    }
}
