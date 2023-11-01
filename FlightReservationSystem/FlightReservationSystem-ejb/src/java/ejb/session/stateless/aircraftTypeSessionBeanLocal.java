/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.AirCraftType;
import javax.ejb.Local;
import util.exception.AirCraftTypeNotFoundException;
import util.exception.AircraftTypeExistException;
import util.exception.GeneralException;

/**
 *
 * @author jonang
 */
@Local
public interface aircraftTypeSessionBeanLocal {
    public AirCraftType retrieveAircraftTypeByName(String aircraftTypeName) throws AirCraftTypeNotFoundException;

    public AirCraftType createNewAircraftType(AirCraftType aircraftType) throws AircraftTypeExistException, GeneralException;
}
