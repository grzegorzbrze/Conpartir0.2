package org.conpartir.sessionBean;

import java.util.List;
import javax.ejb.Local;
import org.conpartir.entity.Driver;

/**
 * Interfaccia locale <i>DriverManagerLocal</i> che espone tutti metodi che
 * possono essere applicati al Session Bean.
 *
 * @author Conpartir Group
 * @version 0.3 21 settembre 2016
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/gipjg.html">
 * Session Bean </a>
 */
@Local
public interface DriverManagerLocal {

    /**
     * Crea una tupla nel database tramite un oggetto di tipo driver
     *
     * @param driver da memorizzare nel database.
     */
    public void createDriver(Driver driver);

    /**
     * Crea una tupla nel database tramite gli argomenti esplici.
     *
     * @param carModel valore dell'attributo carModel del Entity Driver.
     * @param carYear valore dell'attributo carYear del Entity Driver.
     * @param client_id valore dell'identificativo univoco del cliente.
     */
    public void createDriver(String carModel, int carYear, Long client_id);

    /**
     * Restituisce l'oggetto di tipo driver, se esiste, ricercato per ID del
     * driver.
     *
     * @param driver_ID identificativo univoco del driver.
     * @return Driver oggetto cercato.
     */
    public Driver getDriver(Long driver_ID);

    /**
     * Restituisce l'oggetto di tipo driver, se esiste, ricercato per chiave
     * esterna.
     *
     * @param carModel valore dell'attributo carModel del Entity Driver.
     * @param carYear valore dell'attributo carYear del Entity Driver.
     * @param client_id valore dell'identificativo univoco del cliente.
     * @return Driver oggetto cercato.
     */
    public Driver getDriver(String carModel, int carYear, Long client_id);

    /**
     * Verifica se l'identificativo univoco del Driver è già presente nel
     * database.
     *
     * @param ID identificativo univoco del Driver.
     * @return boolean che vale True se il Driver è presente nel database
     * altrimenti è False.
     */
    public boolean isDriver(Long ID);

    /**
     * Restituisce la lista di Driver di un cliente.
     *
     * @param client_id identificativo univoco del cliente.
     * @return List di Driver di proprietà del client.
     */
    public List<Driver> getDrivers(Long client_id);

}
