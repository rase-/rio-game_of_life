/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlife;

/**
 *
 * @author scolphoy
 */
public class LifeFunctions {
    
    public static void updateArrays(boolean[][] reference, boolean[][] result, int current_thread, int thread_count) {
        int range_length = reference.length / thread_count;
        int range_start = range_length * current_thread;
        int range_stop = Math.min(range_length * (current_thread + 1), reference.length);
        
        for (int y = range_start; y < range_stop; y++) {
            for (int x = 0; x < reference[y].length; x++) {
                result[y][x] = nextAlive(reference, y, x);
            }
        }
    }

    private static boolean nextAlive(boolean[][] reference, int y, int x) {
        int liveNeighbours = 0;
        boolean liveCell = false;
        if (isAlive(reference, y-1, x-1)) liveNeighbours++;
        if (isAlive(reference, y-1, x  )) liveNeighbours++;
        if (isAlive(reference, y-1, x+1)) liveNeighbours++;
        if (isAlive(reference, y  , x-1)) liveNeighbours++;
        if (isAlive(reference, y  , x  )) liveCell = true;
        if (isAlive(reference, y  , x+1)) liveNeighbours++;
        if (isAlive(reference, y+1, x-1)) liveNeighbours++;
        if (isAlive(reference, y+1, x  )) liveNeighbours++;
        if (isAlive(reference, y+1, x+1)) liveNeighbours++;
        
        // 1. Any live cell with fewer than two live neighbours dies, as if caused by under-population.
        if (liveCell && liveNeighbours < 2) return false;
        
        // 2. Any live cell with two or three live neighbours lives on to the next generation.
        if (liveCell && (liveNeighbours == 2 || liveNeighbours == 3)) return true;
        
        // 3. Any live cell with more than three live neighbours dies, as if by overcrowding.
        if (liveCell && liveNeighbours > 3) return false;
        
        // 4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
        if (!liveCell && liveNeighbours == 3) return true;

        // If none of the rules applied, no change in state
        return liveCell;
    }

    private static boolean isAlive(boolean[][] reference, int y, int x) {
        if (y < 0 || y >= reference.length) return false;
        if (x < 0 || x >= reference[y].length) return false;
        return reference[y][x];
    }
    
}
