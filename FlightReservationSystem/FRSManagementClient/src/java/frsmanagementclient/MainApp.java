/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.AircraftconfigSessionBeanRemote;
import ejb.session.stateless.CabinClassSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.FlightRouteSessionBeanRemote;
import ejb.session.stateless.FlightSchedulePlanSessionBeanRemote;
import ejb.session.stateless.FlightScheduleSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import ejb.session.stateless.aircraftTypeSessionBeanRemote;
import entity.Employee;
import enumeration.EmployeeEnum;
import java.util.Scanner;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author jonang
 */
public class MainApp {
    
    private Employee employee;
    
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;
    
    private aircraftTypeSessionBeanRemote aircraftTypeSessionBeanRemote;
    
    private AircraftconfigSessionBeanRemote aircraftconfigSessionBeanRemote;
    
    private FlightRouteSessionBeanRemote flightRouteSessionBeanRemote;
    
    private FlightSessionBeanRemote flightSessionBeanRemote;
    
    private FlightScheduleSessionBeanRemote flightScheduleSessionBeanRemote;
    
    private FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote;
    
    private CabinClassSessionBeanRemote cabinClassSessionBeanRemote;
    
    public MainApp(EmployeeSessionBeanRemote employeeSessionBeanRemote, aircraftTypeSessionBeanRemote aircraftTypeSessionBeanRemote, AircraftconfigSessionBeanRemote aircraftconfigSessionBeanRemote,
            FlightRouteSessionBeanRemote flightRouteSessionBeanRemote, FlightSessionBeanRemote flightSessionBeanRemote, 
            FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote, CabinClassSessionBeanRemote cabinClassSessionBeanRemote,
            FlightScheduleSessionBeanRemote flightScheduleSessionBeanRemote) {
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
        this.aircraftTypeSessionBeanRemote = aircraftTypeSessionBeanRemote;
        this.aircraftconfigSessionBeanRemote = aircraftconfigSessionBeanRemote;
        this.flightRouteSessionBeanRemote = flightRouteSessionBeanRemote;
        this.flightSessionBeanRemote = flightSessionBeanRemote;
        this.flightSchedulePlanSessionBeanRemote = flightSchedulePlanSessionBeanRemote;
        this.cabinClassSessionBeanRemote = cabinClassSessionBeanRemote;
        this.flightScheduleSessionBeanRemote = flightScheduleSessionBeanRemote;
    }
    
    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        int response;
        
        while (true) {
            System.out.println("*** Welcome to FRS Management Client System ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");

            response = 0;

            while (response < 1 || response > 2) {
                System.out.print("> ");

                if (scanner.hasNextInt()) {
                    response = scanner.nextInt();

                    if (response == 1) {
                        
                        System.out.println("*** FRS Management :: Employee Login ***\n");
                        System.out.print("Enter Username> ");
                        scanner.nextLine(); // Consume the newline character
                        String username = scanner.nextLine().trim();
                        System.out.print("Enter Password> ");
                        String password = scanner.nextLine().trim();
                           
                        try {
                            this.employee = this.employeeSessionBeanRemote.doLogin(username, password);
                        } catch (InvalidLoginCredentialException ex) {
                            System.out.println(ex.getMessage() + "\n");
                            break;
                        }
                        
                        System.out.println("Login successful!\n");
                        this.logInView();
                            
                    } else if (response == 2) {
                        System.out.println("Exiting the application.");
                        break;
                    } else {
                        System.out.println("Invalid option, please try again!");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Clear the invalid input
                }
            }

            if (response == 2) {
                break;
            }
        }
    }


    public void logInView() {
        Scanner scanner = new Scanner(System.in);
        int response;
        
        while (true) {
            System.out.println("*** Welcome to FRS Management Client System, " + this.employee.getUsername() + ", what would you like to do today? ***\n");
            System.out.println("1: Hop into module");
            System.out.println("2: Logout\n");

            response = 0;

            while (response < 1 || response > 2) {
                System.out.print("> ");

                if (scanner.hasNextInt()) {
                    response = scanner.nextInt();

                    if (response == 1) {

                        if (employee.getUserRole().equals(EmployeeEnum.FLEETMANAGER)) {
                            FlightPlanningModule flightPlanningModule = new FlightPlanningModule (aircraftTypeSessionBeanRemote, aircraftconfigSessionBeanRemote, flightRouteSessionBeanRemote, cabinClassSessionBeanRemote);
                            flightPlanningModule.fleetManagerFlightPlanningModule();
                        } else if (employee.getUserRole().equals(EmployeeEnum.ROUTEPLANNER)) {
                            FlightPlanningModule flightPlanningModule = new FlightPlanningModule (aircraftTypeSessionBeanRemote, aircraftconfigSessionBeanRemote, flightRouteSessionBeanRemote, cabinClassSessionBeanRemote);
                            flightPlanningModule.routePlannerFlightPlanningModule();
                        } else if (employee.getUserRole().equals(EmployeeEnum.SCHEDULEMANAGER)) {
                            FlightOperationModule flightOperationModule = new FlightOperationModule(flightSessionBeanRemote, flightRouteSessionBeanRemote, flightSchedulePlanSessionBeanRemote);
                            flightOperationModule.menuFlightOperation();
                        } else if (employee.getUserRole().equals(EmployeeEnum.SALESMANAGER)) {
                            SalesManagementModule salesManagementModule = new SalesManagementModule(flightSessionBeanRemote, flightScheduleSessionBeanRemote);
                            salesManagementModule.menuSalesManagement();
                        }
                    } else if (response == 2) {
                        System.out.println("You are logging out.");
                        this.doLogOut();
                        break;
                    } else {
                        System.out.println("Invalid option, please try again!");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Clear the invalid input
                }
            }

            if (response == 2) {
                break;
            }
        }

        
    }
    
    public void doLogOut() {
        this.employee = null;
        System.out.println("You are logged out, See you Again!");
    }
    
}
