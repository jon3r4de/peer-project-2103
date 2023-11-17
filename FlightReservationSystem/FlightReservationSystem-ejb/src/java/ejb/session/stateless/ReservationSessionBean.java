/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.CabinClass;
import entity.Customer;
import entity.FlightSchedule;
import entity.Passenger;
import entity.Reservation;
import entity.SeatInventory;
import enumeration.CabinClassEnum;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.NoAvailableSeatsException;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author jonang
 */
@Stateless
public class ReservationSessionBean implements ReservationSessionBeanRemote, ReservationSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;
    
//    @Override
//    public Long reserveFlight(Integer numOfPassengers, List<Passenger> passengers, List<String> creditCard, 
//            List<Long> flightScheduleIds, List<Long> returnFlightScheduleIds, String departureAirportiATACode, 
//            String destinationAirportiATACode, Date departureDate, Date returnDate, Customer customer) throws NoAvailableSeatsException {
//
//        Reservation flightReservation = new Reservation(numOfPassengers, passengers, creditCard, departureAirportiATACode, destinationAirportiATACode, departureDate, returnDate, customer);
//        em.persist(flightReservation);
//        for (Long flightScheduleId : flightScheduleIds) {
//            FlightSchedule flightSchedule = em.find(FlightSchedule.class, flightScheduleId);
//
//            for (Passenger passenger : passengers) {
//                CabinClassEnum cabinClassType;
//                if (passenger.getCabinClass().charAt(0) == '1') {
//                    cabinClassType = CabinClassEnum.FIRST;
//                } else if (passenger.getCabinClass().charAt(0) == '2') {
//                    cabinClassType = CabinClassEnum.BUSINESS;
//                } else if (passenger.getCabinClass().charAt(0) == '3') {
//                    cabinClassType = CabinClassEnum.PREMIUMECONOMY;
//                } else {
//                    cabinClassType = CabinClassEnum.ECONOMY;
//                }
//
//                for (SeatInventory si : flightSchedule.getSeatInventories()) {
//                    if (si.getCabinClass().equals(cabinClassType)) {
//                        if (si.getNumberOfAvailableSeats() == 0) {
//                            throw new NoAvailableSeatsException("The chosen class does not have enough seats for the reservation, please choose another one!");
//                        } else {
//                            si.setNumberOfReservedSeats(si.getNumberOfReservedSeats() + 1);
//                        }
//                    }
//                }
//                
//                /*for (CabinClass cabinClass : flightSchedule.getSeatInventories()) {
//                    if (cabinClass.equals(cabinClassType)) {
//                        if (cabinClass.getAvailableSeats() == 0) {
//                            throw new NoAvailableSeatsException("The chosen class does not have enough seats for the reservation, please choose another one!");
//                        } else {
//                            cabinClass.setReservedSeats(cabinClass.getReservedSeats() + 1);
//                        }
//                    }
//                }*/
//            }
//            
//            flightSchedule.getReservations().add(flightReservation);
//            flightReservation.getFlightSchedules().add(flightSchedule);//add(flightSchedule);
//            //flightReservation.getFlightSchedules().add(flightSchedule);
//        }
//
//        if (!returnFlightScheduleIds.isEmpty()) {
//            for (Long returnFlightScheduleId : returnFlightScheduleIds) {
//                FlightSchedule returnFlightSchedule = em.find(FlightSchedule.class, returnFlightScheduleId);
//
//                    for (Passenger passenger : passengers) {
//                        CabinClassEnum cabinClassType;
//                        if (passenger.getCabinClass().charAt(0) == '1') {
//                            cabinClassType = CabinClassEnum.FIRST;
//                        } else if (passenger.getCabinClass().charAt(0) == '2') {
//                            cabinClassType = CabinClassEnum.BUSINESS;
//                        } else if (passenger.getCabinClass().charAt(0) == '3') {
//                            cabinClassType = CabinClassEnum.PREMIUMECONOMY;
//                        } else {
//                            cabinClassType = CabinClassEnum.ECONOMY;
//                        }
//                        
//                        for (SeatInventory si : returnFlightSchedule.getSeatInventories()) {
//                    if (si.getCabinClass().equals(cabinClassType)) {
//                        if (si.getNumberOfAvailableSeats() == 0) {
//                            throw new NoAvailableSeatsException("The chosen class does not have enough seats for the reservation, please choose another one!");
//                        } else {
//                            si.setNumberOfReservedSeats(si.getNumberOfReservedSeats() + 1);
//                        }
//                    }
//                }
//
//                    /*for (CabinClass cabinClass : returnFlightSchedule.getCabinClasses()) {
//                        if (cabinClass.equals(cabinClassType)) {
//                            if (cabinClass.getBalanceSeats() == 0) {
//                                throw new NoAvailableSeatsException("The chosen cabin class does not have enough seats for the reservation, please choose another one!");
//                            } else {
//                                cabinClass.setReservedSeats(cabinClass.getReservedSeats() + 1);
//                            }
//                        }
//                    }*/
//                }
//                returnFlightSchedule.getReservations().add(flightReservation);
//               // flightReservation.getReturnFlightSchedules().add(returnFlightSchedule);
//            }
//        }
//        
//        em.flush();
//        
//        return flightReservation.getReservationId();
//    }
    
    //Reservation reservation = new Reservation(temp, numOfPassengers, creditCard); what reservation has 
    //what reservation lacking :and customer, passenger list
    @Override
    public Reservation reserveFlight(Integer numOfPassengers, List<Passenger> passengers, List<String> creditCard, FlightSchedule flightSchedule, Customer customer, Reservation reservation) {
        FlightSchedule managedFlightSchedule = em.find(FlightSchedule.class, flightSchedule.getFlightScheduleId());
        System.out.println(customer);
        System.out.println(customer.getCustomerId());
        Customer managedCustomer = em.find(Customer.class, customer.getCustomerId());
        //managedFlightSchedule.getFlightSchedulePlan().getFares().
        reservation.setPassengerList(passengers);
        reservation.setCustomer(managedCustomer);
        reservation.getFlightSchedules().add(managedFlightSchedule);
        em.persist(reservation);
        em.flush();
        managedFlightSchedule.getReservations().add(reservation);
        managedCustomer.getReservations().add(reservation);
        
        //assosciate passenger with the reservation
        for(Passenger p : passengers) {
            Passenger managedP = em.find(Passenger.class, p.getPassengerId());
            managedP.setReservation(reservation);
        }
        
        reservation.setDepartureDate(managedFlightSchedule.getDepartureDateTime());   
        reservation.setFlightNumber(managedFlightSchedule.getFlightSchedulePlan().getFlight().getFlightNumber());
        reservation.setDestinationAirport(managedFlightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getIataAirportcode());
        reservation.setDepartureAirport(managedFlightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getOrigin().getIataAirportcode());
        
        return reservation;
    }
    
    
    public Reservation retrieveReservationByID(Long reservationId) throws ReservationNotFoundException {
        Reservation reservation = em.find(Reservation.class, reservationId);
        
        if (reservation == null) {
            throw new ReservationNotFoundException("Flight Reservation with ID: " + reservationId + " does not exist!");
        }
        
        reservation.getCreditCardInfo().size();
        reservation.getFlightSchedules().size();
        reservation.getPassengerList().size();
        reservation.getPassengers().size();

        return reservation;
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
