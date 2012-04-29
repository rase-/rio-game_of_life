/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewOfLife;

import java.io.*;
import java.util.Scanner;

/**
 *
 * @author scolphoy
 */
public class CommandLife {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        // TODO code application logic here
        FileInputStream fis = new FileInputStream(new File(args[0]));
        LifeJob job = new LifeJob(fis);
        job.setThreadCount(Integer.parseInt(args[1]));
        
        System.err.println(job.toString());
        System.err.println("Running with " + job.getThreadCount() + " threads.");
        
        job.start();
        while (!job.done()) {
            Thread.sleep(250);
        }
        
        System.err.println(String.format("Time total: %s (%dms)", naturalTime(job.endTime()-job.startTime()), job.endTime()-job.startTime()));
        System.err.println(String.format("Time computing steps: %s (%dms)", naturalTime(job.pTimeDelta()), job.pTimeDelta()));
        
        PrintWriter out = new PrintWriter(new File("result.txt"));
        out.print(job.result());
        out.close();
        System.err.println("Result written in result.txt");

        

        
        if (args.length > 2) System.err.println("Comparison: " + (match(job, args[2]) ? "Match!" : "No match!"));

    }

    static boolean match(LifeJob job, String referenceFile) throws FileNotFoundException {
        FileInputStream reference = new FileInputStream(new File(referenceFile));
        Scanner y = new Scanner(job.result());
        Scanner x = new Scanner(new InputStreamReader(reference));
        
        if (x.hasNextLine()) {x.nextLine();}    // We don't care how many steps you made! 
        if (y.hasNextLine()) {y.nextLine();}    // Skipping the first line of both results.
        
        while (x.hasNextLine() || y.hasNextLine()) {
            if (!x.hasNextLine()) {
                return false;
            }
            if (!y.hasNextLine()) {
                return false;
            }
            if (x.nextLine().trim().equals(y.nextLine().trim()) == false) {
                return false;
            }
        }

        return true;

    }
    
    static String naturalTime(long time_delta) {
        time_delta = time_delta / 1000; // ms to s
        int hours = (int)(time_delta/3600);
        
        time_delta = time_delta % 3600;
        int minutes = (int) (time_delta/60);
        
        time_delta = time_delta % 60;
        int seconds = (int) (time_delta);
        
        return (hours>0 ? hours+"h ":"") + (minutes>0 ? minutes+"m ":"") + seconds+"s";
    }
}
