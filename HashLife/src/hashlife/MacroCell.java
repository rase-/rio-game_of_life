/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlife;

import java.util.HashMap;

/**
 *
 * @author kviiri
 */
public class MacroCell {

    public static HashMap<MacroCell, MacroCell> hashManager;
    public static final byte MAGIC_OUTSIDE = -1;
    public static final byte MAGIC_ALIVE = 1;
    public static final byte MAGIC_DEAD = 0;
    public byte[][] cells;
    private MacroCell result;
    MacroCell nw, ne, sw, se;
    //The quadrants

    public MacroCell(MacroCell nw2, MacroCell ne2, MacroCell se2, MacroCell sw2) {
		
        cells = new byte[nw2.cells.length * 2][nw2.cells.length * 2];
        
		// tässä lasketaan mitkä solut kuuluvat mihinkin alueeseen
		for (int x = 0; x < cells.length; x++) {
			
            for (int y = 0; y < cells.length; y++) {
				
                if (x < cells.length / 2) {
                    if (y < cells.length / 2) {
                        cells[x][y] = nw2.cells[x][y];
                    } else {
                        cells[x][y] = sw2.cells[x][y - sw2.cells.length];
                    }
                } else {
                    if (y < cells.length / 2) {
                        cells[x][y] = sw2.cells[x - ne2.cells.length / 2][y];
                    } else {
                        cells[x][y] = se2.cells[x - se2.cells.length / 2][y - se2.cells.length / 2];
                    }
                }
            }
        }
		
        nw = nw2;
        ne = ne2;
        se = se2;
        sw = sw2;
    }

    public MacroCell(byte[][] cells) {

        result = null;

		// jos luotuja soluja on vähemmän kuin neljä, ei tiedetä vielä lopputulosta
        if (cells.length > 4) {
            byte[][] nwCells = new byte[cells.length / 2][cells.length / 2];
            byte[][] neCells = new byte[cells.length / 2][cells.length / 2];
            byte[][] seCells = new byte[cells.length / 2][cells.length / 2];
            byte[][] swCells = new byte[cells.length / 2][cells.length / 2];

            for (int x = 0; x < cells.length; x++) {
                for (int y = 0; y < cells.length; y++) {
                    nwCells[x][y] = cells[x][y];
                    neCells[x][y] = cells[x + cells.length / 2][y];
                    seCells[x][y] = cells[x + cells.length / 2][y + cells.length / 2];
                    swCells[x][y] = cells[x][y + cells.length / 2];
                }
            }

			// jos solu löytyy hash-taulukosta, ei sitä tarvitse laskea uudelleen
			// muussa tapauksessa luodaan uusi solu joka laskee tuloksensa			
			this.setCell(nw, nwCells);
			this.setCell(ne, neCells);
			this.setCell(se, seCells);
			this.setCell(sw, swCells);
			
        }


    }
	
	private void setCell(MacroCell cell, byte[][] cells) {
		 if (hashManager.containsKey(cell)) {
                cell = hashManager.get(cell);
            } else {
                cell = new MacroCell(cells);
            }
	}

    public MacroCell result() {
        //Laskettu jo? Voidaan palauttaa suoraan ilman laskemista! (jee)
        if (result != null) {
            return result;
        }

		// lasketaan tulos jos soluja löytyy neljä
        if (cells.length == 4) {
            byte[][] resultArray = new byte[2][2];
            for (int x = 1; x <= 2; x++) {
                for (int y = 1; y <= 2; y++) {
                    if (cells[x][y] == MAGIC_OUTSIDE) {
                        resultArray[x][y] = MAGIC_OUTSIDE;
                        continue;
                    }
                    byte aliveNeighbors = 0;
                    aliveNeighbors += cells[x - 1][y - 1] == 1 ? 1 : 0;
                    aliveNeighbors += cells[x][y-1] == 1 ? 1 : 0;
                    aliveNeighbors += cells[x+1][y-1] == 1 ? 1 : 0;
                    aliveNeighbors += cells[x-1][y] == 1 ? 1 : 0;
                    aliveNeighbors += cells[x+1][y] == 1 ? 1 : 0;
                    aliveNeighbors += cells[x-1][y+1] == 1 ? 1 : 0;
                    aliveNeighbors += cells[x][y+1] == 1 ? 1 : 0;
                    aliveNeighbors += cells[x+1][y+1] == 1 ? 1 : 0;
                    if(aliveNeighbors == 3 || (cells[x][y] == MAGIC_ALIVE && (aliveNeighbors == 2 || aliveNeighbors == 3))) {
                        resultArray[x][y] = MAGIC_ALIVE;
                    }
                    else {
                        resultArray[x][y] = MAGIC_DEAD;
                    }
                }
            }
            result = new MacroCell(resultArray);
        }
        result = result(nw, ne, se, sw);
        return result;
    }

    public static MacroCell result(MacroCell nw, MacroCell ne, MacroCell se, MacroCell sw) {

        MacroCell nw1 = result(nw.nw, nw.ne, nw.se, nw.sw);
        MacroCell ne1 = result(ne.nw, ne.ne, ne.se, ne.sw);
        MacroCell se1 = result(se.nw, se.ne, se.se, se.sw);
        MacroCell sw1 = result(sw.nw, sw.ne, sw.se, sw.sw);
        MacroCell north1 = result(nw.ne, ne.nw, ne.sw, nw.se);
        MacroCell east1 = result(ne.sw, ne.se, se.ne, se.nw);
        MacroCell south1 = result(sw.ne, se.nw, se.sw, sw.se);
        MacroCell west1 = result(nw.sw, nw.se, sw.ne, sw.nw);

        MacroCell center1 = result(nw.se, ne.sw, se.nw, sw.ne);

        MacroCell nw2 = result(nw1, north1, center1, west1);
        MacroCell ne2 = result(north1, ne1, east1, center1);
        MacroCell se2 = result(center1, east1, se1, south1);
        MacroCell sw2 = result(west1, center1, south1, sw1);

        return new MacroCell(nw2, ne2, se2, sw2);

    }
}
