/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Flight;
import entity.FlightSchedule;
import java.util.List;
import javax.ejb.Local;
import util.exception.AircraftConfigNotFoundException;
import util.exception.DeleteFlightException;
import util.exception.FlightExistException;
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteDisabledException;
import util.exception.FlightRouteNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author jonang
 */
@Local
public interface FlightSessionBeanLocal {

    public Flight retrieveFlightByFlightNumber(String flightNumber) throws FlightNotFoundException;

    public void updateFlight(Flight flight, String newAircraftConfigurationName) throws AircraftConfigNotFoundException;

    public List<FlightSchedule> retrieveFlightSchedulesByFlightNumber(String flightNumber) throws FlightNotFoundException;

    public void deleteFlight(Flight flight) throws FlightNotFoundException, DeleteFlightException;

    public Flight retrieveFlightById(Long flightId) throws FlightNotFoundException;

    public List<Flight> retrieveAllFlights();

    public void associateComplementaryFlight(Long flightId, Long complementaryFlightId);

    public Long createNewFlight(Flight newFlight, String originAirportIATACode, String destinationAirportIATACode, String aircraftConfigurationName) throws FlightExistException, GeneralException, FlightRouteNotFoundException, AircraftConfigNotFoundException, FlightRouteDisabledException;
    
}
