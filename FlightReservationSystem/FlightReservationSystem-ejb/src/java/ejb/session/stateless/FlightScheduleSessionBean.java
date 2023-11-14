/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Airport;
import entity.FlightSchedule;
import entity.Reservation;
import enumeration.CabinClassEnum;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AirportNotFoundException;
import util.exception.FlightScheduleNotFoundException;

/**
 *
 * @author tristan
 */
@Stateless
public class FlightScheduleSessionBean implements FlightScheduleSessionBeanRemote, FlightScheduleSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;
    
    @EJB(name = "AirportSessionBeanLocal")
    private AirportSessionBeanLocal airportSessionBeanLocal;

    @Override
    public List<Reservation> viewReservations(FlightSchedule flightSchedule) {
        Long fsId = flightSchedule.getFlightScheduleId();
        FlightSchedule managedFs = em.find(FlightSchedule.class, fsId);
        
        Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.flightSchedule = :flightSchedule");
        query.setParameter("flightSchedule", managedFs);
        
        return query.getResultList();
    }
    
    
    @Override
    public List<FlightSchedule> searchDirectFlightSchedules(String departureAirportiATACode, String destinationAirportiATACode, Date departureDate, CabinClassEnum cabinClassType) throws AirportNotFoundException, FlightScheduleNotFoundException {

        Airport departureAirport = airportSessionBeanLocal.retrieveAirportByIataCode(departureAirportiATACode);
        Airport destinationAirport = airportSessionBeanLocal.retrieveAirportByIataCode(destinationAirportiATACode);

        //flightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getOrigin();
        Query query = em.createQuery("SELECT f FROM FlightSchedule f WHERE f.departureDateTime = :departureDate AND f.flightSchedulePlan.flight.flightRoute.origin = :departureAirport AND f.flightSchedulePlan.flight.flightRoute.destination = :destinationAirport");
        query.setParameter("departureDate", departureDate);
        query.setParameter("departureAirport", departureAirport);
        query.setParameter("destinationAirport", destinationAirport);

        List<FlightSchedule> flightSchedules = query.getResultList();

        if (flightSchedules.isEmpty()) {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd MMM", Locale.US);
            String departureDateString = inputDateFormat.format(departureDate);
            throw new FlightScheduleNotFoundException("Flight schedule departure from: " + departureAirportiATACode + " to " + destinationAirportiATACode + " on " + departureDateString + " does not exist!");
        }

        for (FlightSchedule flightSchedule : flightSchedules) {
            flightSchedule.getCabinClasses().size();
            flightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination();
            flightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getOrigin();
        }
        return flightSchedules;
    }
    
    @Override
    public List<List<FlightSchedule>> searchConnectingFlightScehdules(String departureAirportiATACode, String destinationAirportiATACode, Date departureDate,
            CabinClassEnum cabinClassType) throws AirportNotFoundException, FlightScheduleNotFoundException {
        
        Airport departureAirport = airportSessionBeanLocal.retrieveAirportByIataCode(departureAirportiATACode);
        Airport destinationAirport = airportSessionBeanLocal.retrieveAirportByIataCode(destinationAirportiATACode);

        Query query = em.createQuery("SELECT f FROM FlightSchedule f WHERE f.departureDateTime = :departureDate AND f.departureAirport= :departureAirport");
        query.setParameter("departureDate", departureDate);
        query.setParameter("departureAirport", departureAirport);
        
        List<FlightSchedule> firstFlightSchedules = query.getResultList();
        if (firstFlightSchedules.isEmpty()) {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd MMM", Locale.US);
            String departureDateString = inputDateFormat.format(departureDate);
            throw new FlightScheduleNotFoundException("Flight schedule departure from: " + departureAirportiATACode + " to " + destinationAirportiATACode + " on " + departureDateString + " does not exist!");
        }

        
        List<List<FlightSchedule>> flightSchedules = new ArrayList<>();
        for (FlightSchedule firstFlightSchedule: firstFlightSchedules) {
            List<FlightSchedule> flightSchedule = new ArrayList<>();
            flightSchedule.add(firstFlightSchedule);
            flightSchedules.add(flightSchedule);
        }
        
        for (List<FlightSchedule> flightSchedule: flightSchedules) {
            FlightSchedule firstFlightSchedule = flightSchedule.get(0);
            Airport transferAirport = firstFlightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination();
            query = em.createQuery("SELECT f FROM FlightSchedule f WHERE f.departureAirport = :transferAirport AND f.destinationAirport = :destinationAiport AND f.departureDateTime BETWEEN :firstArrivalTime AND :secondDepartureTime");
            query.setParameter("transferAirport", transferAirport);
            query.setParameter("destinationAirport", destinationAirport);
            query.setParameter("firstArrivalTime", firstFlightSchedule.getArrivalDateTime());
            query.setParameter("secondDepartureTime", new Date(firstFlightSchedule.getArrivalDateTime().getTime() + 24 * 60 * 60 * 1000));
            
            List<FlightSchedule> secondFlightSchedules = query.getResultList();
            
            if (secondFlightSchedules.isEmpty()) {
                flightSchedules.remove(flightSchedule);
            } else {
                flightSchedule.addAll(secondFlightSchedules);
            }
        }
        
        if (flightSchedules.isEmpty()) {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd MMM", Locale.US);
            String departureDateString = inputDateFormat.format(departureDate);
            throw new FlightScheduleNotFoundException("Connecting Flight schedule departure from " + departureAirportiATACode + " to " +
                    destinationAirportiATACode + " on " + departureDateString + " does not exist!");
        }
        
        return flightSchedules;
        
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
