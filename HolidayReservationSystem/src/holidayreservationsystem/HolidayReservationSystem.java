/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package holidayreservationsystem;

import java.util.Scanner;
import ws.frs.InvalidLoginCredentialException;
import ws.frs.InvalidLoginCredentialException_Exception;
import ws.frs.Partner;

/**
 *
 * @author tristan
 */
public class HolidayReservationSystem {
    private static Partner partner;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scanner = new Scanner(System.in);
        int response;
        
        while (true) {
            response = 0;
            System.out.println("\n*** Welcome to Holiday Reservation System ***\n");
            System.out.println("1: Login");
            System.out.println("2: Search for flights");
            System.out.println("3: Exit\n");
            
            while (response < 1 || response > 3) {
                response = 0;    
                System.out.print("> ");
                response = scanner.nextInt();
                scanner.nextLine();

                if (response == 1) {
                    doLogin();
                    break; 
                } else if (response == 2) {
//                    searchFlight();
//                    break;
                } else if (response == 3) {
                    return;
                } else {
                    System.out.println("Please choose a valid option.");
                }
            }
            
            if (partner != null) {
                loggedInView();
            }
        }
    }
    
    private static void doLogin() {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";
        
        try {
            System.out.println("\n*** Holiday Reservation System:: Partner Login ***\n");
            System.out.print("Enter username> ");
            username = scanner.nextLine().trim();
            System.out.print("Enter password> ");
            password = scanner.nextLine().trim();

            if(username.length() > 0 && password.length() > 0) {
                partner = partnerLogin(username, password);
            } else {
                System.out.println("Missing login credentials!");
            }
        } catch (InvalidLoginCredentialException_Exception ex) {
            System.out.println("Invalid input, enter username and password in text!\n");
            scanner.next();
        }
    }
    
    public static void loggedInView() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("*** Welcome to the Holiday Reservation System ***\n");
            System.out.println("You are login as " + partner.getPartnerName() + "\n");
            System.out.println("1: Search Flight");
            System.out.println("2: View all Flight Reservation");
            System.out.println("3: View Flight Reservation Detail");
            System.out.println("4: logout\n"); 

            int response;

            System.out.print("> ");
            response = scanner.nextInt();

            switch (response) {
                case 1:
//                    searchFlight();
//                    break;
                case 2:
//                    viewAllFlightReservations();
//                    break;
                case 3:
//                    viewFlightReservationDetails();
//                    break;
                case 4:
                    System.out.println("Exiting the application.");
                    doLogOut();
                    return;
                default:
                    System.out.println("Invalid option, please try again!");
            }
        }
    }
    
    private static void doLogOut() {
        partner = null;
    }

    
    private static Partner partnerLogin(java.lang.String username, java.lang.String password) throws InvalidLoginCredentialException_Exception {
        ws.frs.PartnerWebService_Service service = new ws.frs.PartnerWebService_Service();
        ws.frs.PartnerWebService port = service.getPartnerWebServicePort();
        return port.partnerLogin(username, password);
    }
    
}
