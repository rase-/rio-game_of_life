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
public class ReadFileTest {

    private static LifeJob resultLifeJob;
    private static String filename;
    
    public ReadFileTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() throws Exception {
        filename = "life_800_10000.txt";
        resultLifeJob = ReadFile.fileToLifeJob(filename);
        long stop = System.currentTimeMillis(); 
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void luetaanOikeatMitat() throws Exception {
        int expDimension = 800;
        assertEquals(expDimension, resultLifeJob.getCells().length);
    }
    
    @Test
    public void luetaanOikeaAskelmaara() {
        int expSteps = 10000;
        assertEquals(expSteps, resultLifeJob.getSteps());
    }
    
    @Test
    public void lukeminenOnNopea() throws Exception{
        int limit_millis = 500;
        long start = System.currentTimeMillis();
        resultLifeJob = ReadFile.fileToLifeJob(filename);
        long stop = System.currentTimeMillis();
        System.out.println("Tiedosto luettiin ajassa: " + (stop-start) + "ms");
        assertTrue(stop-start < limit_millis);
    }
    
    @Test
    public void vasenYlanurkkaOikein() {
        boolean[][] expected = {{false, false, false, true, true, false},
                                {true, false, false, false, false, true},
                                {false, true, true, false, false, true},
                                {true, true, true, true, true, false},
                                {true, true, true, true, true, true},
                                {true, false, true, false, false, false}};
        
        for (int y = 0; y < expected.length; y++) {
            for (int x = 0; x < expected[y].length; x++) {
                assertEquals(expected[y][x], resultLifeJob.getCells()[y][x]);
            }
        }
                            
        }
    }

