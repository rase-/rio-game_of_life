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
     * Luo LifeJob-olion askelmäärän ja taulukkoviittauksen perusteella
     *
     * @param steps Askelten lukumäärä
     * @param cells Viite pelilautaa esittävään kaksiulotteiseen
     * boolean-taulukkoon
     */
    public LifeJob(InputStream inputData) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputData);
        BufferedReader inputReader = new BufferedReader(inputStreamReader);
        if (inputData.available() == 0) {
            throw new IOException("No file entered!");
        }

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



        this.steps = steps;
        this.cells = cells;
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
     * Compares two job units with each other
     *
     * @param that The job unit to compare to
     * @return true if the units are similar, false if not. The comparison
     * checks the total step count, board measures and content.
     */
    public boolean equals(LifeJob that) {
        if (this.steps != that.steps) {
            return false;
        }
        if (this.cells.length != that.cells.length) {
            return false;
        }

        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                if (this.cells[y][x] != that.cells[y][x]) {
                    return false;
                }
            }
        }

        return true;
    }

    private void doTheJob() throws InterruptedException {
        done = false;
        running = true;
        startTime = System.currentTimeMillis();


        // Make the reference and temporary arrays
        boolean[][][] arrays = new boolean[2][][];
        arrays[0] = getCells();
        arrays[1] = new boolean[arrays[0].length][arrays[0].length];

        // Bake 'n switch
        for (int round = 0; round < getSteps(); round++) {
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

    private static void swapArrays(boolean[][][] arrays) {
        boolean[][] temp = arrays[0];
        arrays[0] = arrays[1];
        arrays[1] = temp;
    }

    public boolean running() {
        return running;
    }

    public boolean done() {
        return done;
    }

    public String result() {
        return result;
    }

    /**
     * Returns the count of steps for this job.
     *
     * @return Number of steps total.
     */
    public int getSteps() {
        return steps;
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
    
    public boolean setThreads(int count) {
        if (count > 0) {
            threads = count;
            return true;
        }
        return false;
    }
    
    public int threads() {
        return threads;
    }
    
    @Override
    public void run() {
        try {
            doTheJob();
        } catch (InterruptedException ex) {
            Logger.getLogger(LifeJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    long startTime() {
        return startTime;
    }

    long endTime() {
        return endTime;
    }
    
    int step() {
        return step;
    }
}
