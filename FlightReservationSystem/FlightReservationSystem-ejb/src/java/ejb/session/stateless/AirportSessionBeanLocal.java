/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Airport;
import javax.ejb.Local;
import util.exception.AirportExistException;
import util.exception.AirportNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author jonang
 */
@Local
public interface AirportSessionBeanLocal {
    
    public Airport createNewAirport(Airport airport) throws AirportExistException, GeneralException;
    public Airport retrieveAirportByIataCode(String iataCode) throws AirportNotFoundException;
    
}
