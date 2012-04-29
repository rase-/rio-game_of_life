/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

import hashlife.LifeFunctions;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author tonykovanen
 */
public class MemorizingArrayUpdatingUnit implements Runnable {
    private boolean[][] reference;
    private boolean[][] result;
    private int threads;
    private int thisThread;
    private CountDownLatch latch;
    
    public MemorizingArrayUpdatingUnit(boolean[][] reference, boolean[][] result, int threads, int thisThread, CountDownLatch latch) {
        this.reference = reference;
        this.result = result;
        this.threads = threads;
        this.thisThread = thisThread;
        this.latch = latch;
    }
    
    @Override
    public void run() {
        LifeFunctions.updateArrays(reference, result, thisThread, threads);
        latch.countDown();
    }
    
}
