/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Seat;
import entity.SeatInventory;
import javax.ejb.Local;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author tristan
 */
@Local
public interface SeatSessionBeanLocal {
    public Long createSeat(Seat seat, SeatInventory seatInventory);
}
