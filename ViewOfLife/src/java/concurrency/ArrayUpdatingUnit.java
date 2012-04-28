/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

import ViewOfLife.LifeFunctions;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
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
    
    public ArrayUpdatingUnit(boolean[][] reference, boolean[][] result, int threads, int thisThread, Semaphore latch) {
        this.reference = reference;
        this.result = result;
        this.threads = threads;
        this.thisThread = thisThread;
        this.latch = latch;
        s = new Semaphore(0);
        
        double range_length = (double)reference.length / threads;
        range_start = (int)Math.floor((thisThread    * range_length) + 0.5);
        range_stop = (int)Math.floor(((thisThread+1) * range_length) + 0.5);
                
        if (thisThread == threads - 1) {  // Just in case there's a roundign error
            range_stop = reference.length;
        }
        System.out.println("Thread: " + thisThread + "\tRange: " + range_start + "-" + range_stop + "\tRows: " + (range_stop-range_start));

    }
    
    @Override
    public void run() {
        running = true;
        try { s.acquire();} catch (InterruptedException ex) {}
        while (running) {
            LifeFunctions.updateArrays(reference, result, range_start, range_stop);
            swap();
            latch.release();
            try { s.acquire();} catch (InterruptedException ex) {}
        }
    }
    
    public void swap() {
        boolean[][] temp = reference;
        reference = result;
        result = temp;
    }
    
    public void nextStep() {
        try {latch.acquire();} catch (InterruptedException ex) {}         
        s.release();
    }
    
    public void finish() {
        running=false;
        s.release();
    }
    
}
