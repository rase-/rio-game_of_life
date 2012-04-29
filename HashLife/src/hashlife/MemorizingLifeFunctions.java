/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlife;

/**
 * This class implements the functions for updating a reference two-dimentional
 * boolean array representing a game-of-life board one step.
 *
 * @author scolphoy
 */
public class MemorizingLifeFunctions {

    /**
     * Method for updating the next step from reference array to result array
     *
     * @param reference The starting position for this round
     * @param result The array where the result goes
     * @param current_thread The number of the thread calling this method, the
     * first thread must be 0
     * @param thread_count The total number of threads, must be at least 1
     */
    public static void updateArrays(boolean[][] reference, boolean[][] result, int current_thread, int thread_count) {
        int range_length = reference.length / thread_count;
        int range_start = range_length * current_thread;
        int range_stop = range_length * (current_thread + 1);

        if (current_thread == thread_count - 1) {
            range_stop = reference.length;
        }

        for (int y = range_start; y < range_stop; y++) {
            for (int x = 0; x < reference[y].length; x++) {
                result[y][x] = nextAlive(reference, y, x);
                
                //Update the next neighbor table if state changed
                if (result[y][x] != reference[y][x]) {
                    //NEW CELL IS BORN
                    if (result[y][x]) {
                        MemorizingConcurrentLife.addNeighbor(y, x);
                    }
                    //Cell is dead
                    else {
                        MemorizingConcurrentLife.takeNeighbor(y, x);
                    }
                }
            }

        }
    }

    /**
     * Checks if the given cell from the reference array should be alive on the
     * next round
     *
     * @param reference The two-dimentional reference array
     * @param y The y coordinate
     * @param x The x coordinate
     * @return True if the cell should be alive on the next round, False if not.
     */
    private static boolean nextAlive(boolean[][] reference, int y, int x) {
        System.out.println("Next alive called!");

        if (!reference[y][x]) {
            return (MemorizingConcurrentLife.neighbors[0][y][x].get() == 3);
        } else {
            return (MemorizingConcurrentLife.neighbors[0][y][x].get() == 3 || MemorizingConcurrentLife.neighbors[0][y][x].get() == 2);
        }

//        int liveNeighbours = 0;
//        boolean liveCell = false;
//        if (isAlive(reference, y-1, x-1)) liveNeighbours++;
//        if (isAlive(reference, y-1, x  )) liveNeighbours++;
//        if (isAlive(reference, y-1, x+1)) liveNeighbours++;
//        if (isAlive(reference, y  , x-1)) liveNeighbours++;
//        if (isAlive(reference, y  , x  )) liveCell = true;
//        if (isAlive(reference, y  , x+1)) liveNeighbours++;
//        if (isAlive(reference, y+1, x-1)) liveNeighbours++;
//        if (isAlive(reference, y+1, x  )) liveNeighbours++;
//        if (isAlive(reference, y+1, x+1)) liveNeighbours++;
//        
//        // 1. Any live cell with fewer than two live neighbours dies, as if caused by under-population.
//        if (liveCell && liveNeighbours < 2) return false;
//        
//        // 2. Any live cell with two or three live neighbours lives on to the next generation.
//        if (liveCell && (liveNeighbours == 2 || liveNeighbours == 3)) return true;
//        
//        // 3. Any live cell with more than three live neighbours dies, as if by overcrowding.
//        if (liveCell && liveNeighbours > 3) return false;
//        
//        // 4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
//        if (!liveCell && liveNeighbours == 3) return true;
//
//        // If none of the rules applied, no change in state
//        return liveCell;
    }

    /**
     * Checks if the given cell is alive in the reference array
     *
     * @param reference The two-dimentional reference array
     * @param y The y coordinate
     * @param x The x coordinate
     * @return True if the cell is alive, False if the cell is dead or
     * coordinates are outside the reference array's boundaries.
     */
    private static boolean isAlive(boolean[][] reference, int y, int x) {
        if (y < 0 || y >= reference.length) {
            return false;
        }
        if (x < 0 || x >= reference[y].length) {
            return false;
        }
        return reference[y][x];
    }
}
