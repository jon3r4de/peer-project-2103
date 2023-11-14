/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import entity.Passenger;
import entity.Reservation;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import util.exception.NoAvailableSeatsException;

/**
 *
 * @author jonang
 */
@Remote
public interface ReservationSessionBeanRemote {
    public Reservation retrieveReservationById(Long reservationId);
    public Long reserveFlight(Integer numOfPassengers, List<Passenger> passengers, List<String> creditCard, List<Long> flightScheduleIds, List<Long> returnFlightScheduleIds, String departureAirportiATACode, String destinationAirportiATACode, Date departureDate, Date returnDate, Customer customer) throws NoAvailableSeatsException;
    
}
