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
    
    public void setReturnFlightSchedulePlan(Long newFlightSchedulePlanId, Long returnFlightSchedulePlanId) throws GeneralException {
        try {
            FlightSchedulePlan newFlightSchedulePlan = em.find(FlightSchedulePlan.class, newFlightSchedulePlanId);
            FlightSchedulePlan returnFlightSchedulePlan = em.find(FlightSchedulePlan.class, returnFlightSchedulePlanId);

            returnFlightSchedulePlan.setComplementaryReturnSchedulePlan(newFlightSchedulePlan);
            newFlightSchedulePlan.setComplementaryReturnSchedulePlan(returnFlightSchedulePlan);
            em.merge(returnFlightSchedulePlan);
            em.merge(newFlightSchedulePlan);
            
            System.out.println("Return flight schedule plan updated to set complementary return plan.");
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage());
        }
    }

    
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
}
