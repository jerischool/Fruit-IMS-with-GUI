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
public class EditUserProfTest {

    private static DBManager dbManager;
    private static Connection connection;
    private EditUserProf editUserProf;

    public EditUserProfTest() {
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
        editUserProf = new EditUserProf();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of editUserProf method, of class EditUserProf.
     */
    @Test
    public void testEditUserProf() throws SQLException {
        System.out.println("editUserProf");

        String currentUsername = "user4";
        String newUsername = "testUser";
        String newPassword = "testPassword";

        // Add a test user with the current username and password
        AddRemoveUser.addUser(connection, currentUsername, "password4");

        // Call the editUserProf method to update the user's information
        EditUserProf.editUserProf(connection, currentUsername, newUsername, newPassword);

        // Verify that the user details are updated in the database
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE Username = ?")) {
            statement.setString(1, newUsername);
            try (ResultSet resultSet = statement.executeQuery()) {
                assertTrue("User exists in the database", resultSet.next());
                assertEquals("Username should be updated", newUsername, resultSet.getString("Username"));
                assertEquals("Password should be updated", newPassword, resultSet.getString("Password"));
            }
        } catch (SQLException ex) {
            fail("SQL exception occurred: " + ex.getMessage());
        }
    }

}
