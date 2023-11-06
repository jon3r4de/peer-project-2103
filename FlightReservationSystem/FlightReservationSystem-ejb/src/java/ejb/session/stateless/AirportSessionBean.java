/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Airport;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.AirportExistException;
import util.exception.AirportNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author jonang
 */
@Stateless
public class AirportSessionBean implements AirportSessionBeanRemote, AirportSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
    @Override
    public Airport createNewAirport(Airport airport) throws AirportExistException, GeneralException
    {
        try
        {
            em.persist(airport);
            em.flush();
 
            return airport;
        }
        catch(PersistenceException ex)
        {
            if(ex.getCause() != null && 
                    ex.getCause().getCause() != null &&
                    ex.getCause().getCause().getClass().getSimpleName().equals("SQLIntegrityConstraintViolationException"))
            {
                throw new AirportExistException("This airport already exist");
            }
            else
            {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
    }
    
    @Override
    public Airport retrieveAirportByIataCode(String iataCode) throws AirportNotFoundException {
        Query query = em.createQuery("SELECT a FROM Airport a WHERE a.iataAirportcode = :inIataCode");
        query.setParameter("inIataCode", iataCode);
        
        try
        {
            //lazy loading
            Airport airport = (Airport)query.getSingleResult();
            airport.getFlightsFromAirport().size();
            airport.getFlightsToAirport().size();
             
            return airport;
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new AirportNotFoundException("Airport with IATA code: " + iataCode + " does not exist!");
        }
    }

}
