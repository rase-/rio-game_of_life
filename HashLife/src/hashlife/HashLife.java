/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlife;

/**
 *
 * @author kviiri
 */
public class HashLife {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    /*public static MacroCell result(MacroCell nw, MacroCell ne, MacroCell se, MacroCell sw) {
        
        
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
        
    }*/
}

interface CellGroup {
    public CellGroup result();
}



