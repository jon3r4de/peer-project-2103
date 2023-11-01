/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.EmployeeSessionBeanRemote;
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
    
    public MainApp(EmployeeSessionBeanRemote employeeSessionBeanRemote) {
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
    }
    
    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        int response;
        
        while (true) {
            System.out.println("*** Welcome to frs management client system ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");

            response = 0;

            while (response < 1 || response > 2) {
                System.out.print("> ");

                if (scanner.hasNextInt()) {
                    response = scanner.nextInt();

                    if (response == 1) {
                        
                        System.out.println("*** FRS Management :: Employee Login ***\n");
                        System.out.print("Enter username> ");
                        scanner.nextLine(); // Consume the newline character
                        String username = scanner.nextLine().trim();
                        System.out.print("Enter password> ");
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
            System.out.println("*** Welcome to frs management client system " + this.employee.getUsername() + ", what would you like to do today? ***\n");
            System.out.println("1: Hop into module");
            System.out.println("2: Logout\n");

            response = 0;

            while (response < 1 || response > 2) {
                System.out.print("> ");

                if (scanner.hasNextInt()) {
                    response = scanner.nextInt();

                    if (response == 1) {

                        if (employee.getUserRole().equals(EmployeeEnum.FLEETMANAGER)) {
                            /*FlightPlanningModule flightPlanningModule = new FlightPlanningModule (aircraftConfigurationSessionBeanRemote, airportSessionBeanRemote,
                                    flightRouteSessionBeanRemote, aircraftTypeSessionBeanRemote, cabinClassSessionBeanRemote, employee);
                            flightPlanningModule.menuFlightPlanning();*/
                        } else if (employee.getUserRole().equals(EmployeeEnum.ROUTEPLANNER)) {
                            /*FlightPlanningModule flightPlanningModule = new FlightPlanningModule (aircraftConfigurationSessionBeanRemote, airportSessionBeanRemote,
                                    flightRouteSessionBeanRemote, aircraftTypeSessionBeanRemote, cabinClassSessionBeanRemote, employee);
                            flightPlanningModule.menuFlightPlanning();*/
                        } else if (employee.getUserRole().equals(EmployeeEnum.SCHEDULEMANAGER)) {
                            /*FlightOperationModule flightOperationModule = new FlightOperationModule(flightSessionBeanRemote, flightRouteSessionBeanRemote,
                                    aircraftConfigurationSessionBeanRemote, flightScheduleSessionBeanRemote, flightSchedulePlanSessionBeanRemote, employee);
                            flightOperationModule.menuFlightOperation();*/
                        } else if (employee.getUserRole().equals(EmployeeEnum.SALESMANAGER)) {
                            /*SalesManagementModule salesManagementModule = new SalesManagementModule(flightSessionBeanRemote, flightScheduleSessionBeanRemote, employee);
                            salesManagementModule.menuSalesManagement();*/
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
