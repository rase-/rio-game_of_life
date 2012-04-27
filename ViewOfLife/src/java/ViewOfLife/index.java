/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewOfLife;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author scolphoy
 */
public class index extends HttpServlet {

    private static LifeJob job;
    private static int threads;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, InterruptedException {
        if (job == null) {
            System.out.println("Job was null!");
            response.sendRedirect("./");
            return;
        }
        if (!job.done()) {
            response.setHeader("Refresh", "2");
        }
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();


        out.println(job);
        out.println("Using " + job.threads() + " threads.");


        if (!job.running() && !job.done()) {
            out.println("Starting the job...");
            job.start();
        }

        if (job.running() && !job.done()) {
            out.println("Current step: " + job.step());
            out.println("Job running for " + ((System.currentTimeMillis() - job.startTime())) / 1000 + "s...");
        }


        if (!job.running() && job.done()) {
            out.println("Elapsed time: " + (job.endTime() - job.startTime()) + "ms" + " (" + String.format("%.2f",((job.endTime() - job.startTime()) / 1000.0)) + "s)");
            out.println("Result:");
            out.println(job.result());
        }


    }

  


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (InterruptedException ex) {
            Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (job != null && job.running() && !job.done()) {
            response.sendRedirect("Life");
            return;
        }

        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
                    String fieldname = item.getFieldName();
                    String fieldvalue = item.getString();
                    System.out.println(fieldname + ": " + fieldvalue);

                    if (fieldname.equals("threads")) {
                        try {
                            threads = Integer.parseInt(fieldvalue);
                        } catch (Exception ignored) {
                            threads = 1;
                        }
                    }

                } else {
                    // Process form file field (input type="file").
                    String fieldname = item.getFieldName();
                    InputStream filecontent = item.getInputStream();

                    if (fieldname.equals("inputfile")) {
                        try {
                            System.out.println("Setting a new job");
                            job = new LifeJob(filecontent);
                        } catch (IOException ignored) {
                            System.out.println("Error setting up job");
                            job = null;
                        }
                    }
                }
            }
        } catch (FileUploadException e) {
            response.getWriter().println(e.toString());
            throw new ServletException("Cannot parse multipart request.", e);
        }

        if (job != null) job.setThreads(threads);
        
        try {
            processRequest(request, response);

        } catch (InterruptedException ex) {
            Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
