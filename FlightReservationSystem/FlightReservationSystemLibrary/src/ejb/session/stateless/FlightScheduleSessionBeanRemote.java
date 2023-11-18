/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightSchedule;
import entity.Reservation;
import enumeration.CabinClassEnum;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import util.exception.AirportNotFoundException;
import util.exception.FlightScheduleNotFoundException;

/**
 *
 * @author tristan
 */
@Remote
public interface FlightScheduleSessionBeanRemote {
    public List<Reservation> viewReservations(FlightSchedule flightSchedule);

    public List<FlightSchedule> searchDirectFlightSchedules(String departureAirportiATACode, String destinationAirportiATACode, Date departureDate, CabinClassEnum cabinClassType) throws AirportNotFoundException, FlightScheduleNotFoundException;

    public List<List<FlightSchedule>> searchConnectingFlightScehdules(String departureAirportiATACode, String destinationAirportiATACode, Date departureDate, CabinClassEnum cabinClassType) throws AirportNotFoundException, FlightScheduleNotFoundException;

    public FlightSchedule searchGeneralFlightSchedule(FlightSchedule fs);
}
