/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsreservationclient;

import ejb.session.stateless.CustomerSessionBeanRemote;
import entity.Customer;
import java.util.Scanner;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author jonang
 */
public class MainApp {
    private Customer customer;
    private CustomerSessionBeanRemote customerSessionBeanRemote;
    
    public MainApp() {
       
    }
    
    public MainApp(CustomerSessionBeanRemote customerSessionBeanRemote) {
       this();
       this.customerSessionBeanRemote = customerSessionBeanRemote;
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
                            try {
                                this.customer = this.doLogin(scanner);
                                break; // Exiting the switch after handling login
                            } catch (InvalidLoginCredentialException ex) {
                                System.out.println("Invalid login: " + ex.getMessage() + " Please try again!");
                                break;
                            }
                        case 2:
                            this.doRegister(scanner);
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

            if (customer != null) {
                loggedInView();
            }
//            if (response == 2) {
//                break;
//            }
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
    
    public Customer doLogin(Scanner sc) throws InvalidLoginCredentialException { //should return custiomer imo --> atm void just to have no errors
        //this.loggedInView();
        String username = "";
        String password = "";
    
        System.out.println("Please enter your login details.");

        System.out.println("Enter Username:");
        System.out.print("> ");
        username = sc.nextLine().trim();

        System.out.println("Enter Password:");
        System.out.print("> ");

        password = sc.nextLine().trim();
        
        try {
            Customer customer = customerSessionBeanRemote.login(username, password);
            return customer;
        } catch (InvalidLoginCredentialException ex) {
            throw ex;
        }
    }
    
    public void doRegister(Scanner sc) { //can decide whether auto login after register or not 
        String firstName = "";
        String lastName = "";
        String email = "";
        String contactNumber = "";
        String address = "";
        String username = "";
        String password = "";

        
        System.out.println("Enter First Name:");
        System.out.print("> ");
        firstName = sc.nextLine().trim();

        System.out.println("Enter Last Name:");
        System.out.print("> ");
        lastName = sc.nextLine().trim();

        System.out.println("Enter Email:");
        System.out.print("> ");
        email = sc.nextLine().trim();

        System.out.println("Enter Contact Number:");
        System.out.print("> ");
        contactNumber = sc.nextLine().trim();

        System.out.println("Enter Address:");
        System.out.print("> ");
        address = sc.nextLine().trim();

        System.out.println("Enter Username:");
        System.out.print("> ");
        username = sc.nextLine().trim();

        System.out.println("Enter Password:");
        System.out.print("> ");
        password = sc.nextLine().trim();
        
        try {
            Long newCustId = customerSessionBeanRemote.registerCustomer(new Customer(firstName, lastName, email, contactNumber, address, username, password));
            System.out.println("New Customer Created. Their ID is: " + newCustId);
        } catch (UnknownPersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    public void doLogOut() {
        this.customer = null;
    }
}
