package org.conpartir.sessionBean;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.conpartir.entity.Driver;
import org.conpartir.facade.DriverFacadeLocal;

/**
 * Implementazione del SessionBean Stateless <i>DriverManager</i> per la
 * gestione che riguarda il Driver. Implemententa tutti i metodi esposti
 * nell'interfaccia DriverManagerLocal.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/gipjg.html">
 * Session Bean </a>
 */
@Stateless
public class DriverManager implements DriverManagerLocal {

    /**
     * EntityManager che rende persistenti le modiche del SessionBean.
     */
    @EJB
    private DriverFacadeLocal driverFacade;

    /**
     * Crea una tupla nel database tramite un oggetto di tipo driver
     *
     * @param driver da memorizzare nel database.
     */
    @Override
    public void createDriver(Driver driver) {
        if (driver.getCarModel() != null && driver.getCarYear() != 0
                && driver.getClient_id() != null) {
            if (!isExist(driver.getCarModel(), driver.getCarYear(), driver.getClient_id())) {
                driverFacade.create(driver);
            }
        }
    }

    /**
     * Crea una tupla nel database tramite gli argomenti esplici.
     *
     * @param carModel valore dell'attributo carModel del Entity Driver.
     * @param carYear valore dell'attributo carYear del Entity Driver.
     * @param client_id valore dell'identificativo univoco del cliente.
     */
    @Override
    public void createDriver(String carModel, int carYear, Long client_id) {
        if (carModel != null && carYear != 0 && client_id != null) {
            if (!isExist(carModel, carYear, client_id)) {
                Driver driver = new Driver();
                driver.setCarModel(carModel);
                driver.setCarYear(carYear);
                driver.setClient_id(client_id);
                driverFacade.create(driver);
            }
        }
    }

    /**
     * Restituisce l'oggetto di tipo driver, se esiste, ricercato per ID del
     * driver.
     *
     * @param ID identificativo univoco del driver.
     * @return Driver oggetto cercato.
     */
    @Override
    public Driver getDriver(Long ID) {
        Driver driver = new Driver();
        List<Driver> list = driverFacade.findAll();
        for (Driver temp : list) {
            Long tempID = temp.getDriver_id();
            if (tempID.equals(ID)) {
                driver = temp;
            }
        }
        return driver;
    }

    /**
     * Restituisce l'oggetto di tipo driver, se esiste, ricercato per chiave
     * esterna.
     *
     * @param carModel valore dell'attributo carModel del Entity Driver.
     * @param carYear valore dell'attributo carYear del Entity Driver.
     * @param client_id valore dell'identificativo univoco del cliente.
     * @return Driver oggetto cercato.
     */
    @Override
    public Driver getDriver(String carModel, int carYear, Long client_id) {
        Driver driver = new Driver();
        List<Driver> lista = driverFacade.findAll();
        for (Driver temp : lista) {
            if (temp.getCarModel().equals(carModel) && temp.getCarYear() == carYear
                    && temp.getClient_id().equals(client_id)) {
                driver = temp;
            }
        }
        return driver;
    }

    /**
     * Verifica se l'identificativo univoco del Driver è già presente nel
     * database.
     *
     * @param ID identificativo univoco del Driver.
     * @return boolean che vale True se il Driver è presente nel database
     * altrimenti è False.
     */
    @Override
    public boolean isDriver(Long ID) {
        boolean risultato = false;
        List<Driver> list = driverFacade.findAll();
        for (Driver temp : list) {
            Long tempID = temp.getDriver_id();
            if (tempID.equals(ID)) {
                risultato = true;
            }
        }
        return risultato;
    }

    /**
     * Restituisce la lista di Driver di un cliente.
     *
     * @param client_id identificativo univoco del cliente.
     * @return List di Driver di proprietà del client.
     */
    @Override
    public List<Driver> getDrivers(Long client_id) {
        List<Driver> drivers = new ArrayList();
        List<Driver> lista = driverFacade.findAll();
        for (Driver driver : lista) {
            if (driver.getClient_id().equals(client_id)) {
                drivers.add(driver);
            }
        }
        return drivers;
    }

    /**
     * Verifica se esiste un oggetto di tipo Driver che ha attributi pari ai
     * parametri.
     *
     * @param carModel valore dell'attributo carModel del Entity Driver.
     * @param carYear valore dell'attributo carYear del Entity Driver.
     * @param client_id valore dell'identificativo univoco del cliente.
     * @return boolean che vale True se l'oggetto esiste altrimenti è False.
     */
    protected boolean isExist(String carModel, int carYear, Long client_id) {
        boolean risultato = true;
        Driver driver = getDriver(carModel, carYear, client_id);
        if (driver.getDriver_id() == null) {
            risultato = false;
        }
        return risultato;
    }

}
