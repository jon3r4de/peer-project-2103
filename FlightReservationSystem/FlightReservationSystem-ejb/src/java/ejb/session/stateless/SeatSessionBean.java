/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.CabinClass;
import entity.Seat;
import entity.SeatInventory;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author tristan
 */
@Stateless
public class SeatSessionBean implements SeatSessionBeanRemote, SeatSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    public Long createSeat(Seat seat, SeatInventory seatInventory) {
            SeatInventory temp = em.find(SeatInventory.class, seatInventory.getSeatInventoryId());
            em.persist(seat);
            em.flush();
            
            seat.setSeatInventory(seatInventory);
            //cabinClass.getSeats().add(seat);
        
            return seat.getSeatId();
        }
        
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
