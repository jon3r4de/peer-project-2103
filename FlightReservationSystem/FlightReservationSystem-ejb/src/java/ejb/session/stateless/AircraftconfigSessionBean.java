/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AirCraftConfig;
import entity.AirCraftType;
import entity.CabinClass;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.AircraftConfigExistExcetpion;
import util.exception.AircraftConfigNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author jonang
 */
@Stateless
public class AircraftconfigSessionBean implements AircraftconfigSessionBeanRemote, AircraftconfigSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    
    @Override
    public Long createNewAircraftConfiguration(AirCraftConfig newAircraftConfiguration, AirCraftType aircraftType, List<CabinClass> cabinClasses) throws AircraftConfigExistExcetpion, GeneralException
    {
        try {
        em.persist(newAircraftConfiguration);
        } catch(PersistenceException ex) {
            if(ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getSimpleName().equals("SQLIntegrityConstraintViolationException")) {
                throw new AircraftConfigExistExcetpion("Aircraft Configuration with the name: " + newAircraftConfiguration.getAirCraftConfigName() + " already exists!"); 
            } else {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
        
        aircraftType = em.find(AirCraftType.class, aircraftType.getAircraftTypeId());
        
        for (CabinClass cabinClass : cabinClasses) {
            cabinClass.setAirCraftConfig(newAircraftConfiguration);
        }
        
        newAircraftConfiguration.setCabinClasses(cabinClasses);
        newAircraftConfiguration.setAirCraftType(aircraftType);
        aircraftType.getConfigs().add(newAircraftConfiguration);
        
        //set max seat capacity
        //newAircraftConfiguration.getMaxSeatCapacity();

        em.flush();
           
        return newAircraftConfiguration.getAirCraftConfigId();
    }
    
    @Override
    public List<AirCraftConfig> retrieveAllAircraftConfigurations() {
        Query query = em.createQuery("SELECT ac FROM AirCraftConfig ac ORDER BY ac.airCraftType.aircraftTypeName, ac.airCraftConfigName");

        //lazy loading
        List<AirCraftConfig> aircraftConfiguration = (List<AirCraftConfig>) query.getResultList();
       
        for (AirCraftConfig acc : aircraftConfiguration) {
            acc.getFlights().size();
        }
        //lazy loading
        
        return aircraftConfiguration;
    }
    
    @Override
    public AirCraftConfig retrieveAircraftConfigurationByName(String name) throws AircraftConfigNotFoundException
    {
        Query query = em.createQuery("SELECT ac FROM AirCraftConfig ac WHERE ac.airCraftConfigName = :name");
        query.setParameter("name", name);
        
        try
        {
            AirCraftConfig aircraftConfiguration = (AirCraftConfig) query.getSingleResult();
            aircraftConfiguration.getCabinClasses().size();
            aircraftConfiguration.getFlights().size();
            
            return aircraftConfiguration;
        }
        catch(NoResultException ex)
        {
            throw new AircraftConfigNotFoundException("Aircraft configuration " + name + " does not exist!\n");
        }                
    }
    
    @Override
    public AirCraftConfig retrieveAircraftConfigurationById(Long aircraftConfigurationId) throws AircraftConfigNotFoundException
    {
        AirCraftConfig aircraftConfiguration = em.find(AirCraftConfig.class, aircraftConfigurationId);
        
        if(aircraftConfiguration != null)
        {
            aircraftConfiguration.getCabinClasses().size();
            aircraftConfiguration.getFlights().size();
            return aircraftConfiguration;
        }
        else
        {
            throw new AircraftConfigNotFoundException("Aircraft configuration ID " + aircraftConfigurationId + " does not exist");
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
