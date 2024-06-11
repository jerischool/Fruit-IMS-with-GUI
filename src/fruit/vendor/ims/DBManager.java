package fruit.vendor.ims;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DBManager {

    // database url and credentials
    private static final String URL = "jdbc:derby:FruitIMS_DB;create=true";
    private static final String USER_NAME = "pdc";
    private static final String PASSWORD = "pdc";

    // connection object
    private Connection conn;

    // constructor to establish a connection when an instance is created
    DBManager() {
        establishConnection();
    }

    // main method for testing connection
    public static void main(String[] args) {
        DBManager dbManager = new DBManager();
        System.out.println(dbManager.getConnection());
    }

    // method to get the current connection
    public Connection getConnection() {
        return this.conn;
    }

    // method to establish a database connection
    private void establishConnection() {
        try {
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // method to close the database connection
    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
                JOptionPane.showMessageDialog(null, "Connection Closed Successfully.", "Information", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error closing connection: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
