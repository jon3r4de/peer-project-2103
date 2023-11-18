/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import java.text.ParseException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import util.exception.DeleteFlightSchedulePlanException;
import util.exception.FlightSchedulePlanExistException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.GeneralException;
import util.exception.UpdateFlightSchedulePlanException;

/**
 *
 * @author tristan
 */
@Local
public interface FlightSchedulePlanSessionBeanLocal {
    public Long createNewSingleFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long flightId, Date departureDateTime, int estimatedFlightDurationHours, int estimatedFlightDurationMinutes) throws FlightSchedulePlanExistException, GeneralException;

    public Long createNewMultipleFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long flightId, List<Date> departureDateTimes, List<Integer> listEstimatedFlightDurationHours, List<Integer> listEstimatedFlightDurationMinutes) throws FlightSchedulePlanExistException, GeneralException;

    public Long createNewRecurrentFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long flightId, Date departureDateTime, int estimatedFlightDurationHours, int estimatedFlightDurationMinutes, Date endDate, int recurrence, String startDay, Duration layoverDuration) throws FlightSchedulePlanExistException, GeneralException;
    public void updateSingleFlightSchedule(FlightSchedulePlan flightSchedulePlan, FlightSchedule flightSchedule, Date departureDate, Date departureTime, int estimatedDurationTimeHours, int estimatedDurationTimeMinutes) throws UpdateFlightSchedulePlanException, ParseException;

    public void updateRecurrentWeekFlightSchedule(FlightSchedulePlan flightSchedulePlan, Date endDate) throws UpdateFlightSchedulePlanException;

    public List<FlightSchedulePlan> retrieveFlightSchedulePlansByFlightNumber(String flightNumber) throws FlightSchedulePlanNotFoundException;
    public void updateFlightSchedulePlanName(String name, FlightSchedulePlan fsp);
}
