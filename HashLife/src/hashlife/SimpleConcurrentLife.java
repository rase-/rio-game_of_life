/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlife;

import concurrency.ArrayUpdatingUnit;
import java.io.IOException;
import java.util.ArrayList;
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
        for (int y = 0; y < arrays[0].length; y++) {
            StringBuilder output = new StringBuilder();
            for (int x = 0; x < arrays[0][y].length; x++) {
                output.append((arrays[0][y][x] ? 1 : 0)).append(" ");
            }
            output.deleteCharAt(output.length()-1);
            System.out.println(output);
        }
        
        long stop = System.currentTimeMillis();
        
        System.err.println("Whoa, that was snappy! Fast as lightning! only took us " + (stop-start) + "ms!");
        System.err.println("Using class SimpleConcurrentLife");
        System.err.println("Using " + threads + "threads");
        LifeJob expected = new LifeJob("life_800_10000_expected_result.txt");
        
        
    }

    private static void swapArrays(boolean[][][] arrays) {
        boolean[][] temp = arrays[0];
        arrays[0] = arrays[1];
        arrays[1] = temp;
    }
    
   
}
