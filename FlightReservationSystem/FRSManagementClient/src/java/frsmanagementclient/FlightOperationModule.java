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
public class FlightOperationModule {
    
    public void menuFlightOperation() {
        Scanner scanner = new Scanner(System.in);
        int response = 0;

        while (true) {
            System.out.println("*** FRS Management :: Flight Operation Module ***\n");
            System.out.println("1: Create Flight ");
            System.out.println("2: View All Flights");
            System.out.println("3: View Flight Details");
            System.out.println("4: update Flight");
            System.out.println("5: delete Flight");
            System.out.println("-----------------------");
            System.out.println("6: Create Flight Schedule Plan");
            System.out.println("7: View All Flight Schedule Plans");
            System.out.println("8: View Flight Schedule Plan Details");
            System.out.println("9: Update Flight Schedule Plan Details");
            System.out.println("10: Delete Flight Schedule Plan Details");
            System.out.println("-----------------------");
            System.out.println("11: Logout\n");

            response = 0;

            while (response < 1 || response > 11) {
                System.out.print("> ");

                response = scanner.nextInt();

                switch (response) {
                    case 1:
                        createFlight();
                        break;
                    case 2:
                        viewAllFlights();
                        break;
                    case 3:
                        viewFlightDetails();
                        break;
                    case 4:
                        updateFlight();
                        //includes viewFlightDetails();
                        break;
                    case 5:
                        deleteFlight();
                        //includes viewFlightDetails();
                        break;
                    case 6:
                        createFlightSchedulePlan();
                        break;
                    case 7:
                        viewAllFlightSchedulePlans();
                        break;
                    case 8:
                        viewFlightSchedulePlanDetails();
                        break;                  
                    case 9:
                        updateFlightSchedulePlanDetails();
                        //include viewFlightSchedulePlanDetails();
                        break;
                    case 10:
                        deleteFlightSchedulePlanDetails();
                        //include viewFlightSchedulePlanDetails();
                        break;
                    case 11:
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
    
    public void createFlight() {
        
    }
    
    public void viewAllFlights() {
        
    }
    
    public void viewFlightDetails() {
        
    }
    
    public void updateFlight() {
        
    }
    
    public void deleteFlight() {
        
    }
    
    public void createFlightSchedulePlan() {
        
    }
    
    public void viewAllFlightSchedulePlans() {
        
    }
    
    public void viewFlightSchedulePlanDetails() {
        
    }

    public void updateFlightSchedulePlanDetails() {
        
    }
    
    public void  deleteFlightSchedulePlanDetails() {
        
    }
    
}
