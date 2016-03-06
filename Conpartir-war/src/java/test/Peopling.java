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
import java.util.LinkedList;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.conpartir.entity.Driver;
import org.conpartir.entity.Travel;
import org.conpartir.sessionBean.ClientManagerLocal;
import org.conpartir.sessionBean.CommentMagangerLocal;
import org.conpartir.sessionBean.DriverManagerLocal;
import org.conpartir.sessionBean.TravelManagerLocal;

/**
 *
 * @author Blu Light
 */
public class Peopling extends HttpServlet {
    @EJB
    private CommentMagangerLocal commentManager;
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Peopling</title>");            
            out.println("</head>");
            out.println("<body>");
            creaClient();
            //creaDriver();
            out.println("<h1>Servlet Peopling è andata a buon fine " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private int dimensione = 14;
    
    
    
    
    public void creaClient(){
        String[] nomi = {"Andrea", "Luca", "Marco", "Francesco", "Matteo", "Alessandro", "Davide",
                "Giulia", "Chiara", "Francesca", "Sara", "Martina", "Valentina", "Marta"};
        String[] cognomi = {"Rossi", "Russo", "Ferrari", "Neri", "Bianchi", "Rosso", "Esposito", 
            "Bianchi", "Romano", "Colombo", "Ricci", "Greco", "Conti", "Lombardi"};
        char[] genders = {'M', 'M', 'M', 'M', 'M', 'M', 'M', 'F', 'F', 'F', 'F', 'F', 'F', 'F'};
        String[] email = new String[dimensione];
        int[] eta = new int[dimensione];
        String[] paths = new String[dimensione];
        
        int minimum = 18;
        int maximum = 45;
        
        if (nomi.length == cognomi.length && nomi.length == email.length){
            for (int i=0; i<email.length; i++){
                    email[i] = nomi[i]+"."+cognomi[i]+"@gmail.com";
            }
        }
        
        if (nomi.length == eta.length){    
            for(int i=0; i<eta.length; i++){
                    eta[i] = minimum + (int)(Math.random() * maximum); 
            }
        }
        
        if (paths.length == cognomi.length){
            for (int i=0; i<paths.length; i++){
                    paths[i] = "root/"+cognomi[i];   
            }
        }
        
        /*System.out.println("Ecco tutti i dati:");
        for(int i=0; i<nomi.length; i++){
            System.out.println("Nome: "+nomi[i]+" Cognome: "+cognomi[i]+" Sesso: "
                    +genders[i]+" Età: "+eta[i]+" Email: "+email[i]+" Password: "
                    +cognomi[i]+" PathFoto: "+paths[i]);
        }*/
        
        for(int i=0; i<nomi.length; i++){
           
            clientManager.createClient(nomi[i], cognomi[i], genders[i], 
                    eta[i], email[i], cognomi[i], paths[i]);
        }
        
        creaDriver();
    }
   
    public void creaDriver(){
        String[] carModel = {"Volkswagen Golf", "Audi A6", "Renault Clio",
            "Ford Fiesta", "Opel Corsa", "Nissan Qashqai", "Ford Focus", 
            "Volkswagen Passat", "Skoda Octavia", "Peugeot 208"};
        int[] carYear = new int[10];
        int[] client_id = new int[10];
        
        int minimum = 1991;
        int maximum = 25;
        if(carModel.length == carYear.length){
            for(int i=0; i<carYear.length; i++){
                    carYear[i] = minimum + (int)(Math.random() * maximum); 
            }
        }
        
        int min = 1;
        int max = 13;
        if(carModel.length == client_id.length){
            for(int i=0; i<client_id.length; i++){
                    client_id[i] = min + (int)(Math.random() * max); 
            }
        }
        
        /*System.out.println("Ecco tutti i dati:");
        for(int i=0; i<carModel.length; i++){
            System.out.println("Nome macchina: "+carModel[i]+" Anno macchina: "
                    +carYear[i]+" id_client: "+client_id[i]);
        }*/
        
        for(int i=0; i<carModel.length; i++){
            driverManager.createDriver(carModel[i], carYear[i], (long)client_id[i]);
        }
        
        creaTravels();
    }
    
    public void creaTravels(){
        // parametri: citta; partenza = Torino; numeroPosto; id_client; id_driver; data; ora;
        String[] citta = {"Roma", "Milano", "Roma", "Milano", "Novara", 
            "Novara", "Milano", "Roma", "Cuneo", "Cuneo"};
        String partenza = "Torino";
        
        int []posti = new int[10];
        for (int i=0; i<posti.length; i++){
            int numPosti = 2 +(int)(Math.random() * 1);//il numero max di posti è 4 + 1 autista
            posti[i] = numPosti;
        }
        
        Long[] clienti = new Long[10];
        Long[] drivers = new Long[10];
        for(int i=0; i<drivers.length; i++){
            int id_driver = 1 +(int)(Math.random() * 9);
            Driver driver = driverManager.getDriver((long)id_driver);
            drivers[i] = driver.getDriver_id();
            clienti[i] = driver.getClient_id();
        }
        
        Date[] leDate = new Date[10];
        for (int i=0; i<leDate.length; i++){
            Calendar cal = Calendar.getInstance();
            int mese = 2 + (int)(Math.random() * 2);
            int giorno = 2 + (int)(Math.random() * 27);
            int ora = 5 + (int)(Math.random() * 18);
            int minuti = 1 + (int)(Math.random() * 59);
            int anno = 2016;
            //Nota i mesi vanno del 0-gennaio al 11-dicembre 
            //il set è in formato (anno, mese, giorno, ora, minuti, secondi)
            cal.set(anno, mese, giorno, ora, minuti, 00);
            leDate[i] = cal.getTime();
        }
        
        LinkedList<Long> clientiRimasti = new LinkedList();
        for(int i=1; i<15; i++){
            boolean esiste = false;
            for (Long clienti1 : clienti) {
                if (clienti1.equals((long)i)) {
                    esiste = true;
                }
            }
            if (!esiste){
                clientiRimasti.add((long)i);
            }                
        }
        
        System.out.println("Clienti rimasti:");
        for (Long temp : clientiRimasti){
            System.out.println(temp);
        }
        
        System.out.println("Clienti associati: ");
        for (Long clienti1 : clienti) {
            System.out.println(clienti1);
        }
        
        Long[] id_travels = new Long[leDate.length];
       //public Long getTravel_ID(Long driver_id, Long client_id, Date data, Date time,String origine, String destination);
        for (int i=0; i<leDate.length; i++){
           travelManager.createTravel(drivers[i], clienti[i], partenza, citta[i], leDate[i], leDate[i], posti[i]);
           id_travels[i] = travelManager.getTravel_ID(drivers[i], clienti[i], leDate[i], leDate[i], partenza, citta[i]);
        }
        
        int intMin = 0;
        int intMax = clientiRimasti.size()-1;
        int travMin = 0;
        int travMax = id_travels.length-1;
        for (int i=0; i<15; i++){ //qui si creano le prnotazioni
            int id_rimasti = intMin + (int)(Math.random() * intMax);
            int id_trav = travMin + (int)(Math.random() * travMax);
            long client_scelto = clientiRimasti.get(id_rimasti);
            long trav_scelto = id_travels[id_trav];
            boolean aggiunto = travelManager.addPassenger(trav_scelto, client_scelto);
            if (aggiunto){
                creaComment(trav_scelto, client_scelto);
            }
        }
        
        /*System.out.println("I dati sui viaggi");
        for (int i=0; i<posti.length; i++){
            System.out.println("ID_Client: "+clienti[i]+" ID_Driver: "+drivers[i]
                    +" Origine: "+partenza+" Destinazione: "+citta[i]+" Numero Posti: "
                    +posti[i]+" Data e Ora: "+leDate[i]);
        }*/
        /*
        System.out.println("Clienti liberi: ");
        for (Long tempClient : clientiRimasti){
            System.out.println("ID_clienti: "+tempClient);
        }*/
        
        
    }
    
    public void creaComment(Long id_travel, Long author){
        String [] testi = {"é stato un viaggio bellissimo, grazie", 
            "autista era molto professionale", 
            "macchina pulita e autista molto bravo", 
            "rispettato il tempo previsto, molto cordiale e simpatico",
            "guidava meglio di me!", 
            "autista parlava troppo", 
            "ci siamo persi, autista non conosceva la strada",
            "il prezzo era troppo alto per la tratta", 
            "non ci andrò mai più in quella macchina",
            "anche mia nonna sapeva guidare meglio di lui"};
        
        int[] feedbacks = {5, 4, 5, 5, 5, 3, 2, 2, 1, 1};
        Travel viaggio = travelManager.getTravel(id_travel);
        Long autista = viaggio.getDriver_id();
        Date dataCommento = viaggio.getData();
        Date oraCommento = viaggio.getTime();
        int indice = (int)(Math.random()*9);
        
        //public void createComment(Long id_author, Long id_clientJudged, Long id_travel, 
        //    String comment, int feedback, Date comment_date, Date commet_hour);
        commentManager.createComment(author, autista, id_travel, testi[indice], 
                feedbacks[indice], dataCommento, oraCommento);
        
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
