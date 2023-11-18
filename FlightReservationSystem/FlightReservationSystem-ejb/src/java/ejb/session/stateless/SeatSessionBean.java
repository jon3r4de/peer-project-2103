/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Passenger;
import entity.Seat;
import entity.SeatInventory;
import enumeration.SeatStatusEnum;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
            
            //System.out.println("todays daily bean 11");
            
            seat.setSeatInventory(temp);
            //cabinClass.getSeats().add(seat);
            em.persist(seat);
            em.flush();
            //System.out.println("todays daily bean 12");
            return seat.getSeatId();
        }
    
    @Override
    public void bookSeat(Seat seat, Passenger passenger) {
        Seat managedSeat = em.find(Seat.class, seat.getSeatId());
        managedSeat.setPassenger(passenger);
        managedSeat.setSeatStatus(SeatStatusEnum.RESERVED);
        
        em.flush();
    }
        
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
