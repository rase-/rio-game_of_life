/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlife;

import java.io.IOException;

/** Game of life -job unit. Internal representation of a game of life board and its status.
 *
 * @author scolphoy
 */
public class LifeJob {
    private int steps;
    private boolean[][] cells;
    
    /** Luo LifeJob-olion askelmäärän ja taulukkoviittauksen perusteella
     * 
     * @param steps Askelten lukumäärä
     * @param cells Viite pelilautaa esittävään kaksiulotteiseen boolean-taulukkoon
     */
    public LifeJob(int steps, boolean[][] cells) {
        this.steps = steps;
        this.cells = cells;
    }
    
    
    /** Creates the job from a filename string.
     * 
     * @param filepath Path of the input file.
     * @throws IOException An error occurred in reading the input file.
     */
    public LifeJob(String filepath) throws IOException {
        LifeJob temp = ReadFile.fileToLifeJob(filepath);
        this.steps = temp.steps;
        this.cells = temp.cells;
    }
   
    
    
    /** Returns the count of steps for this job.
     * 
     * @return Number of steps total.
     */
    public int getSteps() {
        return steps;
    }
    
    
    
    
    /** Returns a pointer to the game of life -board.
     * 
     * @return Game of Life -board with coordinates [y][x], cell with the value True represents a live cell, a cell with the value False represents a dead cell.
     */
    public boolean[][] getCells() {
        return cells;
    }
    
    
    
    
    /** Returns a descriptive String about this job unit.
     * 
     * @return A String with information about the board measures and the total step count.
     */
    @Override
    public String toString() {
        return "Game of life, " + cells.length + "x" + cells.length + ", " + steps + " steps";
    }
    
    
    
    
    /** Compares two job units with each other
     * 
     * @param that The job unit to compare to
     * @return true if the units are similar, false if not. The comparison checks the total step count, board measures and content.
     */
    public boolean equals(LifeJob that) {
        if (this.steps != that.steps) return false;
        if (this.cells.length != that.cells.length) return false;
        
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                if (this.cells[y][x] != that.cells[y][x]) return false;
            }
        }
        
        return true;
    }
}
