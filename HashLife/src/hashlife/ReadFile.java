/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlife;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**Luokka syötetiedoston lukemiseen, käytetään LifeJob-olion muodostamiseen
 *
 * @author scolphoy
 */
public class ReadFile {
    
    /** Lukee syötetiedoston ja muodostaa työyksikön
     * 
     * @param filename  Syötetiedoston polku
     * @return Työyksikkö, jossa tieto askelten lukumäärästä ja alkuasetelmasta
     * @throws IOException Tiedoston lukeminen epäonnistuu, tiedostoa ei ole olemassa tai se on hakemisto
     */
    public static LifeJob fileToBooleanArray(String filename) throws IOException {

        // Luodaan syötetiedostolle kahva ja varmistetaan sen lukukelpoisuus
        File inputFile = new File(filename);        
        if ((inputFile.exists() && inputFile.isFile() && inputFile.canRead()) == false) {
                throw new IOException("Error reading file " + filename + ". Exists: " + inputFile.exists() + ", Is file: " + inputFile.isFile() + ", Can read: " + inputFile.canRead());
        }
        
        
        // Käytetään puskuroivaa lukijaa viiveiden välttämiseksi
        BufferedReader inputReader = new BufferedReader(new FileReader(inputFile));
        
        
        // Luetaan syötetiedostosta työn parametrit ja luodaan asianmukainen taulukko
        int dimension, steps;
        {
                String[] jobSettings = inputReader.readLine().trim().split(" ");
                dimension = Integer.parseInt(jobSettings[0]);
                steps = Integer.parseInt(jobSettings[1]);          
        }
        boolean[][] cells = new boolean[dimension][dimension];

        
        // Luetaan game of life-lauta boolean-taulukkoon
        for (int y = 0; y < dimension; y++) {
            for (int x = 0; x < dimension; x++) {
                int in = inputReader.read();
                while ((in == '0' || in == '1' || in == -1) == false) {  // toista kunnes 0, 1 tai tiedoston loppu
                    in = inputReader.read();
                } // Täällä in on joko 0, 1 tai -1               
                cells[y][x] = (in == '1' ? true : false); // Jos tiedosto päättyy ennen viimeistä arvoa, loput oletetaan falseksi
            }
        }
        
        
        // Luodaan kerättyjen tietojen perusteella työyksikkö ja palautetaan se
        return new LifeJob(steps, cells);
    }
}
