/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightSchedule;
import enumeration.CabinClassEnum;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import util.exception.AirportNotFoundException;
import util.exception.FlightScheduleNotFoundException;

/**
 *
 * @author tristan
 */
@Local
public interface FlightScheduleSessionBeanLocal {
    public List<FlightSchedule> searchDirectFlightSchedules(String departureAirportiATACode, String destinationAirportiATACode, Date departureDate, CabinClassEnum cabinClassType) throws AirportNotFoundException, FlightScheduleNotFoundException;
}
