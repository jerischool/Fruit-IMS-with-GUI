/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fruit.vendor.ims;

import java.sql.Connection;
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
public class EditFruitDescTest {
    private static DBManager dbManager;
    private static Connection connection;
    private EditFruitDesc editFruitDesc;
    
    public EditFruitDescTest() {
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
        editFruitDesc = new EditFruitDesc(connection);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of editFruitDescription method, of class EditFruitDesc.
     */
    @Test
    public void testEditFruitDescription() throws SQLException {
        System.out.println("editFruitDescription");
        String name = "Apple";
        double newPrice = 2.0;
        int newQuantity = 20;
        double newAvgWeight = 0.2;
        
        boolean result = editFruitDesc.editFruitDescription(name, newPrice, newQuantity, newAvgWeight);
        assertTrue("Fruit is edited", result);

        // Check if the values are updated in the database
        var resultSet = connection.createStatement().executeQuery("SELECT * FROM Fruits WHERE Name = 'Apple'");
        assertTrue("Fruit should be updated", resultSet.next());
        assertEquals("Price should be updated", newPrice, resultSet.getDouble("Price"), 0.001);
        assertEquals("Quantity should be updated", newQuantity, resultSet.getInt("Quantity"));
        assertEquals("AverageWeight should be updated", newAvgWeight, resultSet.getDouble("AverageWeight"), 0.001);
    
    }
    
}
