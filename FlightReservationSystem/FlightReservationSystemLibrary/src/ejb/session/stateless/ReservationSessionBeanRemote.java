/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import entity.FlightSchedule;
import entity.Passenger;
import entity.Reservation;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import util.exception.NoAvailableSeatsException;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author jonang
 */
@Remote
public interface ReservationSessionBeanRemote {
  //  public Long reserveFlight(Integer numOfPassengers, List<Passenger> passengers, List<String> creditCard, List<Long> flightScheduleIds, List<Long> returnFlightScheduleIds, String departureAirportiATACode, String destinationAirportiATACode, Date departureDate, Date returnDate, Customer customer) throws NoAvailableSeatsException;

    public Reservation reserveFlight(Integer numOfPassengers, List<Passenger> passengers, List<String> creditCard, FlightSchedule flightSchedule, Customer customer, Reservation reservation);

    public Reservation retrieveReservationByID(Long reservationId) throws ReservationNotFoundException;
    
}
