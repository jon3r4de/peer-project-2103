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
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
        /*Query query = em.createQuery("SELECT f FROM FlightSchedule f WHERE f.departureDateTime = :departureDate AND f.flightSchedulePlan.flight.flightRoute.origin = :departureAirport AND f.flightSchedulePlan.flight.flightRoute.destination = :destinationAirport");
        query.setParameter("departureDate", departureDate);
        query.setParameter("departureAirport", departureAirport);
        query.setParameter("destinationAirport", destinationAirport);*/
        //cookery
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(departureDate);

            // Set the time to the beginning of the day (00:00:00)
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            Date startOfDay = calendar.getTime();

            // Set the time to the end of the day (23:59:59)
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);

            Date endOfDay = calendar.getTime();

            Query query = em.createQuery("SELECT f FROM FlightSchedule f WHERE f.departureDateTime BETWEEN :startOfDay AND :endOfDay AND f.flightSchedulePlan.flight.flightRoute.origin = :departureAirport AND f.flightSchedulePlan.flight.flightRoute.destination = :destinationAirport");
            query.setParameter("startOfDay", startOfDay);
            query.setParameter("endOfDay", endOfDay);
            query.setParameter("departureAirport", departureAirport);
            query.setParameter("destinationAirport", destinationAirport);
            
            //cookery
        List<FlightSchedule> flightSchedules = query.getResultList();
        
        if (flightSchedules.isEmpty()) {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd MMM", Locale.US);
            String departureDateString = inputDateFormat.format(departureDate);
            throw new FlightScheduleNotFoundException("Flight schedule departure from: " + departureAirportiATACode + " to " + destinationAirportiATACode + " on " + departureDateString + " does not exist!");
        }

        for (FlightSchedule flightSchedule : flightSchedules) {
            flightSchedule.getSeatInventories().size();
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
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(departureDate);

            // Set the time to the beginning of the day (00:00:00)
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            Date startOfDay = calendar.getTime();

            // Set the time to the end of the day (23:59:59)
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);

            Date endOfDay = calendar.getTime();

            Query query = em.createQuery("SELECT f FROM FlightSchedule f " +
            "WHERE FUNCTION('DATE', f.departureDateTime) >= :startOfDay " +
            "AND FUNCTION('DATE', f.departureDateTime) <= :endOfDay " +
            "AND f.flightSchedulePlan.flight.flightRoute.origin = :departureAirport " +
            "AND f.flightSchedulePlan.flight.flightRoute.destination = :destinationAirport");
            query.setParameter("startOfDay", startOfDay);
            query.setParameter("endOfDay", endOfDay);
            query.setParameter("departureAirport", departureAirport);
            query.setParameter("destinationAirport", destinationAirport);

            
        /*Query query = em.createQuery("SELECT f FROM FlightSchedule f WHERE f.departureDateTime = :departureDate AND f.departureAirport= :departureAirport");
        query.setParameter("departureDate", departureDate);
        query.setParameter("departureAirport", departureAirport);*/
        
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
        
       /* List<List<FlightSchedule>> temp = flightSchedules;
            

        for (List<FlightSchedule> flightSchedule: flightSchedules) {
            FlightSchedule firstFlightSchedule = flightSchedule.get(0);

            Airport transferAirport = firstFlightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination();
            query = em.createQuery("SELECT f FROM FlightSchedule f WHERE f.flightSchedulePlan.flight.flightRoute.origin = :transferAirport AND f.flightSchedulePlan.flight.flightRoute.destination = :destinationAirport AND f.departureDateTime BETWEEN :firstArrivalTime AND :secondDepartureTime");
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
        }*/
       
       Iterator<List<FlightSchedule>> iterator = flightSchedules.iterator();

        while (iterator.hasNext()) {
            List<FlightSchedule> flightSchedule = iterator.next();
            FlightSchedule firstFlightSchedule = flightSchedule.get(0);

            Airport transferAirport = firstFlightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination();
            query = em.createQuery("SELECT f FROM FlightSchedule f WHERE f.flightSchedulePlan.flight.flightRoute.origin = :transferAirport AND f.flightSchedulePlan.flight.flightRoute.destination = :destinationAirport AND f.departureDateTime BETWEEN :firstArrivalTime AND :secondDepartureTime");
            query.setParameter("transferAirport", transferAirport);
            query.setParameter("destinationAirport", destinationAirport);
            query.setParameter("firstArrivalTime", firstFlightSchedule.getArrivalDateTime());
            query.setParameter("secondDepartureTime", new Date(firstFlightSchedule.getArrivalDateTime().getTime() + 24 * 60 * 60 * 1000));

            List<FlightSchedule> secondFlightSchedules = query.getResultList();

            if (secondFlightSchedules.isEmpty()) {
                iterator.remove(); // Use the iterator to remove the current element
            } else {
                // Modify the existing flightSchedule instead of adding a new one
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
        //return flightSchedules;
        
    }
    
    @Override
    public FlightSchedule searchGeneralFlightSchedule(FlightSchedule fs) {
        
        
        FlightSchedule managedFlightSchedule = em.find(FlightSchedule.class, fs.getFlightScheduleId());
        
        //lazy loading
        managedFlightSchedule.getReservations().size();
        managedFlightSchedule.getSeatInventories().size();
        
        return managedFlightSchedule;
    }
    
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
