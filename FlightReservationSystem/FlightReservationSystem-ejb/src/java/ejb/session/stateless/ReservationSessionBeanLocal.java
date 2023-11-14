/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Reservation;
import javax.ejb.Local;

/**
 *
 * @author jonang
 */
@Local
public interface ReservationSessionBeanLocal {
    public Reservation retrieveReservationById(Long reservationId);
}
