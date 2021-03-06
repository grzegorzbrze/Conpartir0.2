/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.conpartir.entity.Client;

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
    
    List<Cookie> issuedCookies = new ArrayList();
    
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
    
        
    //Tramite i Get la servlet controlla che il cookie, ricevuto nella request,
    //sia tra i cookie ritenuti validi
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
   /*         throws ServletException, IOException {
        processRequest(request, response);
    }*/ 
    { 
        Boolean flag = false;
        Cookie[] toCheck = request.getCookies();
        String conpCookieValue = null;
        
        int i;
        for (i=0;i<toCheck.length;i++) {
            //cerco il valore del "conpCookie", ovvero il cookie emesso dalla nostra webapp
            if(toCheck[i].getName().equals("conpCookie")) conpCookieValue = toCheck[i].getValue();
        }
        
        //se è scaduto
        if(toCheck[0] != null && toCheck[0].getMaxAge()==0) {  
            try {
                response.sendError(403, "expired Cookie");
            } catch (IOException ex) {
                Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
       
        //controllo nella lista di cookie rilasciati
        for(i=0;i<issuedCookies.size();i++) {            
            String conpCookie = issuedCookies.get(i).getValue();                        
                if (conpCookie != null && conpCookie.equals(conpCookieValue)) flag = true;           
        }
        if(flag==false) {
             try {
                response.sendError(403, "Cookie not found");
            } catch (IOException ex) {
                Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            response.setStatus(200);
        }
        
        
    };

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    // Questa POST della servlet si occupa delle richieste di registrazione e di login. 
    // Gli if/else all'interno distinguono i vari casi
     
     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response) 
     {         
        response.setContentType("text/html");
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
         
        //Inizializzo un parser per leggere il JSON
        JSONParser parser = new JSONParser();
        JSONObject joUser = null;
         
        try
        {
            joUser = (JSONObject) parser.parse(sb.toString());
        } catch (ParseException e) { e.printStackTrace(); }
         
        //inizializzo i parametri         
        String name = null;
        String surname = null;
        int age = 0;
        String gender = null;
        String email = null;
        String password = null;
        String res = null;
         
        //per prima cosa, prendo la variabile "use" che identifica le intenzioni 
        //della richiesta
        String use = (String) joUser.get("use");
         
        if (use.equals("registration")) {
            name = (String) joUser.get("name");      
            surname = (String) joUser.get("surname");             
            age = Integer.parseInt((String) joUser.get("age"));
            gender = (String) joUser.get("gender");
        }         
        email = (String) joUser.get("email");
        password = (String) joUser.get("pass");      
         
        boolean emailCheck = clientManager.isEmail(email);

        if (use.equals("registration")) {
            if (emailCheck == true) {
                //alert: si sta cercando di registrare un utente con una mail già utilizzata
                res = "1 Errore: questa mail è già stata usata per la registrazione di un altro account!";
            } else {
                Client nuovo = new Client();
                nuovo.setName(name);
                nuovo.setSurname(surname);
                nuovo.setGender(gender.charAt(0));
                nuovo.setAge(age);
                nuovo.setEmail(email);
                nuovo.setPass(password);
                //A questo punto ho tutti i dati per creare un nuovo cliente.
                clientManager.createClient(nuovo);
                res = "Registrazione effettuata con successo!";
            }
        } else {
            //Login via gmail
            if (use.equals("gmail")) {
                             
                res = "Login effettuato con successo!";
                String ckValue;
                double val = Math.random() * 5000;
                ckValue = "random" + val;
                Cookie userCookie = new Cookie("conpCookie", ckValue);
                //imposta la validità dei cookie a 10 minuti
                userCookie.setMaxAge(60 * 10);
                issuedCookies.add(userCookie);
                response.addCookie(userCookie);
            }
            //Login via twitte
            if (use.equals("twitter")) {
                              
                res = "Login effettuato con successo!";
                String ckValue;
                double val = Math.random() * 5000;
                ckValue = "random" + val;
                Cookie userCookie = new Cookie("conpCookie", ckValue);
                //imposta la validità dei cookie a 10 minuti
                userCookie.setMaxAge(60 * 10);
                issuedCookies.add(userCookie);
                response.addCookie(userCookie);
             }
            //login via account conpartir
            if (use.equals("login")) {
                if (emailCheck == true) {
                    if (password.equals(clientManager.getClient(email).getPass())) {
                              
                        res = "Login effettuato con successo!";
                        String ckValue;
                        double val = Math.random() * 5000;
                        ckValue = "random" + val;
                        Cookie userCookie = new Cookie("conpCookie", ckValue);
                        //imposta la validità dei cookie a 10 minuti
                        userCookie.setMaxAge(60 * 10);
                        issuedCookies.add(userCookie);
                        response.addCookie(userCookie);
                    } else {
                        res = "3 Errore: password errata";
                    }
                } else { 
                    res = "2 Errore: l'email inserita non è presente nei nostri database.";
                 }
             }
         }
         
         PrintWriter out;
        
         //Servlet Response
         try {
            out = response.getWriter();
            out.write(" " + res);
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
    
    public void createClient(String name, String surname, char sex, int age, String email, String pass) {
        clientManager.createClient(name, surname, sex, age, email, pass);    
    };
    
}
