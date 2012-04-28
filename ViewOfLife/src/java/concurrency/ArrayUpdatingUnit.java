/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

import ViewOfLife.LifeFunctions;
import java.util.concurrent.Semaphore;

/**
 * This class defines the computing threads.
 * @author tonykovanen
 */
public class ArrayUpdatingUnit extends Thread implements Runnable {

    private boolean[][] reference;
    private boolean[][] result;
    private int threads;
    private int thisThread;
    private Semaphore s;
    private Semaphore latch;
    private boolean running;
    private int range_start;
    private int range_stop;

    /**
     * Constructs a computing thread. Sets facilities for controlled advancement of this thread and calculates the range of rows in the array evenly.
     * @param reference The array representing the current state of the game of
     * life board
     * @param result The array where the state after the next step is recorded
     * @param threads The total number of threads to be used
     * @param thisThread The number of this thread, counting form 0
     * @param latch Handle to the latch (implemented as a semaphore) controlling
     * advancement of the whole process
     */
    public ArrayUpdatingUnit(boolean[][] reference, boolean[][] result, int threads, int thisThread, Semaphore latch) {
        this.reference = reference;
        this.result = result;
        this.threads = threads;
        this.thisThread = thisThread;
        this.latch = latch;
        s = new Semaphore(0);

        double range_length = (double) reference.length / threads;
        range_start = (int) Math.floor((thisThread * range_length) + 0.5);
        range_stop = (int) Math.floor(((thisThread + 1) * range_length) + 0.5);

        if (thisThread == threads - 1) {  // Just in case there's a rounding error
            range_stop = reference.length;
        }

    }

    @Override
    /**
     * The main run loop. For each step, waits for permission to take the next
     * step or call to shut down. Informs the main thread when the step is done.
     */
    public void run() {
        running = true;
        try {s.acquire();} catch (InterruptedException ex) {}  // Wait for permission before the first step
        while (running) {
            LifeFunctions.updateArrays(reference, result, range_start, range_stop);
            swap();
            latch.release();
            try {s.acquire();} catch (InterruptedException ex) {}
        }
    }

    /**
     * Used to swap the reference array and the result array.
     */
    private void swap() {
        boolean[][] temp = reference;
        reference = result;
        result = temp;
    }

    /**
     * Advance the computation for this thread's range one step.
     */
    public void nextStep() {
        try {
            latch.acquire();
        } catch (InterruptedException ex) {
        }
        s.release();
    }

    /**
     * Finish the execution of this thread.
     */
    public void finish() {
        running = false;
        s.release();
    }

    
    @Override
    /**
     * Returns a String representation characterizing this thread.
     */
    public String toString() {
        return ("Thread: " + thisThread + "\tRange: " + range_start + "-" + (range_stop - 1) + "\tRows: " + (range_stop - range_start));

    }
}
