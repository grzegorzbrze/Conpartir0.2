/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.conpartir.sessionBean.ClientManagerLocal;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;

/**
 *
 * @author Matteo
 */
public class Registration extends HttpServlet {

    
    @EJB
    private ClientManagerLocal clientManager;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Registration</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Registration at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
     {
         String enteredValue;
         // gets all the selected options from the client browser
         String[] selectedOptions = request.getParameterValues("options");
         // gets the enteredValue fields value
         enteredValue = request.getParameter("enteredValue");
         response.setContentType("text/html");
         PrintWriter printWriter;  
         StringBuffer sb = new StringBuffer();
         
         try 
         {
             BufferedReader reader = request.getReader(); 
             String line = null;
             while ((line = reader.readLine()) != null)
             {
                 sb.append(line);
                         }
         } catch (Exception e) { e.printStackTrace(); }
         
         JSONParser parser = new JSONParser();
         JSONObject joUser = null;
         
         try
         {
             joUser = (JSONObject) parser.parse(sb.toString());
         } catch (ParseException e) { e.printStackTrace(); }/**/
    
         String user = (String) joUser.get("name"); 
    
         PrintWriter out;
        try {
            out = response.getWriter();
            out.write("A new user " + user + " has been created.");
            out.flush();            
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void createClient(String name, String surname, char sex, int age, String email, String pass, String photo) {
        clientManager.createClient(name, surname, sex, age, email, pass, photo);    
    };
    
}
