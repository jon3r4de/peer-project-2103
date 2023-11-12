/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AirCraftConfig;
import entity.CabinClass;
import entity.Flight;
import entity.FlightRoute;
import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.AircraftConfigNotFoundException;
import util.exception.DeleteFlightException;
import util.exception.FlightExistException;
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteDisabledException;
import util.exception.FlightRouteNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author jonang
 */
@Stateless
public class FlightSessionBean implements FlightSessionBeanRemote, FlightSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;
    
    @EJB
    private AircraftconfigSessionBeanLocal aircraftconfigSessionBeanLocal;

    @EJB
    private FlightRouteSessionBeanLocal flightRouteSessionBeanLocal;
    
    @Override
    public Long createNewFlight(Flight newFlight, String originAirportIATACode, String destinationAirportIATACode, String aircraftConfigurationName) 
            throws FlightExistException, GeneralException, FlightRouteNotFoundException, AircraftConfigNotFoundException, FlightRouteDisabledException {
        
        try {
            
            FlightRoute flightRoute = flightRouteSessionBeanLocal.retrieveFlightRouteByOdPair(originAirportIATACode, destinationAirportIATACode);
            
            if(flightRoute.isDisabled()) {

                throw new FlightRouteDisabledException("The flight route from " + originAirportIATACode + " to " + destinationAirportIATACode + " is disabled!");
            
            } else {
                
                AirCraftConfig aircraftConfig= aircraftconfigSessionBeanLocal.retrieveAircraftConfigurationByName(aircraftConfigurationName);
                em.persist(newFlight);
                
                flightRoute.getFlights().add(newFlight);
                
                newFlight.setAirCraftConfig(aircraftConfig);
                newFlight.setFlightRoute(flightRoute);
                
                aircraftConfig.getFlights().add(newFlight);
                
                em.flush();

                return newFlight.getFlightId();
            }
            
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("SQLIntegrityConstraintViolationException")) {
                throw new FlightExistException("The flight already exists!");
            } else {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
    }
    
    @Override
    public void associateComplementaryFlight(Long flightId, Long complementaryFlightId) {
        Flight flight = em.find(Flight.class, flightId);
        Flight complementaryFlight = em.find(Flight.class, complementaryFlightId);
        
        flight.setComplementaryReturnFlight(complementaryFlight);
        flight.setHasComplementaryReturnFlight(true);
        
        /*comp flight has the creator flight stored 
        in the complementary flight but the boolean has comp flight is set as false
        --> allow for deletion of the comp flight wihtout affecting the creator flight
        */
        complementaryFlight.setComplementaryReturnFlight(flight);
    }
    
    @Override
    public List<Flight> retrieveAllFlights() {
        Query query = em.createQuery("SELECT f FROM Flight f ORDER BY f.flightNumber ASC");
        
        List<Flight> flights = query.getResultList();
        for(Flight flight: flights) {
            flight.getComplementaryReturnFlight();
        }
        return flights;
    }
    
    @Override
    public Flight retrieveFlightById(Long flightId) throws FlightNotFoundException
    {
        Flight flight = em.find(Flight.class, flightId);
        
        if(flight != null)
        {
            flight.getFlightSchedulePlans().size();
            return flight;
        }
        else
        {
            throw new FlightNotFoundException("Flight ID " + flightId + " does not exist");
        }
    }

    @Override
    public void deleteFlight(Flight flight) throws FlightNotFoundException, DeleteFlightException {
        

        if (flight.getFlightSchedulePlans().isEmpty()) {
            flight.getFlightRoute().getFlights().remove(flight);
            flight.getAirCraftConfig().getFlights().remove(flight);
            
            if (flight.getComplementaryReturnFlight() != null) {
                //Flight compFlight = flight.getComplementaryReturnFlight();
                flight.getComplementaryReturnFlight().setComplementaryReturnFlight(null);
                flight.setComplementaryReturnFlight(null);
                //deleteComplementaryFlight(compFlight);
            }
            if (!em.contains(flight)) {
                flight = em.merge(flight);
            }
            
            em.remove(flight);
        } else {
            flight.setDisabled(true);
            throw new DeleteFlightException("Flight no. " + flight.getFlightNumber() + " is in use and cannot be deleted! Instaed, it is set as disabled. ");
        }
    }
    
    /*private void deleteComplementaryFlight(Flight flight) throws DeleteFlightException {
        
        if (flight.getFlightSchedulePlans().isEmpty()) {
            flight.getFlightRoute().getFlights().remove(flight);
            flight.getAirCraftConfig().getFlights().remove(flight);
            
            if (!em.contains(flight)) {
                flight = em.merge(flight);
            }
            
            em.remove(flight);
        } else {
            flight.setDisabled(true);
            throw new DeleteFlightException("Complementary Flight no. " + flight.getFlightNumber() + " is in use and cannot be deleted! Instaed, it is set as disabled. ");
        }
        
    }*/

    @Override
    public List<FlightSchedule> retrieveFlightSchedulesByFlightNumber(String flightNumber) throws FlightNotFoundException {
        Flight flight = retrieveFlightByFlightNumber(flightNumber);
        List<FlightSchedule> list = new ArrayList<>();
        List<FlightSchedulePlan> flightSchedulePlans = flight.getFlightSchedulePlans();
        //lazy loading
        flightSchedulePlans.size();
        
        for (FlightSchedulePlan flightSchedulePlan : flightSchedulePlans) {
            if (flightSchedulePlan.getDisabled() == false) {
                List<FlightSchedule> flightSchedules = flightSchedulePlan.getFlightSchedules();
                flightSchedules.size();
                for (FlightSchedule flightSchedule : flightSchedules) {
                    list.add(flightSchedule);
                }
            }
        }
        return list;
    }
        
    @Override
    public void updateFlight(Flight flight, String newAircraftConfigurationName) throws AircraftConfigNotFoundException {
        
        AirCraftConfig newAircraftConfiguration = aircraftconfigSessionBeanLocal.retrieveAircraftConfigurationByName(newAircraftConfigurationName);

        flight.getAirCraftConfig().getFlights().remove(flight);
        flight.setAirCraftConfig(newAircraftConfiguration);
        
        em.merge(flight);
        
        Flight complementaryFlight = flight.getComplementaryReturnFlight();
        complementaryFlight.getAirCraftConfig().getFlights().remove(complementaryFlight);
        complementaryFlight.setAirCraftConfig(newAircraftConfiguration);
    }
    
    @Override
    public Flight retrieveFlightByFlightNumber(String flightNumber) throws FlightNotFoundException {
        Query query = em.createQuery("SELECT f FROM Flight f WHERE f.flightNumber = :inFlightNumber");
        query.setParameter("inFlightNumber", flightNumber);
       
        try {
            Flight flight = (Flight) query.getSingleResult();
            if (flight != null) {
                flight.getFlightRoute();
                flight.getComplementaryReturnFlight();
                flight.getFlightSchedulePlans().size();
                for (CabinClass cabinClass: flight.getAirCraftConfig().getCabinClasses()) {
                    cabinClass.getCabinClassType();
                    cabinClass.getMaxSeatCapacity();
                }
                return flight;
            } else {
                throw new FlightNotFoundException("Flight number " + flightNumber + " is not found!");
            }
        } catch (NoResultException e) {
            throw new FlightNotFoundException("Flight number " + flightNumber + " is not found!");
        }
    }
    
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
