/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsreservationclient;

import entity.Customer;
import java.util.Scanner;

/**
 *
 * @author jonang
 */
public class MainApp {
    private Customer customer;
    
    public MainApp() {
       
    }
    
    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        int response;

        while (true) {
            System.out.println("*** Welcome to the FRS reservation client system ***\n");
            System.out.println("1: Login");
            System.out.println("2: Register");
            System.out.println("3: Search Flight");
            System.out.println("4: Exit\n");

            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                if (scanner.hasNextInt()) {
                    response = scanner.nextInt();

                    switch (response) {
                        case 1:
                            this.doLogin();
                            break; // Exiting the switch after handling login
                        case 2:
                            this.doRegister();
                            break; 
                        case 3:
                            this.searchFlight();
                            break;
                        case 4: 
                            System.out.println("Exiting the application.");
                            return;
                        default:
                            System.out.println("Invalid option, please try again!");
                            break; // Exiting the switch after an invalid option
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

    public void loggedInView() {
        //1 Login --> runApp()
        //2 Register --> runApp()
        //3 Search Flight --> in runApp() as well --> ie in both 
        //4 Reserve Flight --> inside search flight ie search then do you want reserve?
        //5 View my Flight
        //6 View my flight reservation detail
        //7 customer log out --> set customer to null
        
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("*** Welcome to the FRS reservation client system ***\n");
            System.out.println("1: Search Flight");
            System.out.println("2: view flight");
            System.out.println("3: view flight reservation detail");
            System.out.println("4: logout\n"); 

            int response;

            System.out.print("> ");
            response = scanner.nextInt();

            switch (response) {
                case 1:
                    searchFlight();
                    break;
                case 2:
                    viewFlight();
                    break;
                case 3:
                    viewFlightReservationDetail();
                    break;
                case 4:
                    System.out.println("Exiting the application.");
                    doLogOut();
                    return;
                default:
                    System.out.println("Invalid option, please try again!");
            }
        }

    }
    
    public void searchFlight() {
        
    }
    
    public void viewFlight() {
        
    }
    
    public void viewFlightReservationDetail() {
        
    }
    
    public void doLogin() { //should return custiomer imo --> atm void just to have no errors
        
    }
    
    public void doRegister() { //can decide whether auto login after register or not 
        
    }
    
    
    public void doLogOut() {
        this.customer = null;
    }
    
}
