/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlife;

/** Työyksikkö, viite game of life-lautaan ja tieto askelten lukumäärästä.
 *
 * @author scolphoy
 */
public class LifeJob {
    private int steps;
    private boolean[][] cells;
    
    public LifeJob(int steps, boolean[][] cells) {
        this.steps = steps;
        this.cells = cells;
    }
    
    /** Palauttaa tehtävien askelten lukumäärän
     * 
     * @return Askelten lukumäärä 
     */
    public int getSteps() {
        return steps;
    }
    
    /** Palauttaa viitteen game of life-lautaan
     * 
     * @return Game of Life -taulukko koordinaattien järjestyksellä [y][x], solu jossa true viittaa elossa olevaan soluun.
     */
    public boolean[][] getCells() {
        return cells;
    }
    
    /** Palauttaa merkkijonoesityksen työyksikön tiedoista
     * 
     * @return Merkkojono, jossa tieto game of life-laudan mitoista ja tehtävistä askelista.
     */
    @Override
    public String toString() {
        return "Game of life, " + cells.length + "x" + cells.length + ", " + steps + " steps";
    }
    
    /** Vertaa kahta työyksikköä toisiinsa
     * 
     * @param that Verrattava työyksikkö
     * @return true jos samanlaiset, false jos erilaiset. Vertailu ulottuu game of life-laudan sisältöön.
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
