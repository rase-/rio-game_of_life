/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlife;

import concurrency.ArrayUpdatingUnit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author scolphoy
 */
public class SimpleConcurrentLife {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        LifeJob job = args.length>0?new LifeJob(args[0]):new LifeJob("life_800_10000.txt");
        System.err.println("File read, " + (System.currentTimeMillis() - start) + "ms so far");

        // Make the reference and temporary arrays
        boolean[][][] arrays = new boolean[2][][];
        arrays[0] = job.getCells();
        arrays[1] = new boolean[arrays[0].length][arrays[0].length];
        System.err.println("Arrays made, " + (System.currentTimeMillis() - start) + "ms so far");

        
        
        // Bake 'n switch
        int threads = 16;
        for (int round = 0; round < job.getSteps(); round++) {
            CountDownLatch latch = new CountDownLatch(threads);
            for (int i = 0; i < threads; i++) {
                new Thread(new ArrayUpdatingUnit(arrays[0], arrays[1], threads, i, latch)).start();
            }
            
            latch.await();
            //LifeFunctions.updateArrays(arrays[0], arrays[1], 0, 1);
            swapArrays(arrays); // The newest will always be arrays[0] !
        }
        System.err.println("Baked, " + (System.currentTimeMillis() - start) + "ms so far");

        
        // Print the result, same format as input except the job header.
        StringBuilder fileContent = new StringBuilder();
        for (int y = 0; y < arrays[0].length; y++) {
            for (int x = 0; x < arrays[0][y].length; x++) {
                fileContent.append((arrays[0][y][x] ? 1 : 0)).append(" ");
            }
            fileContent.deleteCharAt(fileContent.length()-1).append('\n');
            //System.out.println(output);
            
        }
        
        long stop = System.currentTimeMillis();
        FileWriter writer = new FileWriter(new File("result.txt"));
        writer.write(fileContent.toString());
        writer.close();
        
        
        System.err.println("Whoa, that was snappy! Fast as lightning! only took us " + (stop-start) + "ms altogether!");
        System.err.println("Using class SimpleConcurrentLife");
        System.err.println("Using " + threads + " threads");
        SolutionChecker checker = new SolutionChecker(new File("result.txt"), new File("life_800_10000_expected_result.txt"));
        System.out.println(checker.checkSolution());
    }

    private static void swapArrays(boolean[][][] arrays) {
        boolean[][] temp = arrays[0];
        arrays[0] = arrays[1];
        arrays[1] = temp;
    }
    
   
}
