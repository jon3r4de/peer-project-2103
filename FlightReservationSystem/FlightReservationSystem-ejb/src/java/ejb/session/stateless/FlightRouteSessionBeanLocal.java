/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightRoute;
import java.util.List;
import javax.ejb.Local;
import util.exception.AirportNotFoundException;
import util.exception.DeleteFlightRouteException;
import util.exception.FlightRouteExistException;
import util.exception.FlightRouteNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author jonang
 */
@Local
public interface FlightRouteSessionBeanLocal {

    public Long createNewFlightRoute(String originIataCode, String destinationIataCode) throws FlightRouteExistException, GeneralException, AirportNotFoundException;
    public void associateComplementaryFlightRoute(Long newFlightRouteId, Long newComplementaryFlightRouteId) throws FlightRouteNotFoundException;

    public Long deleteFlightRoute(String originCode, String destinationCode) throws FlightRouteNotFoundException, DeleteFlightRouteException;

    public FlightRoute retrieveFlightRouteByOdPair(String originCode, String destinationCode) throws FlightRouteNotFoundException;

    public List<FlightRoute> retrieveAllFlightRoutes();

    public FlightRoute retrieveFlightRouteById(Long flightRouteId) throws FlightRouteNotFoundException;

    public Boolean checkIfComplementaryFlightRouteExist(String originCode, String destinationCode) throws FlightRouteNotFoundException;
}
