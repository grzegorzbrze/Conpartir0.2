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
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.conpartir.entity.Comment;
import org.conpartir.entity.Driver;
import org.conpartir.entity.Travel;
import org.conpartir.entity.Client;
import org.conpartir.entity.Taxi;
import org.conpartir.sessionBean.ClientManagerLocal;
import org.conpartir.sessionBean.CommentMagangerLocal;
import org.conpartir.sessionBean.DriverManagerLocal;
import org.conpartir.sessionBean.TaxiManagerLocal;
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
    @EJB 
    private TaxiManagerLocal taxiManager;
    
    
    
    
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
            //creaTravels(this.carModel.length*5);
            out.println("<h1>Servlet Peopling è andata a buon fine " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    
    private String[] nomi = {"Adamo", "Achille", "Mario", "Adriano", "Aiace", "Alberto", 
        "Aldo", "Alessandro", "Alessio", "Alfio", "Alfonso", "Ambrogio", "Luca", 
        "Amedeo", "Andrea", "Angelo", "Bartolomeo", "Caio", "Carlo", "Cesare", "Claudio", 
        "Cristoforo", "Damiano", "Daniele", "Dario", "Davide", "Diego", "Domenico", 
        "Edoardo", "Elia", "Emanuele", "Enrico", "Enzo", "Ernesto", "Ettore", "Eugenio", 
        "Fabio", "Fabrizio", "Fausto", "Federico", "Nicola", "Filippo", "Flavio", 
        "Francesco", "Furio", "Gabriele", "Giacomo", "Giancarlo", "Gianluca", "Gianmarco", 
        "Gianni", "Giorgio", "Lorenzo", "Samuele", "Giovanni", "Giudo", "Giulio", 
        "Giuseppe", "Ada", "Adele", "Adriana", "Agata", "Agnese", "Alessandra", "Alessia", 
        "Alice", "Amanda", "Amelia", "Anastasia", "Angelica", "Anna", "Azzurra", 
        "Barbara", "Beatrice", "Benedetta", "Bianca", "Camilla", "Laura", "Carla", 
        "Carlotta", "Carolina", "Cassandra", "Caterina", "Cecilia", "Chiara", "Claudia", 
        "Cloe", "Costanza", "Cristina", "Daniela", "Debora", "Donatella", "Irene", 
        "Elena", "Eleonora", "Elisabetta", "Emanuela", "Emma", "Eva", "Fabiola", 
        "Federica", "Filippa", "Flavia", "Francesca", "Gabriella", "Gaia", "Gemma", 
        "Giada", "Gioia", "Giulia", "Gloria", "Monica", "Grazia", "Martina"};
    
    private String[] cognomi = {"Abate", "Abatantuono", "Rossi", "Abbiento", "Abbondi", 
        "Abbondio", "Abbruscato", "Abelli", "Abrami", "Abruscato", "Acardi", "Acciai", 
        "Accorso", "Accursio", "Acerbi", "Babare", "Babuscio", "Baccari", "Baccelieri", 
        "Bacci", "Baceliere", "Badalati", "Badalucco", "Badanai", "Badessi", "Badia", 
        "Badiale", "Baffo", "Bagaglia", "Bagato", "Bagiacci", "Bagini", "Cabassi", 
        "Cabello", "Cabizzosu", "Caboni", "Cabrini", "Caccia", "Cacciapuoti", "Cacciari", 
        "Cacciatore", "Caccini", "Cadau", "Cadeo", "Cadoni", "Cadore", "Caffari", 
        "Roncati", "Ronchi", "Roncone", "Rondana", "Rondini", "Ropolo", "Rosanova", 
        "Rosari", "Rosato", "Roscigno", "Roscio", "Rosellini", "Rosi", "Rosselli", 
        "Rosset", "Oneto", "Onofrio", "Oppini", "Oppio", "Oppizio", "Orco", "Orio", 
        "Orlandi", "Orsatti", "Orsini", "Orso", "Osio", "Ossia", "Nespola", "Niccodemo", 
        "Niccolai", "Nicefaro", "Nicheli", "Nicoletti", "Nicosia", "Nigrelli", "Nistri", 
        "Nobile", "Noce", "Nocentini", "Leonardini", "Leonelli", "Leopardi", "Leopizzi", 
        "Leotta", "Lercari", "Leto", "Letta", "Lettieri", "Leva", "Levante", "Libero", 
        "Gianotti", "Giaquinta", "Giarelli", "Giarrusso", "Giavarini", "Giberti", 
        "Gigli", "Gilardo", "Gilli", "Ginelli", "Ginosa", "Pagano", "Marchi", "Lessio", "Amicucci"};
    
    private char[] genders = {'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 
        'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 
        'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 
        'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 
        'M', 'M', 'M', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 
        'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 
        'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 
        'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F'};
    
    private int [] eta = {25, 50, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 
        42, 45, 20, 30, 40, 50, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 
        30, 31, 32, 33, 34, 35, 36, 37, 38, 20, 21, 22, 23, 24, 25, 26, 27, 28, 
        29, 30, 31, 32, 33, 34, 25, 50, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 
        38, 40, 42, 45, 20, 30, 40, 50, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 
        28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 20, 21, 22, 23, 24, 25, 26, 
        27, 28, 29, 30, 31, 22, 23, 24, 25, 32};
    
    private int dimensione = nomi.length;
    String[] email = new String[dimensione];
    LinkedList <Client> clienti = new LinkedList();
    LinkedList <Client> autisti = new LinkedList();
    LinkedList <Client> passeggeri = new LinkedList();
    LinkedList <Travel> viaggi = new LinkedList();
    LinkedList <Driver> macchine = new LinkedList();
    LinkedList <Travel> tuttiViaggi = new LinkedList();
    LinkedList <Taxi> tuttiTaxi = new LinkedList();
    
    private String[] carModel = {"Volkswagen Golf", "Renault Clio", "Ford Fiesta", 
        "Opel Corsa", "Nissan Qashqai", "Ford Focus", "Volkswagen Passat", "Fiat Punto", 
        "Skoda Octavia", "Peugeot 208", "Volkswagen Polo", "Peugeot 308", "Audi A3", 
        "Opel Astra", "Renault Captur", "Toyota Yaris", "Mercedes C 230", "Fiat Punto", 
        "Opel Mokka", "Fiat Panda", "Fiat 500", "Kia Rio", "Volkswagen Golf", "Audi A6", 
        "Renault Clio", "Ford Fiesta", "Opel Corsa", "Nissan Qashqai", "Ford Focus", 
        "Volkswagen Passat", "Skoda Octavia", "Peugeot 208", "Volkswagen Polo", "Peugeot 308", 
        "Skoda Fabia", "Fiat Panda", "Dacia Sandero", "BMW 320", "Seat Leon", "Citroen C3", 
        "Dacia Duster", "Toyota Auris", "Kia Sportage", "Renault Mégane", "Audi A4", 
        "Seat Ibiza", "Mercedes A 160", "Hyundai Solaris", "Hyundai i20", "Nissan Juke", 
        "Ford Kuga", "Volkswagen Golf", "Renault Clio", "Ford Fiesta", "Opel Corsa", 
        "Nissan Qashqai", "Ford Focus", "Volkswagen Passat", "Skoda Octavia", "Peugeot 208", 
        "Volkswagen Polo", "BMW E60", "Fiat 500L", "Skoda Rapid", "Audi A6", "Kia Cee?d", 
        "Citroen C4 Picasso", "Renault Twingo", "Opel Insignia", "Audi A1", "Citroen Berlingo", 
        "Citroen C4", "Toyota Aygo", "Hyundai ix35", "Mercedes B 220", "Ford Mondeo", 
        "Volvo V40", "Volvo XC60", "Renault Kangoo", "Skoda Yeti", "Citroen C4 Cactus", 
        "Fiat 500X", "Lancia Ypsilon", "Alfa Romeo Giulietta"};
        
    private int[] carYear = {2010, 2011, 2012, 2013, 2014, 2015, 2006, 2007, 2008, 
        2009, 2010, 2011, 2012, 2013, 2014, 2015, 2008, 2009, 2010, 2011, 2012, 
        2013, 2014, 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2006, 2007, 
        2008, 2009, 2010, 2011, 2002, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 
        2011, 2012, 2010, 2011, 2012, 2013, 2014, 2007, 2008, 2009, 2010, 2011, 
        2012, 2013, 2014, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 
        2015, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2014, 2013, 2012, 
        2011, 2010, 2016};
    
    private boolean[] isAutista = {true, false, true, false, true, false, true, false, 
        true, false, true, false, true, true, false, true, false, true, false, true, 
        false, true, false, true, false, true, true, false, true, false, true, false, 
        true, false, true, false, true, false, true, true, false, true, false, true, 
        false, true, false, true, false, false, false, true, true, false, true, false, 
        true, false, true, false, true, false, true, false, true, true, false, true, 
        false, true, false, true, false, true, false, true, false, true, true, false, 
        true, false, true, false, true, false, true, false, true, false, true, true, 
        false, true, false, true, false, true, false, true, false, true, false, true, 
        true, false, true, false, true, false, true, false, false, true};
    
    private String[] citta = {"Alessandria", "Milano", "Milano", "Bologna", "Bari", 
        "Belluno", "Roma", "Bergamo", "Bologna", "Bolzano", "Venezia", "Roma", 
        "Caserta", "Como", "Cuneo", "Como", "Ferrara", "Firenze", "Venezia", "Genova", 
        "Grosseto", "Imperia", "Firenze", "Macerata", "Mantova", "Novara", "Milano", 
        "Modena", "Napoli", "Novara", "Padova", "Parma", "Genova", "Perugia", "Pisa", 
        "Potenza", "Reggio Calabria", "Reggio Emilia", "Rimini", "Roma", "Salerno", 
        "Siena", "Taranto", "Terni", "Trieste", "Trento", "Trieste", "Udine", "Venezia", "Verona"};
    
    private String[] corsi ={"Piazza Carlo Alberto", "Piazza Carlo Felice", "Piazza Castello", 
        "Piazza Cavour", "Piazzale Grande Torino", "Piazza Palazzo di Città", "Piazza della Reppublica", 
        "Piazza San Carlo", "Via XX Settembre ", "Piazza Savoia", "Piazza Solferino", 
        "Piazza Statuto", "Piazza Vittorio Veneto", "Piazza XVIII Dicembre", "Corso Regina Margherita", 
        "Corso San Maurizio", "Corso San Martino", "Corso Bolzano", "Corso Vittorio Emanuele II", 
        "Via Po", "Via Garibaldi", "Via Roma", "Via Pietro Micca", "Via Milano", "Via Nizza", 
        "Corso Giulio Cesare", "Corso Francia", "Corso Unione Sovietica", "Corso Vinzaglio", 
        "Via Paolo Sacchi", "Via Cernaia", "Corso Svizzera", "Piazzale Ciao Mario", 
        "Corso Sebastopoli", "Corso Duca degli Abruzzi"};
    
    private String[] coordinate = {"45.0686364+7.6865047", "45.0632501+7.6799207", 
        "45.0711813+7.6850388", "45.0631705+7.6897173", "45.0429575+7.6518609", 
        "45.0730919+7.6816178", "45.0758418+7.6831398", "45.0678808+7.6821725", 
        "44.8303267+7.7191000", "45.0749021+7.6774346", "45.068951+7.67698599", 
        "45.0763689+7.67062970", "45.0649017+7.69542179", "45.0738305+7.66830300", 
        "45.0754308+7.68829849", "45.068278+7.696295899", "45.0747918+7.66854850", 
        "45.0694139+7.66481799", "45.0710109+7.65639110", "45.0676943+7.69091570", 
        "45.0724281+7.68154770", "45.0686251+7.68299530", "45.0706063+7.68321200", 
        "45.0753219+7.68266180", "45.0453391+7.67227109", "45.0916004+7.69340023", 
        "45.0759108+7.65750790", "45.0396318+7.65418690", "45.0692169+7.66802830", 
        "45.0589295+7.67445920", "45.071939+7.67321689", "45.0891946+7.65935361", 
        "45.0226107+7.63532429", "45.0481027+7.63686060", "45.0628364+7.66327500"};
    
    public String [] commenti = {"il viaggio è stato molto piacevole. l'autista era puntuale, simpatico e serio, consigliatissimo!", 
        "ottima esperienza con l'autista e gli altri compagni di viaggio. Grazie del bel viaggio.", 
        "autista corretto, tranquillo e simpatico. Ottimo passeggero.", "puntuale, simpatico e di buona compagnia, consigliatissimo",
        "esperienza piacevole. il viaggio volato in ottima conversazione.", 
        "puntuale all'incontro e paziente dopo un rifornimento gasolio che ha creato imprevisti. consigliato!!!", 
        "simpaticissimo e puntualissimo! splendida compagnia...a presto!", 
        "tutto alla grande, consigliato!", "ottima esperienza, organizzata Last minute e tutto è andato liscio, disponibile e gentile", 
        "non potevo chiedere di meglio: puntuale, affidabile, sicuro!", 
        "assolutamente da consigliare agli altri utenti...molto piacevole...risponde ai messaggi e molto coretto", 
        "persona di ottima compagnia, viaggio tutto ok, molto disponibile nel venire incontro alle esigenze di tutti! consigliato", 
        "è stato un viaggio bellissimo, grazie!", "Puntuale e attento guidatore. Viaggio piacevole!", 
        "Simpatico, puntuale e di piacevole compagnia. Il viaggio è volato! Molto consigliato!", 
        "Persona super disponibile, attentissimo alle esigenze di tutti. Prudente, mette a prorpio agio, simpatico.", 
        "Ottima guida, puntuale e disponibile. Molto simpatico, si chiacchiera volentieri!", 
        "autista era molto professionale!", "macchina pulita e autista molto bravo!", 
        "rispettato il tempo previsto, molto cordiale e simpatico!", "Simpatico e piacevole consigliato!", 
        "Viaggio ok. Conducente puntuale e cortese. Conversazione su temi spirituali...", 
        "Puntuale, guida sicura e un po' troppo eccentrico...", 
        "socievole e con me è stato molto disponibile perché è venuto a prendermi sotto casa, ma nella guida ha avuto un pochino il piede troppo veloce.", 
        "Buona guida...", "autista parlava troppo, era peggio della radio, ma in generale è andata bene.", 
        "l'autista si è fermato troppo spesso e siamo arrivati in ritardo...", 
        "ci siamo persi, autista non conosceva la strada, mai più!!!!", 
        "il prezzo era troppo alto per la tratta... non mi è piacuto", 
        "non ci andrò mai più in quella macchina",
        "anche mia nonna sapeva guidare meglio!!!! :(", 
        "per vari giorni eravamo d'accordo ma il giorno della partenza non si è presentato. inaffidabile!!!"};
    
    int [] voti = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 4, 4, 4, 3, 3, 1, 1, 1, 1, 1};
    
    public void creaClient() {
        System.out.println("Lunghezza di nomi " + this.nomi.length + ", cognomi " + this.cognomi.length
                + ", email " + this.email.length + ", genders " + this.genders.length + ", eta " + this.eta.length);
        if (this.nomi.length == this.cognomi.length && this.nomi.length == this.email.length
                && this.nomi.length == this.genders.length && this.nomi.length == this.eta.length) {

            for (int i = 0; i < email.length; i++) {
                if (i % 2 == 0) {
                    email[i] = nomi[i].toLowerCase() + "." + cognomi[i].toLowerCase() + "@gmail.com";
                } else if (i % 3 == 0) {
                    email[i] = nomi[i].toLowerCase() + "." + cognomi[i].toLowerCase() + "@yahoo.com";
                } else {
                    email[i] = nomi[i].toLowerCase() + "." + cognomi[i].toLowerCase() + "@libero.it";
                }
            }

            for (int i = 0; i < nomi.length; i++) {

                clientManager.createClient(nomi[i], cognomi[i], genders[i], eta[i], email[i], cognomi[i]);
                clienti.add(clientManager.getClient(email[i]));
            }
            
            creaDriver();
        }
    }
   
    // 60 autisti in totale -> 24 ha 2 macchine -> 36 ha 1 macchina -> 84 macchine
    public void creaDriver(){
        System.out.println("Lunghezza di carModel "+this.carModel.length+ ", carYear "
                +this.carYear.length+", isAutista "+this.isAutista.length+", clienti "+this.clienti.size());
        
        if (this.carModel.length == this.carYear.length && this.isAutista.length == this.clienti.size()) {
            int postiRimasti = this.carModel.length - 60;
            for (int j=0; j<this.isAutista.length; j++){
                if (this.isAutista[j]){
                    this.autisti.add(this.clienti.get(j));
                    if (postiRimasti>0){
                        this.autisti.add(this.clienti.get(j));
                        postiRimasti--;
                    }
                }
                else{
                    this.passeggeri.add(this.clienti.get(j));
                }
            }
            
            System.out.println("Autisti sono in totale: "+this.autisti.size());
            
            if (this.autisti.size() == this.carModel.length){
                for (int i = 0; i < carModel.length; i++) {
                    driverManager.createDriver(carModel[i], carYear[i], this.autisti.get(i).getId());
                    macchine.add(driverManager.getDriver(carModel[i], carYear[i], this.autisti.get(i).getId()));
                }
            }
            
            creaTravels(420);
        }
    }
    
    //ogni macchina deve fare 5 viaggi in una delle città
    // parametri: citta; partenza = Torino; numeroPosto; id_client; id_driver; data; ora;
    public void creaTravels(int numeroViaggi){
        
        String partenza = "Torino";
        int []posti = new int[numeroViaggi];
        for (int i=0; i<posti.length; i++){
            int numPosti = 1 +(int)(Math.random() * 3);//il numero max di posti è 4 + 1 autista
            posti[i] = numPosti;
        }
        
        Long [] id_autisti = new Long[numeroViaggi];
        Long [] id_drivers = new Long[numeroViaggi];
        LinkedList<Driver> copiaMacchine = new LinkedList();
        for (int i=0; i<this.macchine.size(); i++){
            int jStart = i*5;
            for (int j=jStart; j<jStart+5; j++){
                id_autisti[j]= this.macchine.get(i).getClient_id();
                id_drivers[j]= this.macchine.get(i).getDriver_id();
                copiaMacchine.add(this.macchine.get(i));
            }
        }
        
        System.out.println("Numero di coppie macchina: "+copiaMacchine.size());
        
        String[] destinazioni = new String[numeroViaggi];
        for(int i=0; i<numeroViaggi; i++){            
            int id_citta = (int)(Math.random() * (this.citta.length-1));
            destinazioni[i] = this.citta[id_citta];
        }
        
        int[] ilMese = new int[numeroViaggi];
        int meseMin = 6;
        int meseVal = meseMin;
        for (int i=0; i<numeroViaggi; i++){
            meseVal++;
            if (meseVal<11){
                ilMese[i] = meseVal;
            }
            else{
                ilMese[i] = meseVal;
                meseVal = meseMin;
            }
            
        }
        
        int[] ilGiorno = new int[numeroViaggi];
        for (int i=0; i<numeroViaggi; i++){
            if (i%2 == 0){
                ilGiorno[i]=1+(int)(Math.random()*14);
            }
            else{
                ilGiorno[i]=16+(int)(Math.random()*14);
            }
        }
        
        Date[] leDate = new Date[numeroViaggi];
        for (int i=0; i<leDate.length; i++){
            Calendar cal = Calendar.getInstance();
            
            int ora = 5 + (int)(Math.random() * 18);
            int minuti = 1 + (int)(Math.random() * 59);
            int anno = 2016;
            //Nota i mesi vanno del 6-giugno al 12-dicembre 
            //il set è in formato (anno, mese, giorno, ora, minuti, secondi)
            cal.set(anno, ilMese[i], ilGiorno[i], ora, minuti, 00);
            leDate[i] = cal.getTime();
        }
        
        
       //public Long getTravel_ID(Long driver_id, Long client_id, Date data, Date time,String origine, String destination);
        for (int i=0; i<leDate.length; i++){
           travelManager.createTravel(id_drivers[i], id_autisti[i], partenza, destinazioni[i], leDate[i], leDate[i], posti[i]);
           Long id_travel = travelManager.getTravel_ID(id_drivers[i], id_autisti[i], leDate[i], leDate[i], partenza, destinazioni[i]);
           tuttiViaggi.add(travelManager.getTravel(id_travel));
           //System.out.println("autista: "+id_drivers[i]+" id_persona:"+ id_autisti[i]+" arrivo:"+destinazioni[i]+" quando:"+leDate[i]+" posti liberi:"+posti[i]);
        }
        
        // ogni passeggero farà 3 viaggi 
        int l = 0;
        for (int i= 0; i<this.passeggeri.size(); i++){  // passeggeri liberi
            int k = 0; //viaggio per passeggero
                for (int j=0; j<this.tuttiViaggi.size() && k<3; j++){
                    Travel temp = tuttiViaggi.get(j);
                    if(temp.getFreeSeats()>0){
                        boolean aggiunto = travelManager.addPassenger(temp.getTravel_id(), this.passeggeri.get(i).getId());
                        if (aggiunto){
                            k++;
                            creaComment(temp.getTravel_id(), this.passeggeri.get(i).getId(), l%32);
                            l++;
                            //System.out.println("Commento sul viaggio " + temp.getTravel_id() + " fatto sul client "+this.passeggeri.get(i).getId());
                        }
                    }
                }    
        }
        
        //creaTaxi(ilMese, ilGiorno, id_autisti);
        
        
    }
    
    public void creaTaxi(int[] mesi, int[] giorni, Long[] creatori){
        
        String[] partenze = new String[mesi.length];
        String[] coordPart = new String[mesi.length];
        String[] destinazioni = new String[mesi.length];
        String[] coordDesti = new String[mesi.length];
        int offset = 0;
        while (offset < mesi.length){
            for(int i=0; i<this.corsi.length; i++){
                partenze[i+offset] = this.corsi[i];
                coordPart[i+offset] = this.coordinate[i];
                int id_via = (int)(Math.random() * (this.corsi.length -1));
                while (id_via == i){
                    id_via = (int)(Math.random() * (this.corsi.length -1));
                }
                destinazioni[i+offset] = this.corsi[id_via];
                coordDesti[i+offset] = this.coordinate[id_via]; 
            }
            offset = offset + this.corsi.length;
        }
        
        System.out.println("Generate "+destinazioni.length+" destinazioni per il taxi.");
        int [] iGiorni = new int[giorni.length];
        for(int i=0; i<giorni.length; i++){
            iGiorni[i] = giorni[i];
            if (iGiorni[i]>=30){
                giorni[i]=1;
            }
            if (iGiorni[i]>0 && iGiorni[i]<30){
                giorni[i] = iGiorni[i]+1;
            }
        }
        
        System.out.println("Creazione delle date per i taxi.");
        
        
        Date[] leDate = new Date[mesi.length];
        for (int i=0; i<leDate.length; i++){
            Calendar cal = Calendar.getInstance();
            
            int ora = 5 + (int)(Math.random() * 18);
            int minuti = 1 + (int)(Math.random() * 59);
            int anno = 2016;
            //Nota i mesi vanno del 6-giugno al 12-dicembre 
            //il set è in formato (anno, mese, giorno, ora, minuti, secondi)
            cal.set(anno, mesi[i], giorni[i], ora, minuti, 00);
            leDate[i] = cal.getTime();
        }
        
        int []posti = new int[mesi.length];
        for (int i=0; i<posti.length; i++){
            int numPosti = 1 +(int)(Math.random() * 3);//il numero max di posti è 4 + 1 autista
            posti[i] = numPosti;
        }
        
        /*public void createTaxi(Long creator_id, Long id_client, Date data, 
        Date time, String origin, String destination, int freeSeat, 
            String coordStart, String coordEnd);*/
        for (int i=0; i<leDate.length; i++){
            taxiManager.createTaxi(creatori[i], creatori[i], leDate[i], leDate[i], partenze[i], destinazioni[i], posti[i], coordPart[i], coordDesti[i]);
        }
        
        Long tempId = null;
        for (int i=0; i<this.macchine.size(); i++){
            Long creatore = this.macchine.get(i).getClient_id();
            if (!creatore.equals(tempId)){
                tempId = creatore;
                List<Taxi> tempT = taxiManager.getTaxiCreated(creatore); 
                this.tuttiTaxi.addAll(tempT);

            }
        }
        
        System.out.println("Taxi creati sono: "+this.tuttiTaxi.size());
        
        for (int i= 0; i<this.passeggeri.size(); i++){  // passeggeri liberi
            int k = 0; //viaggio per passeggero
                for (int j=0; j<this.tuttiTaxi.size() && k<3; j++){
                    Taxi temp = tuttiTaxi.get(j);
                    if(temp.getFreeSeats()>0){
                        boolean aggiunto = taxiManager.addPassenger(temp.getTaxi_id(), this.passeggeri.get(i).getId());
                        if (aggiunto){
                            k++;
                            //creaComment(temp.getTravel_id(), this.passeggeri.get(i).getId());
                            //System.out.println("Commento sul viaggio " + temp.getTravel_id() + " fatto sul client "+this.passeggeri.get(i).getId());
                        }
                    }
                }    
        }
        
        
    }
    
    public void creaComment(Long id_travel, Long author, int posizione){
        
        if (posizione<this.commenti.length){
            Travel viaggio = travelManager.getTravel(id_travel);
            Long autista = viaggio.getDriver_id();
            Date dataCommento = viaggio.getData();
            Date oraCommento = viaggio.getTime();
            
            //public void createComment(Long id_author, Long id_clientJudged, Long id_travel, 
            //    String comment, int feedback, Date comment_date, Date commet_hour);
            commentManager.createComment(author, autista, id_travel, commenti[posizione].toLowerCase(), 
                    this.voti[posizione], dataCommento, oraCommento);
        }
        else{
            System.out.println("Fuori dai commenti...");
        }
    }

    public LinkedList<Client> getClienti() {
        return clienti;
    }

    public LinkedList<Client> getAutisti() {
        return autisti;
    }

    public LinkedList<Travel> getViaggi() {
        return viaggi;
    }

    public LinkedList<Driver> getMacchine() {
        return macchine;
    }
    
    
    
    public void stampaCommenti(){
        for (int i=0; i<email.length; i++){
            Long id_cliente = clientManager.getClient(email[i]).getId();
            List<Comment> lista = commentManager.getCommentReceived(id_cliente);
            System.out.println("Ecco i commenti sul cliente "+email[i]);
            for (Comment commento : lista){
                System.out.println("Commento da client: "+commento.getId_author()+
                        " testo: "+commento.getComment());
            }
            System.out.println("Il feedback del cliente è: "+ commentManager.getAverageFeedback(id_cliente));
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
