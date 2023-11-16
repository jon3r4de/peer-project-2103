/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package frsreservationclient;

import ejb.session.stateless.CabinClassSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.FlightSchedulePlanSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import ejb.session.stateless.FlightScheduleSessionBeanRemote;
import ejb.session.stateless.PassengerSessionBeanRemote;
import ejb.session.stateless.ReservationSessionBeanRemote;
import ejb.session.stateless.SeatSessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author jonang
 */
public class Main {

    @EJB
    private static FlightSessionBeanRemote flightSessionBeanRemote;

    /**
     * @param args the command line arguments
     */
    @EJB
    private static CustomerSessionBeanRemote customerSessionBeanRemote;
    
    @EJB
    private static ReservationSessionBeanRemote reservationSessionBeanRemote;
    
    @EJB
    private static FlightScheduleSessionBeanRemote flightScheduleSessionBeanRemote;
    
    @EJB
    private static CabinClassSessionBeanRemote cabinClassSessionBeanRemote;
    
    @EJB 
    private static FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote;
    
    @EJB
    private static PassengerSessionBeanRemote passengerSessionBeanRemote;
    
    @EJB
    private static SeatSessionBeanRemote seatSessionBeanRemote;
    

    
    public static void main(String[] args) {
        // TODO code application logic here
         MainApp mainApp = new MainApp(customerSessionBeanRemote, reservationSessionBeanRemote, 
             flightScheduleSessionBeanRemote,  flightSessionBeanRemote, 
            cabinClassSessionBeanRemote,  flightSchedulePlanSessionBeanRemote, 
             passengerSessionBeanRemote, seatSessionBeanRemote);

        
        System.out.println("deployed2");
        mainApp.runApp();
    }
    
}
