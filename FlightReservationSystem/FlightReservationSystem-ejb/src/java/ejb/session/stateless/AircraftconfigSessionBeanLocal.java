/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.AirCraftConfig;
import entity.AirCraftType;
import entity.CabinClass;
import entity.SeatInventory;
import java.util.List;
import javax.ejb.Local;
import util.exception.AircraftConfigExistExcetpion;
import util.exception.AircraftConfigNotFoundException;
import util.exception.GeneralException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author jonang
 */
@Local
public interface AircraftconfigSessionBeanLocal {
    
    public Long createNewAircraftConfiguration(AirCraftConfig newAircraftConfiguration, AirCraftType aircraftType, List<CabinClass> cabinClasses) throws AircraftConfigExistExcetpion, GeneralException;
    
     public List<AirCraftConfig> retrieveAllAircraftConfigurations();
     
     public AirCraftConfig retrieveAircraftConfigurationByName(String name) throws AircraftConfigNotFoundException;
     
     public AirCraftConfig retrieveAircraftConfigurationById(Long aircraftConfigurationId) throws AircraftConfigNotFoundException;

    public void generateSeats(CabinClass cabinClass, SeatInventory seatInventory);
}
