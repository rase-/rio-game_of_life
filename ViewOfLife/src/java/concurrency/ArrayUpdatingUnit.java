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
    
    public ArrayUpdatingUnit(boolean[][] reference, boolean[][] result, int threads, int thisThread, Semaphore latch) {
        this.reference = reference;
        this.result = result;
        this.threads = threads;
        this.thisThread = thisThread;
        this.latch = latch;
        s = new Semaphore(0);
    }
    
    @Override
    public void run() {
        running = true;
        try { s.acquire();} catch (InterruptedException ex) {}
        while (running) {
            LifeFunctions.updateArrays(reference, result, thisThread, threads);
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
