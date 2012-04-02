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
public class CellGroupTest {
    
    public CellGroupTest() {
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
     * Test of result method, of class CellGroup.
     */
    @Test
    public void testResult() {
        System.out.println("result");
        CellGroup instance = new CellGroupImpl();
        CellGroup expResult = null;
        CellGroup result = instance.result();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class CellGroupImpl implements CellGroup {

        public CellGroup result() {
            return null;
        }
    }
}
