/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlife;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**A class for reading input files.
 *
 * @author scolphoy
 */
public class ReadFile {
    
    /** Reads an input file and constructs a LifeJob unit.
     * 
     * @param filename  Path to the input file.
     * @return The newly created LifeJob object.
     * @throws IOException Reading of the file failed for some reason.
     */
    public static LifeJob fileToLifeJob(String filename) throws IOException {

        // Creating a handle for the input file and seeing if it's good
        File inputFile = new File(filename);        
        if ((inputFile.exists() && inputFile.isFile() && inputFile.canRead()) == false) {
                throw new IOException("Error reading file " + filename + ". Exists: " + inputFile.exists() + ", Is file: " + inputFile.isFile() + ", Can read: " + inputFile.canRead());
        }
        
        
        // Reading to a buffer for performance
        BufferedReader inputReader = new BufferedReader(new FileReader(inputFile));
        
        
        // Reading the job parameters and constructing a proper array for the board
        int dimension, steps;
        {
                String[] jobSettings = inputReader.readLine().trim().split(" ");
                dimension = Integer.parseInt(jobSettings[0]);
                steps = Integer.parseInt(jobSettings[1]);          
        }
        boolean[][] cells = new boolean[dimension][dimension];

        
        // Reading the initial contents for the game of life board
        for (int y = 0; y < dimension; y++) {
            for (int x = 0; x < dimension; x++) {
                int in = inputReader.read();
                while ((in == '0' || in == '1' || in == -1) == false) {  // repeat until 0, 1 or EOF
                    in = inputReader.read();
                } // Here in is either -1, 0 or 1      
                cells[y][x] = (in == '1' ? true : false); // If EOF is reached before end of the board, assuming all the rest of the cells dead
            }
        }
        
        
        // Constructing the LifeJob object and returning it.
        return new LifeJob(steps, cells);
    }
}
