/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightSchedule;
import entity.Reservation;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author tristan
 */
@Remote
public interface FlightScheduleSessionBeanRemote {
    public List<Reservation> viewReservations(FlightSchedule flightSchedule);
}
