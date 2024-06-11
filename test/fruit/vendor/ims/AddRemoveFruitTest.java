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

public class AddRemoveFruitTest {

    private static DBManager dbManager;
    private static Connection connection;

    public AddRemoveFruitTest() {
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
    public void setUp() throws SQLException {
        try {
            connection.prepareStatement("DELETE FROM Fruits").executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error clearing database table: " + ex.getMessage());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addFruit method, of class AddRemoveFruit.
     */
    @Test
    public void testAddFruit() {
        System.out.println("addFruit");
        String name = "Apple";
        int quantity = 10;
        double price = 1.5;
        double averageWeight = 100.0;

        AddRemoveFruit.addFruit(connection, name, quantity, price, averageWeight);
        System.out.println("Fruit added: " + name);

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Fruits WHERE Name = ?")) {
            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(name, rs.getString("Name"));
                assertEquals(quantity, rs.getInt("Quantity"));
                assertEquals(price, rs.getDouble("Price"), 0.001);
                assertEquals(averageWeight, rs.getDouble("AverageWeight"), 0.001);
            }
        } catch (SQLException ex) {
            fail("SQLException occurred: " + ex.getMessage());
        }
    }
    
    /**
     * Test of removeFruit method, of class AddRemoveFruit.
     */
    @Test
    public void testRemoveFruit() {
        System.out.println("removeFruit");
        String name = "Apple";

        // Remove the fruit
        AddRemoveFruit.removeFruit(connection, name);
        System.out.println("Fruit " + name + " has been removed!");

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Fruits WHERE Name = ?")) {
            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                assertFalse(rs.next());
            }
        } catch (SQLException ex) {
            fail("SQLException occurred: " + ex.getMessage());
        }

    }
}
