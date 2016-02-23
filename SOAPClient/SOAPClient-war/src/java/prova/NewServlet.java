/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prova;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;


/**
 *
 * @author Matteo
 */
public class NewServlet extends HttpServlet {
   
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/Conpartir-war/SOAPServiceClient.wsdl")
    private SOAPServiceClient_Service service;

    
    
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
            out.println("<title>Servlet NewServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
            out.println("<h3>" + this.getTravels("Roma","Lizzano") + "</h3>");
            out.println("<h3>" + this.getTravels("Lizzano","Roma") + "</h3>");
            out.println("</body>");
            out.println("</html>");
            this.createClient1("gianno", "canio", 'm', 65, "fff", "Denjer", "wdjsenf");
            
            
            
            
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
            throws ServletException, IOException {
        processRequest(request, response);
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

    private void createClient1(java.lang.String name, java.lang.String surname, int gender, int age, java.lang.String email, java.lang.String pass, java.lang.String urlPhoto) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        prova.SOAPServiceClient port = service.getSOAPServiceClientPort();
        
        port.createClient1(name, surname, gender, age, email, pass, urlPhoto);
    }
    /*
    private String getDriver(java.lang.Long id) {
    // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
    // If the calling of port operations may lead to race condition some synchronization is required.
    prova.SOAPServiceClient port = service.getSOAPServiceClientPort();
    return port.getDriver(id);
    }*/
    
    private List<Travel> getTravels(String start, String end) {
        prova.SOAPServiceClient port = service.getSOAPServiceClientPort();
        return port.getTravels(start, end);
        
    };

 
    
 
}
