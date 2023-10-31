/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsmanagementclient;

import java.util.Scanner;

/**
 *
 * @author jonang
 */
public class FlightPlanningModule {
    
    public void fleetManagerFlightPlanningModule() {
        Scanner scanner = new Scanner(System.in);
        int response = 0;

        while (true) {
            System.out.println("*** FRS Management :: Flight Planning Module ***\n");
            System.out.println("1: Create Aircraft Configuration");
            System.out.println("2: View All Aircraft Configurations");
            System.out.println("3: View Aircraft Configuration Details");
            System.out.println("4: Logout\n");

            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                switch (response) {
                    case 1:
                        createAircraftConfig();
                        break;
                    case 2:
                        viewAllAircraftConfig();
                        break;
                    case 3:
                        viewAircraftConfigDetails();
                        break;
                    case 4:
                        System.out.println("Exiting the application.");
                        return; // Exiting the switch after handling exit
                    default:
                        System.out.println("Invalid option, please try again!\n");
                        break;
                }
            }

            /*if (response == 11) {
                break;
            }*/
        }
    }
    //
    public void createAircraftConfig() {
        
    }
    
    public void viewAllAircraftConfig() {
        
    }
    
    public void viewAircraftConfigDetails() {
        
    }
    
    
    
    public void routePlannerFlightPlanningModule() {
        Scanner scanner = new Scanner(System.in);
        int response = 0;

        while (true) {
            System.out.println("*** FRS Management :: Flight Planning Module ***\n");
            System.out.println("1: Create flight route");
            System.out.println("2: View All flight routes");
            System.out.println("3: Delete flight routes");
            System.out.println("4: Logout\n");

            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                switch (response) {
                    case 1:
                        createFlightRoutes();
                        break;
                    case 2:
                        viewAllFlightRoutes();
                        break;
                    case 3:
                        deleteFlightRoutes();
                        break;
                    case 4:
                        System.out.println("Exiting the application.");
                        return; // Exiting the switch after handling exit
                    default:
                        System.out.println("Invalid option, please try again!\n");
                        break;
                }
            }

            /*if (response == 11) {
                break;
            }*/
        }
    }
    
    //
    public void createFlightRoutes() {
        
    }
    
    public void viewAllFlightRoutes() {
        
    }
    
    public void deleteFlightRoutes() {
        
    }
    
    
}
