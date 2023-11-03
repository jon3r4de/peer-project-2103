/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.AircraftconfigSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.FlightRouteSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import ejb.session.stateless.aircraftTypeSessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author jonang
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    @EJB
    private static EmployeeSessionBeanRemote employeeSessionBeanRemote;
    
    @EJB
    private static aircraftTypeSessionBeanRemote aircraftTypeSessionBeanRemote;
    
    @EJB
    private static FlightRouteSessionBeanRemote flightRouteSessionBeanRemote;
    
    @EJB
    private static AircraftconfigSessionBeanRemote aircraftconfigSessionBeanRemote;
    
    @EJB 
    private static FlightSessionBeanRemote flightSessionBeanRemote;
    
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("deployed1");
         MainApp mainApp = new MainApp(employeeSessionBeanRemote, aircraftTypeSessionBeanRemote, aircraftconfigSessionBeanRemote, flightRouteSessionBeanRemote, flightSessionBeanRemote);
        
        System.out.println("deployed2");
        mainApp.runApp();
    }
    
}
