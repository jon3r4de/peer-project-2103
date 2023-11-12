/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Fare;
import entity.Flight;
import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.DeleteFlightSchedulePlanException;
import util.exception.FlightSchedulePlanExistException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.GeneralException;
import util.exception.UpdateFlightSchedulePlanException;

/**
 *
 * @author tristan
 */
@Stateless
public class FlightSchedulePlanSessionBean implements FlightSchedulePlanSessionBeanRemote, FlightSchedulePlanSessionBeanLocal {
    
    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private FareSessionBeanLocal fareSessionBeanLocal;
    
    /**
     *
     * @param newFlightSchedulePlan
     * @param flightId
     * @param departureDateTime
     * @param estimatedFlightDuration
     * @return
     * @throws FlightSchedulePlanExistException
     * @throws GeneralException
     */
    @Override
    public Long createNewSingleFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long flightId, Date departureDateTime, Date estimatedFlightDuration) throws FlightSchedulePlanExistException, GeneralException {
        try {
            em.persist(newFlightSchedulePlan);

            // link flight and flightscheduleplan
            Flight flight = em.find(Flight.class, flightId);
            newFlightSchedulePlan.setFlight(flight);
            flight.getFlightSchedulePlans().add(newFlightSchedulePlan);
            
            // link fare and flightscheduleplan
            for (Fare fare : newFlightSchedulePlan.getFares()) {
                fare.setFlightSchedulePlan(newFlightSchedulePlan);
                //Long fareSavedId = fareSessionBeanLocal.createNewFare(fare);
                //System.out.println("Fare saved, ID: " + fareSavedId);
            }

            Date arrivalDateTime = this.findArrivalDateTime(departureDateTime, estimatedFlightDuration);

            FlightSchedule newFlightSchedule = new FlightSchedule(departureDateTime, estimatedFlightDuration, arrivalDateTime, flight.getFlightNumber(), flight.getAirCraftConfig().getCabinClasses(), newFlightSchedulePlan);
            em.persist(newFlightSchedule);
            newFlightSchedule.setFlightSchedulePlan(newFlightSchedulePlan);
            em.flush();
            newFlightSchedulePlan.getFlightSchedules().add(newFlightSchedule);

            em.flush();
            return newFlightSchedulePlan.getFlightSchedulePlanId();
        } catch (PersistenceException | ParseException ex) {
            if(ex.getCause() != null && 
                    ex.getCause().getCause() != null &&
                    ex.getCause().getCause().getClass().getSimpleName().equals("SQLIntegrityConstraintViolationException"))
            {
                throw new FlightSchedulePlanExistException("This flight schedule plan already exists!");
            }
            else {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
    }
    
    /**
     *
     * @param newFlightSchedulePlan
     * @param flightId
     * @param departureDateTimes
     * @param estimatedFlightDurations
     * @return
     * @throws FlightSchedulePlanExistException
     * @throws GeneralException
     */
    @Override
    public Long createNewMultipleFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long flightId, List<Date> departureDateTimes, List<Date> estimatedFlightDurations) throws FlightSchedulePlanExistException, GeneralException {
        try {
            em.persist(newFlightSchedulePlan);
            
            // link flight and flightscheduleplan
            Flight flight = em.find(Flight.class, flightId);
            newFlightSchedulePlan.setFlight(flight);
            flight.getFlightSchedulePlans().add(newFlightSchedulePlan);
            
            // link fare and flightscheduleplan
            for (Fare fare : newFlightSchedulePlan.getFares()) {
                fare.setFlightSchedulePlan(newFlightSchedulePlan);
                //Long fareSavedId = fareSessionBeanLocal.createNewFare(fare);
                //System.out.println("Fare saved, ID: " + fareSavedId);
            }
            
            for (int i = 0; i < departureDateTimes.size(); i++) {
                Date arrivalDateTime = this.findArrivalDateTime(departureDateTimes.get(i), estimatedFlightDurations.get(i));
                FlightSchedule newFlightSchedule = new FlightSchedule(departureDateTimes.get(i), estimatedFlightDurations.get(i), arrivalDateTime, flight.getFlightNumber(), flight.getAirCraftConfig().getCabinClasses(), newFlightSchedulePlan);
                em.persist(newFlightSchedule);
                newFlightSchedule.setFlightSchedulePlan(newFlightSchedulePlan);
                em.flush();
                newFlightSchedulePlan.getFlightSchedules().add(newFlightSchedule);
            }
            
            em.flush();
            return newFlightSchedulePlan.getFlightSchedulePlanId();
        } catch (PersistenceException | ParseException ex) {
            if(ex.getCause() != null && 
                    ex.getCause().getCause() != null &&
                    ex.getCause().getCause().getClass().getSimpleName().equals("SQLIntegrityConstraintViolationException"))
            {
                throw new FlightSchedulePlanExistException("This flight schedule plan already exists!");
            }
            else {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
    }
    
    /**
     *
     * @param newFlightSchedulePlan
     * @param flightId
     * @param departureDateTime
     * @param estimatedFlightDuration
     * @param endDate
     * @param recurrence
     * @return
     * @throws FlightSchedulePlanExistException
     * @throws GeneralException
     */
    @Override
    public Long createNewRecurrentFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long flightId, Date departureDateTime, Date estimatedFlightDuration, Date endDate, int recurrence) throws FlightSchedulePlanExistException, GeneralException {
        try {
            em.persist(newFlightSchedulePlan);
            
            // link flight and flightscheduleplan
            Flight flight = em.find(Flight.class, flightId);
            newFlightSchedulePlan.setFlight(flight);
            flight.getFlightSchedulePlans().add(newFlightSchedulePlan);
            
            // link fare and flightscheduleplan
            for (Fare fare : newFlightSchedulePlan.getFares()) {
                fare.setFlightSchedulePlan(newFlightSchedulePlan);
//                Long fareSavedId = fareSessionBeanLocal.createNewFare(fare);
//                System.out.println("Fare saved, ID: " + fareSavedId);
            }
            
            Date tempDate = new Date(departureDateTime.getTime());
            
            while (tempDate.getTime() <= endDate.getTime()) {
                Date arrivalDateTime = this.findArrivalDateTime(tempDate, estimatedFlightDuration);
                FlightSchedule newFlightSchedule = new FlightSchedule(tempDate, estimatedFlightDuration, arrivalDateTime, flight.getFlightNumber(), flight.getAirCraftConfig().getCabinClasses(), newFlightSchedulePlan);
                em.persist(newFlightSchedule);
                newFlightSchedule.setFlightSchedulePlan(newFlightSchedulePlan);
                em.flush();
                newFlightSchedulePlan.getFlightSchedules().add(newFlightSchedule);
                
                // move departuredatetime of new flight to date after recurrence
                tempDate = new Date(tempDate.getTime() + (recurrence * 24 * 60 * 60 * 1000));
            }
            
            em.flush();
            return newFlightSchedulePlan.getFlightSchedulePlanId();
        } catch (PersistenceException | ParseException ex) {
            if(ex.getCause() != null && 
                    ex.getCause().getCause() != null &&
                    ex.getCause().getCause().getClass().getSimpleName().equals("SQLIntegrityConstraintViolationException"))
            {
                throw new FlightSchedulePlanExistException("This flight schedule plan already exists!");
            }
            else {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
    }
    
//    public Long createNewReturnFlightSchedulePlan(Long newFlightSchedulePlanId,FlightSchedulePlan returnFlightSchedulePlan, Long returnFlightId, Date layoverDurationTime) {
//        FlightSchedulePlan newFlightSchedulePlan = em.find(FlightSchedulePlan.class, newFlightSchedulePlanId);
//        Flight returnFlight = em.find(Flight.class, returnFlightId);
//        
//        newFlightSchedulePlan.setLayoverDuration(layoverDurationTime);
//        
//        for (Fare fare : returnFlightSchedulePlan.getFares()) {
//            fare.setFlightSchedulePlan(returnFlightSchedulePlan);
//            Long fareSavedId = fareSessionBeanLocal.createNewFare(fare);
//            System.out.println("Fare saved, ID: " + fareSavedId);
//        }
//        
//        
//    }

    
    public List<FlightSchedulePlan> retrieveAllFlightSchedulePlans() {
        //Query query = em.createQuery("SELECT fsp FROM FlightSchedulePlan fsp ORDER BY fsp.flight.flightNumber ASC, fsp.firstDepartureTimeLong DESC");
       Query query = em.createQuery("SELECT fsp FROM FlightSchedulePlan fsp " +
            "ORDER BY fsp.flightNumber ASC, " +
            "(SELECT MIN(fs.departureDateTime) FROM fsp.flightSchedules fs) DESC");


        List<FlightSchedulePlan> flightSchedulePlans = query.getResultList();

        for (FlightSchedulePlan flightSchedulePlan : flightSchedulePlans) {
            flightSchedulePlan.getComplementaryReturnSchedulePlan();
            flightSchedulePlan.getFlight();
            flightSchedulePlan.getFares().size();
            flightSchedulePlan.getFlightScheduleType();
            flightSchedulePlan.getFlightSchedules().size();
        }
        return flightSchedulePlans;
    }
    
    //@Override
    public void deleteFlightSchedulePlan(FlightSchedulePlan flightSchedulePlan) throws FlightSchedulePlanNotFoundException, DeleteFlightSchedulePlanException {
        if (flightSchedulePlan.getFlightSchedules().isEmpty()) {
            flightSchedulePlan.getFlight().getFlightSchedulePlans().remove(flightSchedulePlan); //remove flightSchedulePlan from the flights list of flightSchedulePlan 
            FlightSchedulePlan complementaryFlightSchedulePlan = flightSchedulePlan.getComplementaryReturnSchedulePlan();
            complementaryFlightSchedulePlan.getFlight().getFlightSchedulePlans().remove(complementaryFlightSchedulePlan);
            em.remove(flightSchedulePlan);
            em.remove(complementaryFlightSchedulePlan);
        } else {
            flightSchedulePlan.setDisabled(true);
            throw new DeleteFlightSchedulePlanException("Flight Schedule Plan with Flight no. " + flightSchedulePlan.getFlight().getFlightNumber()
                    + " is in use and cannot be deleted! Instead, it is set as disabled. ");
        }
    }
    
    private Date findArrivalDateTime(Date departureDateTime, Date estimatedFlightDuration) throws ParseException {
        try {
            // Set the departureDateTime and estimatedFlightDuration to appropriate values
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy hh:mm aa");
            SimpleDateFormat durationFormat = new SimpleDateFormat("hh 'Hours' mm 'Minute'");

            departureDateTime = sdf.parse("01 Nov 23 09:30 AM");
            estimatedFlightDuration = durationFormat.parse("03 Hours 30 Minute");

            // Create a Calendar instance for departure date and time
            Calendar departureCalendar = Calendar.getInstance();
            departureCalendar.setTime(departureDateTime);

            // Create a Calendar instance for estimated flight duration
            Calendar durationCalendar = Calendar.getInstance();
            durationCalendar.setTime(estimatedFlightDuration);

            // Add the estimated flight duration to the departure date and time
            departureCalendar.add(Calendar.HOUR, durationCalendar.get(Calendar.HOUR));
            departureCalendar.add(Calendar.MINUTE, durationCalendar.get(Calendar.MINUTE));

            Date arrivalDateTime = departureCalendar.getTime();
            return arrivalDateTime;
        } catch (ParseException ex) {
            throw ex;
        }
    }
    
    
    @Override
    public void updateSingleFlightSchedule(FlightSchedulePlan flightSchedulePlan, FlightSchedule flightSchedule, Date departureDate, Date departureTime, Date durationTime) throws UpdateFlightSchedulePlanException, ParseException {
        em.find(FlightSchedulePlan.class, flightSchedulePlan.getFlightSchedulePlanId());
        em.find(FlightSchedule.class, flightSchedule.getFlightScheduleId());

        if (!flightSchedule.getReservations().isEmpty()) {
            throw new UpdateFlightSchedulePlanException("Flight schedule cannot be updated because tickets have already been issued!");
        } else {
            flightSchedulePlan.getFlightSchedules().remove(flightSchedule);

            Flight flight = flightSchedulePlan.getFlight();
            flightSchedulePlan.setFlight(flight);
            flight.getFlightSchedulePlans().add(flightSchedulePlan);

            //Date departureDateTime, Date estimatedFlightDuration,  String flightNumber, List<CabinClass> cabinClasses, FlightSchedulePlan flightSchedulePlan
            FlightSchedule newFlightSchedule = new FlightSchedule(departureDate, durationTime, this.findArrivalDateTime(departureDate, departureTime),flight.getFlightNumber(),
             flight.getAirCraftConfig().getCabinClasses(),flightSchedulePlan);
            em.persist(newFlightSchedule);
            newFlightSchedule.setFlightSchedulePlan(flightSchedulePlan);
            flightSchedulePlan.getFlightSchedules().add(newFlightSchedule);
        }
    }
    
        public void updateRecurrentDayFlightSchedule(FlightSchedulePlan flightSchedulePlan, Integer recurrence, Date endDate) throws UpdateFlightSchedulePlanException {
         em.find(FlightSchedulePlan.class, flightSchedulePlan.getFlightSchedulePlanId());

        for (FlightSchedule flightSchedule : flightSchedulePlan.getFlightSchedules()) {
            if (!flightSchedule.getReservations().isEmpty()) {
                throw new UpdateFlightSchedulePlanException("Flight schedule cannot be updated because tickets have already been issued!");
            }
        }
        flightSchedulePlan.setRecurrence(recurrence);
        flightSchedulePlan.setEndDate(endDate);

        FlightSchedule firstFlightSchedule = flightSchedulePlan.getFlightSchedules().get(0);
        Flight flight = flightSchedulePlan.getFlight();
        flightSchedulePlan.getFlightSchedules().clear();

        Date departureDate = firstFlightSchedule.getDepartureDateTime();
        //Date departureTime = firstFlightSchedule.getDepartureTime();
        while (departureDate.getTime() <= endDate.getTime()) {
            FlightSchedule newFlightSchedule = new FlightSchedule(departureDate, firstFlightSchedule.getEstimatedFlightDuration(), firstFlightSchedule.getArrivalDateTime(), flight.getFlightNumber(),
                    flight.getAirCraftConfig().getCabinClasses(), flightSchedulePlan);
            em.persist(newFlightSchedule);
            
            newFlightSchedule.setFlightSchedulePlan(flightSchedulePlan);
            flightSchedulePlan.getFlightSchedules().add(newFlightSchedule);

            departureDate = new Date(departureDate.getTime() + recurrence * 24 * 60 * 60 * 1000);
            em.flush();
        }

    }
        
    @Override
    public void updateRecurrentWeekFlightSchedule(FlightSchedulePlan flightSchedulePlan, Date endDate) throws UpdateFlightSchedulePlanException {
        em.merge(flightSchedulePlan);

        for (FlightSchedule flightSchedule : flightSchedulePlan.getFlightSchedules()) {
            if (flightSchedule.getDepartureDateTime().getTime() > endDate.getTime() && !flightSchedule.getReservations().isEmpty()) {
                throw new UpdateFlightSchedulePlanException("Flight schedule cannot be updated because tickets have already been issued!");
            }
        }

        flightSchedulePlan.setEndDate(endDate);

        for (FlightSchedule flightSchedule : flightSchedulePlan.getFlightSchedules()) {
            if (flightSchedule.getDepartureDateTime().getTime() > endDate.getTime()) {
                flightSchedulePlan.getFlightSchedules().remove(flightSchedule);
            }
        }
    }
    
    @Override
    public List<FlightSchedulePlan> retrieveFlightSchedulePlansByFlightNumber(String flightNumber) throws FlightSchedulePlanNotFoundException {
        Query query = em.createQuery("SELECT fsp FROM FlightSchedulePlan fsp WHERE fsp.flight.flightNumber = :inFlightNumber");
        query.setParameter("inFlightNumber", flightNumber);

        List<FlightSchedulePlan> flightSchedulePlans = query.getResultList();

        if (!flightSchedulePlans.isEmpty()) {
            for (FlightSchedulePlan flightSchedulePlan : flightSchedulePlans) {
                flightSchedulePlan.getComplementaryReturnSchedulePlan();
                flightSchedulePlan.getFlight();
                flightSchedulePlan.getFares().size();
                flightSchedulePlan.getFlightScheduleType();
                flightSchedulePlan.getFlightSchedules().size();
            }
            return flightSchedulePlans;
        } else {
            throw new FlightSchedulePlanNotFoundException("Flight schedule plan with flight number " + flightNumber + " is not found!");
        }
    }
    
    
    
    
}
