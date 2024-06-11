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
public class UserLoginTest {

    private static DBManager dbManager;
    private static Connection connection;

    public UserLoginTest() {
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
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of isValidUser method, of class UserLogin.
     */
    @Test
    public void testIsValidUser() {
        System.out.println("isValidUser");
        String validUsername = "validUsername";
        String validPassword = "validPassword";
        
        boolean result = UserLogin.isValidUser(connection);
        if(result){
        assertTrue("User is valid", result);
        } else {
            fail("User is not valid");
        }
        
    }

}
