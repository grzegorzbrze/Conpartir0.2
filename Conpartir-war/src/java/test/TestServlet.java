/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.conpartir.sessionBean.ClientManagerLocal;
import org.conpartir.entity.Client;
import org.conpartir.entity.Driver;
import org.conpartir.entity.Travel;
import org.conpartir.sessionBean.DriverManagerLocal;
import org.conpartir.sessionBean.TravelManagerLocal;

/**
 *
 * @author Blu Light
 */
public class TestServlet extends HttpServlet {
    @EJB
    private TravelManagerLocal travelManager;
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
    
    
    private void test(){
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
  
            Travel travel1 = new Travel();
            travel1.setClient_id(client1.getId());
            
            travel1.setDestination("Milano");
            travel1.setOrigin("Torino");
            Calendar cal = Calendar.getInstance();
            cal.set(2016, 3, 12, 17, 15, 25);
            Date d = cal.getTime();
            travel1.setData(d);
            travel1.setFreeSeats(4);
            travel1.setTime(d);
            driver1.getTravels().add(travel1);
            travel1.setDriver(driver1);
            //driverManager.cerateDriver(driver1);
            travelManager.createTravel(travel1);
            travelManager.subFreeSeat(driver1.getDriver_id(), client1.getId());
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
            testClient();
            testDriver();
            testTravel();
    }
    
    private void testClient(){
        System.out.println("Test classe Client");
        System.out.println("Esiste email mario.rossi@gmail.com: "+clientManager.isEmail("mario.rossi@gmail.com"));
        System.out.println("Info sul client che ha email mario.rossi@gmail.com: "+clientManager.getClient("mario.rossi@gmail.com").toString());
        System.out.println("Esiste lorenzo@gmail.com: "+clientManager.isEmail("lorenzo@gmail.com"));
        System.out.println("Info sul client che ha email lorenzo@gmail.com: "+clientManager.getClient("lorenzo@gmail.com").toString());
        System.out.println("Restituisci l'email del client con ID 1 : "+clientManager.getEmail((long)1));
        System.out.println("Restituisci l'email del client con ID 5: "+clientManager.getEmail((long)5));
    }
    
    private void testDriver(){
        System.out.println("Test classe Driver");
        System.out.println("Info sul driver con ID 1: "+driverManager.getDriver((long)1).toString());
        System.out.println("Esiste il dirver con ID 5: "+driverManager.isDriver((long)5));
        System.out.println("Esiste il dirver con ID 1: " +driverManager.isDriver((long)1));
    }
    
    private void testTravel(){
        int numero1 = travelManager.searchByOriginDestination("Milano", "Torino").size();
        int numero2 = travelManager.searchByOriginDestination("Torino", "Milano").size();
        System.out.println("Test classe Travel");
        System.out.println("Il numero di viaggi da Milano a Torino: "+numero1);
        System.out.println("Il numero di viaggi da Torino a Milano: "+numero2);
        
        Date data = new Date();
        Date time = new Date();
        System.out.println("Lista di viaggi da Torino per Milano a partire da oggi: ");
        for (Travel temp : travelManager.searchByOriginDestinationDate(data, "Torino", "Milano")){
            System.out.println(temp.toString());
        }
        
        System.out.println("Lista di viaggi da Torino per Milano a partire da oggi a quest'ora");
        for (Travel temp : travelManager.searchByOriginDestinationDateTime(data, time, "Torino", "Milano")){
            System.out.println(temp.toString());
        }
    }
    
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
            
            test();
                        
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
