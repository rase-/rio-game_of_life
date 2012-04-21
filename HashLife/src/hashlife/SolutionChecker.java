/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashlife;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author kviiri
 */
public class SolutionChecker {

    File proposedSolution;
    File expectedSolution;

    public SolutionChecker(File proposedSolution, File expectedSolution) {
        this.proposedSolution = proposedSolution;
        this.expectedSolution = expectedSolution;
    }

    public boolean checkSolution() throws FileNotFoundException {
        Scanner proposed = new Scanner(proposedSolution);
        Scanner expected = new Scanner(expectedSolution);

        while (proposed.hasNextLine()) {
            if (expected.hasNextLine()) {
                if(!expected.nextLine().equals(proposed.nextLine())) {
                    return false;
                }
            }
            else return false;
        }
        if(expected.hasNextLine()) return false;
        return true;
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        SolutionChecker sol = new SolutionChecker(new File(args[0]), new File(args[1]));
        if(sol.checkSolution()) {
            System.out.println("Epic, läpi meni :---D");
        }
        else {
            System.out.println("Trololo, ei mennyt läpi.");
        }
    }
}
