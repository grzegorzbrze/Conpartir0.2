/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.conpartir.sessionBean.ClientManagerLocal;
import org.conpartir.entity.Client;
import org.conpartir.entity.Driver;
import org.conpartir.entity.Post;
import org.conpartir.sessionBean.DriverManagerLocal;
import org.conpartir.sessionBean.PostManagerLocal;

/**
 *
 * @author Blu Light
 */
public class TestServlet extends HttpServlet {
    @EJB
    private PostManagerLocal postManager;
    @EJB
    private DriverManagerLocal driverManager;
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
        //clientManager.addClient("Mario", "Rossi", 'M', 27, "mario.rossi@gmail.com", "rossi", "root/rossi");
        //clientManager.addClient("Lorenzo", "Verdi", 'M', 47, "lorenzo.verdi@gmail.com", "verdi", "root/verdi");
        //long i = (long)1;
        //driverManager.addDriver(i);
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestServlet</title>");            
            out.println("</head>");
            out.println("<body>");
                        
            Driver driver1 = new Driver();
            driver1.setCarModel("Audi A6");
            driver1.setCarYear(2014);
                        
            Driver driver2 = new Driver();
            driver2.setCarModel("Fiat 126");
            driver2.setCarYear(1981);
            
            Client client1 = new Client();
            client1.setName("Mario");
            client1.setSurname("Rossi");
            client1.setGender('M');
            client1.setAge(27);
            client1.setEmail("mario.rossi@gmail.com");
            client1.setPass("rossi");
            client1.setUrlPhoto("root/rossi");
                  
            client1.getDrivers().add(driver1);
            client1.getDrivers().add(driver2);
            
            driver1.setClient(client1);
            driver2.setClient(client1);     
            
            clientManager.createClient(client1);
            System.out.println("Esiste email di Mario: "+clientManager.isEmail("mario.rossi@gmail.com"));
            System.out.println("Esiste email di Mario: "+clientManager.isEmail("lorenzo@gggg.it"));
            System.out.println("Email di ID 1 : "+clientManager.getEmail((long)1));
            System.out.println("Email di ID 5: "+clientManager.getEmail((long)5));
            
            System.out.println("Info sul client Mario: "+clientManager.getClient("mario.rossi@gmail.com").toString());
            
            
            Post travel1 = new Post();
            travel1.setClient_id(client1.getId());
            travel1.setData(new Date());
            travel1.setDestination("Milano");
            travel1.setOrigin("Torino");
            travel1.setTime(new Date());
            driver1.getPosts().add(travel1);
            travel1.setDriver(driver1);
            //driverManager.cerateDriver(driver1);
            postManager.createPost(travel1);
            
            Client client2 = new Client();
            client2.setName("Lorenzo");
            client2.setSurname("Verdi");
            client2.setGender('M');
            client2.setAge(47);
            client2.setEmail("lorenzo.verdi@gmail.com");
            client2.setPass("verdi");
            client2.setUrlPhoto("root/verdi");
            
            clientManager.createClient("Lorenzo", "violi", 'M', 23, "lorenzo@gggg.it", "derck", "http", null);
            
            /*  clientManager.createClient(client2);*/
            
            out.println("<h1>Servlet TestServlet at " + request.getContextPath() + "</h1>");
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

}
