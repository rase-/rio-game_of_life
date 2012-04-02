/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlife;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author scolphoy
 */
public class LifeJobTest {
    
    public LifeJobTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getSteps method, of class LifeJob.
     */
    @Test
    public void testGetSteps() {
        System.out.println("getSteps");
        LifeJob instance = null;
        int expResult = 0;
        int result = instance.getSteps();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCells method, of class LifeJob.
     */
    @Test
    public void testGetCells() {
        System.out.println("getCells");
        LifeJob instance = null;
        boolean[][] expResult = null;
        boolean[][] result = instance.getCells();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class LifeJob.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        LifeJob instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
