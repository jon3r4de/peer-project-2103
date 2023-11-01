/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AirCraftType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.AirCraftTypeNotFoundException;
import util.exception.AircraftTypeExistException;
import util.exception.GeneralException;

/**
 *
 * @author jonang
 */
@Stateless
public class aircraftTypeSessionBean implements aircraftTypeSessionBeanRemote, aircraftTypeSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public AirCraftType createNewAircraftType(AirCraftType aircraftType) throws AircraftTypeExistException, GeneralException {
           
        try {         
            em.persist(aircraftType);
            em.flush();        
            
            aircraftType.getConfigs();

            return aircraftType;
        }catch(PersistenceException ex) {
            if(ex.getCause() != null && 
                ex.getCause().getCause() != null &&
                ex.getCause().getCause().getClass().getSimpleName().equals("SQLIntegrityConstraintViolationException")) {
                throw new AircraftTypeExistException("Aircraft Type with Name: " + aircraftType.getAircraftTypeName() + " does not exist!");
            } else {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
    }
    
    @Override
    public AirCraftType retrieveAircraftTypeByName(String aircraftTypeName) throws AirCraftTypeNotFoundException {       
        Query query = em.createQuery("SELECT at FROM AirCraftType at WHERE at.aircraftTypeName = :inAircraftTypeName");
        query.setParameter("inAircraftTypeName", aircraftTypeName);

        
        try
        {
            return (AirCraftType)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new AirCraftTypeNotFoundException("AircraftType " + aircraftTypeName + " does not exist!");
        }
    }
    
}
