/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.FlightSchedule;
import entity.Reservation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.FlightScheduleNotFoundException;

/**
 *
 * @author tristan
 */
@Stateless
public class FlightScheduleSessionBean implements FlightScheduleSessionBeanRemote, FlightScheduleSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public List<Reservation> viewReservations(FlightSchedule flightSchedule) {
        Long fsId = flightSchedule.getFlightScheduleId();
        FlightSchedule managedFs = em.find(FlightSchedule.class, fsId);
        
        Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.flightSchedule = :flightSchedule");
        query.setParameter("flightSchedule", managedFs);
        
        return query.getResultList();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
