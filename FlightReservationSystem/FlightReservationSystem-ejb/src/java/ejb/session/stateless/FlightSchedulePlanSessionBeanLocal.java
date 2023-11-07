/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightSchedulePlan;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import util.exception.DeleteFlightSchedulePlanException;
import util.exception.FlightSchedulePlanExistException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author tristan
 */
@Local
public interface FlightSchedulePlanSessionBeanLocal {
    public Long createNewSingleFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long flightId, Date departureDateTime, Date estimatedFlightDuration) throws FlightSchedulePlanExistException, GeneralException; 
    public Long createNewMultipleFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long flightId, List<Date> departureDateTimes, List<Date> estimatedFlightDurations) throws FlightSchedulePlanExistException, GeneralException; 
    public Long createNewRecurrentFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long flightId, Date departureDateTime, Date estimatedFlightDuration, Date endDate, int recurrence) throws FlightSchedulePlanExistException, GeneralException;
    public List<FlightSchedulePlan> retrieveAllFlightSchedulePlans();
    public void deleteFlightSchedulePlan(FlightSchedulePlan flightSchedulePlan) throws FlightSchedulePlanNotFoundException, DeleteFlightSchedulePlanException;
    public void setReturnFlightSchedulePlan(Long newFlightSchedulePlanId, Long returnFlightSchedulePlanId) throws GeneralException;
}
