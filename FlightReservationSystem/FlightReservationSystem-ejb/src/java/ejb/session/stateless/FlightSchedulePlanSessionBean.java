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
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
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
    
    @EJB
    private SeatInventorySessionBeanLocal seatInventorySessionBeanLocal;

    
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
    public Long createNewSingleFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long flightId, Date departureDateTime, int estimatedFlightDurationHours, int estimatedFlightDurationMinutes) throws FlightSchedulePlanExistException, GeneralException {
        try {
            em.persist(newFlightSchedulePlan);

            // link flight and flightscheduleplan
            Flight flight = em.find(Flight.class, flightId);
            newFlightSchedulePlan.setFlight(flight);
            flight.getFlightSchedulePlans().add(newFlightSchedulePlan);
            
            newFlightSchedulePlan.setFlightNumber(flight.getFlightNumber());
            
            // link fare and flightscheduleplan
            for (Fare fare : newFlightSchedulePlan.getFares()) {
                fare.setFlightSchedulePlan(newFlightSchedulePlan);
                //Long fareSavedId = fareSessionBeanLocal.createNewFare(fare);
                //System.out.println("Fare saved, ID: " + fareSavedId);
            }

           // Date arrivalDateTime = this.findArrivalDateTime(departureDateTime, estimatedFlightDuration);

            FlightSchedule newFlightSchedule = new FlightSchedule(departureDateTime, estimatedFlightDurationHours, estimatedFlightDurationMinutes, flight.getFlightNumber(), newFlightSchedulePlan);
            newFlightSchedule.calculateArrivalTime();
            em.persist(newFlightSchedule);
            newFlightSchedule.setFlightSchedulePlan(newFlightSchedulePlan);
            seatInventorySessionBeanLocal.createSeatInventory(newFlightSchedule);

            em.flush();
            newFlightSchedulePlan.getFlightSchedules().add(newFlightSchedule);

            em.flush();
            return newFlightSchedulePlan.getFlightSchedulePlanId();
        } catch (PersistenceException ex) {
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
    public Long createNewMultipleFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long flightId, List<Date> departureDateTimes, List<Integer> listEstimatedFlightDurationHours, List<Integer> listEstimatedFlightDurationMinutes) throws FlightSchedulePlanExistException, GeneralException {
        try {
            em.persist(newFlightSchedulePlan);
            
            // link flight and flightscheduleplan
            Flight flight = em.find(Flight.class, flightId);
            newFlightSchedulePlan.setFlight(flight);
            flight.getFlightSchedulePlans().add(newFlightSchedulePlan);
            
            newFlightSchedulePlan.setFlightNumber(flight.getFlightNumber());
            
            // link fare and flightscheduleplan
            for (Fare fare : newFlightSchedulePlan.getFares()) {
                fare.setFlightSchedulePlan(newFlightSchedulePlan);
                //Long fareSavedId = fareSessionBeanLocal.createNewFare(fare);
                //System.out.println("Fare saved, ID: " + fareSavedId);
            }
            
            for (int i = 0; i < departureDateTimes.size(); i++) {
                //Date arrivalDateTime = this.findArrivalDateTime(departureDateTimes.get(i), estimatedFlightDurations.get(i));
                FlightSchedule newFlightSchedule = new FlightSchedule(departureDateTimes.get(i), listEstimatedFlightDurationHours.get(i), listEstimatedFlightDurationMinutes.get(i),flight.getFlightNumber(), newFlightSchedulePlan);
                
                newFlightSchedule.calculateArrivalTime();
                
                em.persist(newFlightSchedule);
                newFlightSchedule.setFlightSchedulePlan(newFlightSchedulePlan);
                em.flush();
                seatInventorySessionBeanLocal.createSeatInventory(newFlightSchedule);
                
                newFlightSchedulePlan.getFlightSchedules().add(newFlightSchedule);
            }
            
            em.flush();
            return newFlightSchedulePlan.getFlightSchedulePlanId();
        } catch (PersistenceException ex) {
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
    public Long createNewRecurrentFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long flightId, Date departureDateTime, int estimatedFlightDurationHours, int estimatedFlightDurationMinutes, Date endDate, int recurrence, String startDay, Duration layoverDuration) throws FlightSchedulePlanExistException, GeneralException {
        try {
            
            em.persist(newFlightSchedulePlan);
            newFlightSchedulePlan.setRecurrence(recurrence);
            
            System.out.println("todays daily bean 1");
            Date adjustedDate = this.adjustStartDate(startDay, departureDateTime);
            System.out.println(adjustedDate);
            newFlightSchedulePlan.setLayoverDuration(layoverDuration);
            System.out.println(layoverDuration);
            newFlightSchedulePlan.setReccurrentDay(startDay);
            //System.out.println("cnrfsp debug1");
            newFlightSchedulePlan.setStartDate(adjustedDate);
            newFlightSchedulePlan.setEndDate(endDate);
            System.out.println(endDate);
            // link flight and flightscheduleplan
            //System.out.println("cnrfsp debug2");
            Flight flight = em.find(Flight.class, flightId);
            //System.out.println("cnrfsp debug3");
            newFlightSchedulePlan.setFlight(flight);
            //System.out.println("cnrfsp debug4");
            
            flight.getFlightSchedulePlans().add(newFlightSchedulePlan);
            //System.out.println("cnrfsp debug5");
            
            newFlightSchedulePlan.setFlightNumber(flight.getFlightNumber());
            
           // System.out.println("cnrfsp debug6");
            // link fare and flightscheduleplan
            for (Fare fare : newFlightSchedulePlan.getFares()) {
                fare.setFlightSchedulePlan(newFlightSchedulePlan);
//                Long fareSavedId = fareSessionBeanLocal.createNewFare(fare);
//                System.out.println("Fare saved, ID: " + fareSavedId);
            }
           // System.out.println("cnrfsp debug7");
            
            Date tempDate = new Date(adjustedDate.getTime());
            
            System.out.println("initial tempdate " + tempDate);
            
            em.flush();
            
            while (tempDate.getTime() <= endDate.getTime()) {
                //Date arrivalDateTime = this.findArrivalDateTime(tempDate, estimatedFlightDuration);
                //System.out.println("cnrfsp debug8");
                System.out.println(endDate.getTime());
                FlightSchedule newFlightSchedule = new FlightSchedule(tempDate, estimatedFlightDurationHours, estimatedFlightDurationMinutes, flight.getFlightNumber(), newFlightSchedulePlan);
                //System.out.println("cnrfsp debug9");
                newFlightSchedule.calculateArrivalTime();
                //System.out.println("cnrfsp debug10");
                //System.out.println("todays daily bean 2 " + recurrence);
                em.persist(newFlightSchedule);
                em.flush();
                //System.out.println("todays daily bean 3");
                seatInventorySessionBeanLocal.createSeatInventory(newFlightSchedule);

                //System.out.println("todays daily bean 4");
                //System.out.println("cnrfsp debug11");
                newFlightSchedule.setFlightSchedulePlan(newFlightSchedulePlan);
                //System.out.println("cnrfsp debug12");
                
                newFlightSchedulePlan.getFlightSchedules().add(newFlightSchedule);
                //System.out.println("cnrfsp debug13");
                // move departuredatetime of new flight to date after recurrence
                tempDate = new Date(tempDate.getTime() + (recurrence * 24 * 60 * 60 * 1000));
            }
            
            em.flush();
            return newFlightSchedulePlan.getFlightSchedulePlanId();
        } catch (PersistenceException ex) {
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
    
    private Date adjustStartDate(String startDay, Date creationDate) {
        // Specify the day of the week you want to start the first flight (Calendar.MONDAY in this example)\
        int desiredDayOfWeek;
        if (startDay.equals("Mon")) {
            desiredDayOfWeek = Calendar.MONDAY;
        } else if (startDay.equals("Tue")) {
            desiredDayOfWeek = Calendar.TUESDAY;
        } else if (startDay.equals("Wed")) {
            desiredDayOfWeek = Calendar.WEDNESDAY;
        } else if (startDay.equals("Thu")) {
            desiredDayOfWeek = Calendar.THURSDAY;
        } else if (startDay.equals("Fri")) {
            desiredDayOfWeek = Calendar.FRIDAY;
        } else if (startDay.equals("Sat")) {
            desiredDayOfWeek = Calendar.SATURDAY;
        } else {
            desiredDayOfWeek = Calendar.SUNDAY;
        }
         
        // Create a Calendar instance and set it to the creation date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(creationDate);

        // Find the next occurrence of the desired day of the week after the creation date
        while (calendar.get(Calendar.DAY_OF_WEEK) != desiredDayOfWeek) {
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }

        // Use the time from the creation date to set the time for the first flight
        Calendar creationTime = Calendar.getInstance();
        creationTime.setTime(creationDate);
        calendar.set(Calendar.HOUR_OF_DAY, creationTime.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, creationTime.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, creationTime.get(Calendar.SECOND));

        // Now 'calendar' contains the desired date and time for the first flight
        Date adjustedDate = calendar.getTime();
        System.out.println(adjustedDate);
        return adjustedDate;
    }
    
    @Override
    public void setReturnFlightSchedulePlan(Long newFlightSchedulePlanId, Long returnFlightSchedulePlanId) throws GeneralException {
        try {
            FlightSchedulePlan newFlightSchedulePlan = em.find(FlightSchedulePlan.class, newFlightSchedulePlanId);
            FlightSchedulePlan returnFlightSchedulePlan = em.find(FlightSchedulePlan.class, returnFlightSchedulePlanId);

            returnFlightSchedulePlan.setComplementaryReturnSchedulePlan(newFlightSchedulePlan);
            newFlightSchedulePlan.setComplementaryReturnSchedulePlan(returnFlightSchedulePlan);
            //em.merge(returnFlightSchedulePlan);
            //em.merge(newFlightSchedulePlan);
            
            System.out.println("Return flight schedule plan updated to set complementary return plan.");
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage());
        }
    }
    

    
    @Override
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
    
    @Override
    public void deleteFlightSchedulePlan(FlightSchedulePlan flightSchedulePlan) throws FlightSchedulePlanNotFoundException, DeleteFlightSchedulePlanException {
       
         FlightSchedulePlan flightSchedulePlanManaged = em.find(FlightSchedulePlan.class, flightSchedulePlan.getFlightSchedulePlanId());
        
        if (flightSchedulePlan.getFlightSchedules().isEmpty()) {
            flightSchedulePlanManaged.getFlight().getFlightSchedulePlans().remove(flightSchedulePlan); //remove flightSchedulePlan from the flights list of flightSchedulePlan 
            FlightSchedulePlan complementaryFlightSchedulePlan = flightSchedulePlanManaged.getComplementaryReturnSchedulePlan();
            complementaryFlightSchedulePlan.getFlight().getFlightSchedulePlans().remove(complementaryFlightSchedulePlan);
            em.remove(flightSchedulePlanManaged);
            em.remove(complementaryFlightSchedulePlan);
        } else {
            flightSchedulePlanManaged.setDisabled(true);
            throw new DeleteFlightSchedulePlanException("Flight Schedule Plan with Flight no. " + flightSchedulePlanManaged.getFlight().getFlightNumber()
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
    public void updateSingleFlightSchedule(FlightSchedulePlan flightSchedulePlan, FlightSchedule flightSchedule, Date departureDate, Date departureTime, int estimatedDurationTimeHours, int estimatedDurationTimeMinutes) throws UpdateFlightSchedulePlanException, ParseException {
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
            FlightSchedule newFlightSchedule = new FlightSchedule(departureDate, estimatedDurationTimeHours, estimatedDurationTimeMinutes,flight.getFlightNumber(),
             flightSchedulePlan);
           
            em.persist(newFlightSchedule);
            
            seatInventorySessionBeanLocal.createSeatInventory(newFlightSchedule);
            
            newFlightSchedule.setFlightSchedulePlan(flightSchedulePlan);
            flightSchedulePlan.getFlightSchedules().add(newFlightSchedule);
        }
    }
    
        @Override
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
            FlightSchedule newFlightSchedule = new FlightSchedule(departureDate, firstFlightSchedule.getEstimatedFlightDurationHours(), firstFlightSchedule.getEstimatedFlightDurationMinutes(),flight.getFlightNumber(),
                    flightSchedulePlan);
            newFlightSchedule.calculateArrivalTime();
            em.persist(newFlightSchedule);
            
            seatInventorySessionBeanLocal.createSeatInventory(newFlightSchedule);
            
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
    
    @Override
    public void updateFlightSchedulePlanName(String name, FlightSchedulePlan fsp) {
        FlightSchedulePlan managedFsp = em.find(FlightSchedulePlan.class, fsp.getFlightSchedulePlanId());
        
        managedFsp.setFlightSchedulePlanName(name);
    }
    
    @Override
    public FlightSchedulePlan retrieveById(Long id) {
        FlightSchedulePlan fsp = em.find(FlightSchedulePlan.class, id);
        fsp.getFares().size();
        fsp.getFlightSchedules().size();
        
        return fsp;
    }
}
