/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Fare;
import entity.Flight;
import entity.FlightSchedulePlan;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tristan
 */
@Stateless
public class FlightSchedulePlanSessionBean implements FlightSchedulePlanSessionBeanRemote, FlightSchedulePlanSessionBeanLocal {
    
    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public Long createNewSingleFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long flightId, Date departureDate, Date departureTime, Date estimatedFlightDuration) {
        
        em.persist(newFlightSchedulePlan);
        
        // link flight and flightscheduleplan
        Flight flight = em.find(Flight.class, flightId);
        newFlightSchedulePlan.setFlight(flight);
        flight.getFlightSchedulePlans().add(newFlightSchedulePlan);
        
        // link fare and flightscheduleplan
        for (Fare fare : newFlightSchedulePlan.getFares()) {
            fare.setFlightSchedulePlan(newFlightSchedulePlan);
        }
        
        FlightSchedule newFlightSchedule = new FlightSchedule(departureDate, departureTime, estimatedFlightDuration, )
    }
}
