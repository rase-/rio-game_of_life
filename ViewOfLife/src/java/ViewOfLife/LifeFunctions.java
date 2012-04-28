/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewOfLife;

/**
 * This class implements the functions for updating a reference two-dimentional
 * boolean array representing a game-of-life board one step.
 *
 * @author scolphoy
 */
public class LifeFunctions {

    /**
     * Method for updating the next step from reference array to result array
     *
     * @param reference The starting position for this round
     * @param result The array where the result goes
     * @param range_start The index of the first row to check, starting from 0
     * @param range_stop The index of the first row not to check, eg. array length.
     */
    public static void updateArrays(boolean[][] reference, boolean[][] result, int range_start, int range_stop) {

        for (int y = range_start; y < range_stop; y++) {
            for (int x = 0; x < reference[y].length; x++) {
                result[y][x] = nextAlive(reference, y, x);
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
        int liveNeighbours = 0;
        boolean liveCell = false;
        if (isAlive(reference, y, x)) {liveCell = true;}

        if (isAlive(reference, y - 1, x - 1)) {liveNeighbours++;}
        if (isAlive(reference, y - 1, x)) {liveNeighbours++;}
        if (isAlive(reference, y - 1, x + 1)) {liveNeighbours++;}        
        if (isAlive(reference, y, x - 1)) {liveNeighbours++;}        
        if (isAlive(reference, y, x + 1)) {liveNeighbours++;}        
        if (isAlive(reference, y + 1, x - 1)) {liveNeighbours++;}        
        if (isAlive(reference, y + 1, x)) {liveNeighbours++;}        
        if (isAlive(reference, y + 1, x + 1)) {liveNeighbours++;}

        // 1. Any live cell with fewer than two live neighbours dies, as if caused by under-population.
        // 2. Any live cell with two or three live neighbours lives on to the next generation.
        // 3. Any live cell with more than three live neighbours dies, as if by overcrowding.
        if (liveCell) {
            if (liveNeighbours == 2 || liveNeighbours == 3) {
                return true;
            }
            return false;
        }

        // 4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
        return liveNeighbours == 3;

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
