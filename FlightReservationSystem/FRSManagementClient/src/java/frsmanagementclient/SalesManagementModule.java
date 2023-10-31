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
public class SalesManagementModule {
    
        public void menuSalesManagement() {
        Scanner scanner = new Scanner(System.in);
        int response = 0;

        while (true) {
            System.out.println("*** FRS Management :: Sales Management Module ***\n");
            System.out.println("1: View sales inventory ");
            System.out.println("2: View flight reservations");
            System.out.println("3: Logout\n");

            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                switch (response) {
                    case 1:
                        viewSeatInventory();
                        break;
                    case 2:
                        viewFlightReservations();
                        break;
                    case 3:
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
        
    public void viewSeatInventory() {
        
    }
    
    public void viewFlightReservations() {
        
    }
}
