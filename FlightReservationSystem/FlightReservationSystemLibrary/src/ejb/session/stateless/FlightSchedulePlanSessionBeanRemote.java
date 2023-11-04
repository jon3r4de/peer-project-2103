/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightSchedulePlan;
import java.util.Date;
import javax.ejb.Remote;
import util.exception.FlightSchedulePlanExistException;
import util.exception.GeneralException;

/**
 *
 * @author tristan
 */
@Remote
public interface FlightSchedulePlanSessionBeanRemote {
    public Long createNewSingleFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long flightId, Date departureDateTime, Date estimatedFlightDuration) throws FlightSchedulePlanExistException, GeneralException; 
}
