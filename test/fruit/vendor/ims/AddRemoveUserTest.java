/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fruit.vendor.ims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author OEM
 */
public class AddRemoveUserTest {

    private static DBManager dbManager;
    private static Connection connection;

    public AddRemoveUserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        dbManager = new DBManager();
        connection = dbManager.getConnection();
    }

    @AfterClass
    public static void tearDownClass() {
        dbManager.closeConnections();
    }

    @Before
    public void setUp() {
        try {
            connection.prepareStatement("DELETE FROM Users").executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error clearing database table: " + ex.getMessage());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addUser method, of class AddRemoveUser.
     */
    @Test
    public void testAddUser() {
        System.out.println("addUser");
        String username = "user1";
        String password = "password1";
        AddRemoveUser.addUser(connection, username, password);
        System.out.println("User added: " + username);

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE Username = ?")) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(username, rs.getString("Username"));
                assertEquals(password, rs.getString("Password"));
            }
        } catch (SQLException ex) {
            fail("SQLException occurred: " + ex.getMessage());
        }
    }

    

}
