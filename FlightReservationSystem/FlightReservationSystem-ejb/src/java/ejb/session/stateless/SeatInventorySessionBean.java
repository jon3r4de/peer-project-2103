/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.CabinClass;
import entity.FlightSchedule;
import entity.SeatInventory;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jonang
 */
@Stateless
public class SeatInventorySessionBean implements SeatInventorySessionBeanRemote, SeatInventorySessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;
    
    @EJB
    private AircraftconfigSessionBeanLocal aircraftconfigSessionBeanLocal;
    
    @EJB
    private SeatInventorySessionBeanLocal seatInventorySessionBeanLocal;

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void createSeatInventory(FlightSchedule flightSchedule) {
        flightSchedule = em.find(FlightSchedule.class, flightSchedule.getFlightScheduleId());
        flightSchedule.getFlightSchedulePlan().getFlight().getAirCraftConfig().getCabinClasses().size();
        List<CabinClass> cabinclasses = flightSchedule.getFlightSchedulePlan().getFlight().getAirCraftConfig().getCabinClasses();
        
        //System.out.println("todays daily bean 5");
        for (CabinClass c : cabinclasses) {
            //System.out.println("todays daily bean 6");
            SeatInventory seatInventory = new SeatInventory(c, flightSchedule, c.getMaxSeatCapacity());
            em.persist(seatInventory);
            em.flush(); 
            //System.out.println("todays daily bean 7");
            aircraftconfigSessionBeanLocal.generateSeats(c, seatInventory);
            flightSchedule.getSeatInventories().add(seatInventory);
            em.flush();
        }
        
    }
    
    @Override
    public SeatInventory searchForSeatInvenotry(SeatInventory seatInventory) {
        SeatInventory managedSeatInventory = em.find(SeatInventory.class, seatInventory.getSeatInventoryId());
       
        //lazy loading
        managedSeatInventory.getSeats().size();
        
        return managedSeatInventory;
    }
    
    @Override
    public SeatInventory adjustSeatCapacity(SeatInventory seatInventory) {
        SeatInventory managedSeatInventory = this.searchForSeatInvenotry(seatInventory);
        
        int tempReserved = managedSeatInventory.getNumberOfReservedSeats() + 1;
        managedSeatInventory.setNumberOfReservedSeats(tempReserved);
                
        int tempAvailable = managedSeatInventory.getNumberOfAvailableSeats() - 1;
        managedSeatInventory.setNumberOfAvailableSeats(tempAvailable);
                
        int tempBalance = managedSeatInventory.getNumberOfBalanceSeats() - 1;
        managedSeatInventory.setNumberOfBalanceSeats(tempBalance);
                
        em.flush();

        return managedSeatInventory;
    }
    
    
    
}
