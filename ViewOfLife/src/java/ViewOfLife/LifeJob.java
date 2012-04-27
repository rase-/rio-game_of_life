/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewOfLife;

import concurrency.ArrayUpdatingUnit;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Game of life -job unit. Internal representation of a game of life board and
 * its status.
 *
 * @author scolphoy
 */
public class LifeJob extends Thread implements Runnable {

    private boolean done;
    private boolean running;
    private long startTime;
    private int steps;
    private boolean[][] cells;
    private String result;
    private long endTime;
    private int threads = 1;
    private int step;

    /**
     * Creates a LifeJob-object from an input stream
     *
     * @param inputData The input stream containing job data
     * @throws IOException An error with the input stream
     */
    public LifeJob(InputStream inputData) throws IOException {
        if (inputData.available() == 0) {
            throw new IOException("No file entered!");
        }

        InputStreamReader inputStreamReader = new InputStreamReader(inputData);
        BufferedReader inputReader = new BufferedReader(inputStreamReader);

        // Reading the job parameters and constructing a proper array for the board
        int dimension, steps;
        {
            String[] jobSettings = inputReader.readLine().trim().split(" ");
            dimension = Integer.parseInt(jobSettings[0]);
            steps = Integer.parseInt(jobSettings[1]);
        }
        boolean[][] cells = new boolean[dimension][dimension];


        // Reading the initial contents for the game of life board
        for (int y = 0; y < dimension; y++) {
            for (int x = 0; x < dimension; x++) {
                int in = inputReader.read();
                while ((in == '0' || in == '1' || in == -1) == false) {  // repeat until 0, 1 or EOF
                    in = inputReader.read();
                } // Here in is either -1, 0 or 1      
                cells[y][x] = (in == '1' ? true : false); // If EOF is reached before end of the board, assuming all the rest of the cells dead
            }
        }


        // Now setting the object's fields
        this.steps = steps;
        this.cells = cells;
    }

    /**
     * Method for computing the job
     *
     * @throws InterruptedException Background thread got interrupted
     */
    private void doTheJob() throws InterruptedException {
        done = false;
        running = true;
        startTime = System.currentTimeMillis();


        // Make the reference and temporary arrays
        boolean[][][] arrays = new boolean[2][][];
        arrays[0] = getCells();
        arrays[1] = new boolean[arrays[0].length][arrays[0].length];

        // Bake 'n switch
        for (int round = 0; round < totalSteps(); round++) {
            CountDownLatch latch = new CountDownLatch(threads);
            for (int i = 0; i < threads; i++) {
                new Thread(new ArrayUpdatingUnit(arrays[0], arrays[1], threads, i, latch)).start();
            }

            latch.await();
            swapArrays(arrays); // The newest will always be arrays[0] !
            step = round;
        }

        // Print the result, same format as input except the job header.
        StringBuilder fileContent = new StringBuilder();
        for (int y = 0; y < arrays[0].length; y++) {
            for (int x = 0; x < arrays[0][y].length; x++) {
                fileContent.append((arrays[0][y][x] ? 1 : 0)).append(" ");
            }
            fileContent.deleteCharAt(fileContent.length() - 1).append('\n');
        }


        result = fileContent.toString();
        endTime = System.currentTimeMillis();
        running = false;
        done = true;

    }

    /**
     * Swaps who arrays in a three dimensional boolean array
     *
     * @param arrays The target array
     */
    private static void swapArrays(boolean[][][] arrays) {
        boolean[][] temp = arrays[0];
        arrays[0] = arrays[1];
        arrays[1] = temp;
    }

    /**
     * Returns a pointer to the game of life -board.
     *
     * @return Game of Life -board with coordinates [y][x], cell with the value
     * True represents a live cell, a cell with the value False represents a
     * dead cell.
     */
    public boolean[][] getCells() {
        return cells;
    }

    /**
     * Returns a descriptive String about this job unit.
     *
     * @return A String with information about the board measures and the total
     * step count.
     */
    @Override
    public String toString() {
        return "Game of life, " + cells.length + "x" + cells.length + ", " + steps + " steps";
    }

    /**
     * Returns the running status of the job
     *
     * @return True if background job is running, False if not
     */
    public boolean running() {
        return running;
    }

    /**
     * Returns if the job is done or not
     *
     * @return True if done, False if not.
     */
    public boolean done() {
        return done;
    }

    /**
     * Returns a String presentation of the computed result
     *
     * @return A String representing the computed result, null if not done
     */
    public String result() {
        return result;
    }

    /**
     * Returns the number of threads to use.
     *
     * @return The number of threads.
     */
    public int getThreadCount() {
        return threads;
    }

    /**
     * Sets the amount of threads to use. The thread count must be at least 1.
     * Values 0 or less will not change the thread count.
     *
     * @param count The thread count to use.
     * @return True if change was successful, False if not.
     */
    public boolean setThreadCount(int count) {
        if (count > 0) {
            threads = count;
            return true;
        }
        return false;
    }

    /**
     * Returns the time current job was started.
     *
     * @return The time when the job was started in milliseconds from unix
     * epoch, 0 if not started.
     */
    long startTime() {
        return startTime;
    }

    /**
     * Returns the time current job was finished.
     *
     * @return The time when the job finished in milliseconds from unix epoch, 0
     * if not finished.
     */
    long endTime() {
        return endTime;
    }

    /**
     * Returns the current step in progress
     *
     * @return The current step
     */
    int currentStep() {
        return step;
    }

    /**
     * Returns the count of steps for this job.
     *
     * @return Number of steps total.
     */
    public int totalSteps() {
        return steps;
    }

    @Override
    /**
     * The run method for implementing the interface Runnable
     *
     */
    public void run() {
        try {
            doTheJob();
        } catch (InterruptedException ex) {
            Logger.getLogger(LifeJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
