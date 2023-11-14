/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsreservationclient;

import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.ReservationSessionBeanRemote;
import entity.Customer;
import entity.Passenger;
import entity.Reservation;
import enumeration.CabinClassEnum;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.exception.AirportNotFoundException;
import util.exception.FlightScheduleNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.NoAvailableSeatsException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author jonang
 */
public class MainApp {
    private Customer customer;
    private CustomerSessionBeanRemote customerSessionBeanRemote;
    private ReservationSessionBeanRemote reservationSessionBeanRemote;
    
    public MainApp() {
       
    }
    
    public MainApp(CustomerSessionBeanRemote customerSessionBeanRemote, ReservationSessionBeanRemote reservationSessionBeanRemote) {
       this();
       this.customerSessionBeanRemote = customerSessionBeanRemote;
       this.reservationSessionBeanRemote = reservationSessionBeanRemote;
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
                    scanner.nextLine();

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
            System.out.println("You are login as " + customer.getFirstName() + " " + customer.getLastName() + "\n");
            System.out.println("1: Search Flight");
            System.out.println("2: View Flight");
            System.out.println("3: View Flight Reservation Detail");
            System.out.println("4: logout\n"); 

            int response;

            System.out.print("> ");
            response = scanner.nextInt();

            switch (response) {
                case 1:
                    searchFlight();
                    break;
                case 2:
                    viewFlightReservation();
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
    
    
    public void viewFlightReservation() {
        
    }
    
        public void reserveFlight(Integer tripType, Integer numOfPassengers) {
        //try {
            Scanner scanner = new Scanner(System.in);
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd MMM", Locale.US);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            System.out.println("*** FRS Reservation :: Search Flight :: Reserve Flight***\n");

            List<Long> flightScheduleIds = new ArrayList<>();
            List<Long> returnFlightScheduleIds = new ArrayList<>();

            System.out.print("Enter Departure Airport IATA Code> ");
            String departureAirportiATACode = scanner.nextLine().trim();
            System.out.print("Enter Destination Airport IATA Code> ");
            String destinationAirportiATACode = scanner.nextLine().trim();
            System.out.print("Enter Departure Date (dd/mm/yyyy)> ");
           
             Date departureDate = null;
            try {
                departureDate = inputDateFormat.parse(scanner.nextLine().trim());
            } catch (ParseException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Date returnDate = null;
            if (tripType == 2) {
                System.out.print("Enter Return Date (dd/mm/yyyy)> ");
                try {
                    returnDate = inputDateFormat.parse(scanner.nextLine().trim());
                } catch (ParseException ex) {
                    Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            boolean next = true;
            while (next) {
                System.out.print("Enter Flight Schedule ID to Reserve> ");
                flightScheduleIds.add(scanner.nextLong());

                System.out.print("More Flights to Reserve? Y/N> ");
                String option = scanner.nextLine().trim();

                if (option.charAt(0) == 'N') {
                    next = false;
                }

            }

            next = true;
            if (tripType == 2) {
                while (next) {
                    System.out.print("Enter Return Flight Schedule ID to Reserve> ");
                    returnFlightScheduleIds.add(scanner.nextLong());

                    System.out.print("More Flights to Reserve? Y/N> ");
                    String option = scanner.nextLine().trim();

                    if (option.charAt(0) == 'N') {
                        next = false;
                    }
                }
            }

            List<String> creditCard = new ArrayList<>();
            System.out.print("Enter Credit Card Number> ");
            creditCard.add(scanner.nextLine().trim());
            System.out.print("Enter Name On the Card> ");
            creditCard.add(scanner.nextLine().trim());
            System.out.print("Enter Expiry Date> ");
            creditCard.add(scanner.nextLine().trim());
            System.out.print("Enter CVV Number> ");
            creditCard.add(scanner.nextLine().trim());
            
            BigDecimal temp = new BigDecimal(1.00);
            
            List<Passenger> passengers = new ArrayList<>();
           
            for (int i = 1; i <= numOfPassengers; ++i) {
                
                try {
                System.out.print("Enter First Name of Passenger " + i + "> ");
                String firstName  = scanner.nextLine().trim();
                
                System.out.print("Enter Last Name of Passenger " + i + "> ");
                String lastName = scanner.nextLine().trim();
                
                System.out.print("Enter Passport Number of Passenger " + i + "> ");
                String passportNumber = scanner.nextLine().trim();
                
                /*passenger must have reseration tagged to it --> handle during session bean --> pass in reservation id as well
                for the creation of the passenger*/
                Passenger passenger = new Passenger(firstName, lastName, passportNumber); //need sessionbean for this
                
                passengers.add(passenger);
                
                } catch(Exception ex) { // for compile
                    System.out.println("Error: " + ex.getMessage());
                }
           
            Reservation reservation = new Reservation(temp, numOfPassengers, creditCard);
            try {
                Long newFlightReservationId = reservationSessionBeanRemote.reserveFlight(numOfPassengers, creditCard,
                        flightScheduleIds, returnFlightScheduleIds, departureAirportiATACode, destinationAirportiATACode, departureDate, returnDate, customer);
                System.out.println("Reserved Successfully! Flight Reservation ID: " + newFlightReservationId + "\n");

            } catch (NoAvailableSeatsException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            } catch (ParseException ex) {
                System.out.println("Invalid date input!\n");
            }


                
                reservation.setPassengerList(passengers);
                
                //set thelist of passenger into reservation
                //reservation.set(passengers)
                
                
            }



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
        
        if (firstName.length() > 0 && lastName.length() > 0 && email.length() > 0 && contactNumber.length() > 0 && address.length() > 0 && username.length() > 0 && password.length() > 0) {
            Customer newCustomer = new Customer(firstName, lastName, email, contactNumber, address, username, password);
            try {
            Long newCustId = customerSessionBeanRemote.registerCustomer(new Customer(firstName, lastName, email, contactNumber, address, username, password));
                System.out.println("Registered succefully! Customer ID: " + newCustId + "\n");
                customer = newCustomer;
            } catch (UnknownPersistenceException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("Incomplete information, please try again!\n");
        }
    }
    
    
    public void doLogOut() {
        this.customer = null;
    }
    
       public void searchFlight() {

        try {
            Scanner scanner = new Scanner(System.in);
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd MMM", Locale.US);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMM", Locale.US);
            Integer tripType;
            String departureAirportiATACode = "";
            String destinationAirportiATACode = "";
            Date departureDate;
            Date returnDate = null;
            Integer numOfPassengers;
            Integer flightTypePreference;
            Integer cabinClass;

            System.out.println("*** FRS Reservation :: Search Flight ***\n");
            System.out.print("Enter Trip Type:  1: One-way, 2: Round-trip > ");
            tripType = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Departure Airport IATA Code> ");
            departureAirportiATACode = scanner.nextLine().trim();
            System.out.print("Enter Destination Airport IATA Code> ");
            destinationAirportiATACode = scanner.nextLine().trim();
            System.out.print("Enter Departure Date (dd MMM)> ");
            departureDate = inputDateFormat.parse(scanner.nextLine().trim());
            
            if (tripType == 2) {
                System.out.println("Enter Return Date (dd MMM)> ");
                returnDate = inputDateFormat.parse(scanner.nextLine().trim());
            }
            
            System.out.print("Enter Number Of Passengers> ");
            numOfPassengers = scanner.nextInt();
            System.out.print("Enter Flight Type Preference:  1: Direct Flight, 2: Connecting Flight, 3: No Preference > ");
            flightTypePreference = scanner.nextInt();
            System.out.print("Enter Cabin Class Preference:  1: First Class, 2: Business Class, 3: Premium Economy Class, 4: Economy Class, 5: No Preference > ");
            cabinClass = scanner.nextInt();

            CabinClassEnum cabinClassType = null;
            
            switch (cabinClass) {
                case 1:
                    cabinClassType = CabinClassEnum.FIRST;
                    break;
                case 2:
                    cabinClassType = CabinClassEnum.BUSINESS;
                    break;
                case 3:
                    cabinClassType = CabinClassEnum.PREMIUMECONOMY;
                    break;
                case 4:
                    cabinClassType = CabinClassEnum.ECONOMY;
                    break;
                default:
                    break;
            }

            switch (flightTypePreference) {
                case 1:
                    searchDirectFlight(departureAirportiATACode, destinationAirportiATACode, departureDate, returnDate, numOfPassengers, tripType, cabinClassType);
                    break;
                case 2:
                    searchConnectingFlights(departureAirportiATACode, destinationAirportiATACode, departureDate, returnDate, numOfPassengers, tripType, cabinClassType);
                    break;
                default:
                    searchDirectFlight(departureAirportiATACode, destinationAirportiATACode, departureDate, returnDate, numOfPassengers, tripType, cabinClassType);
                    searchConnectingFlights(departureAirportiATACode, destinationAirportiATACode, departureDate, returnDate, numOfPassengers, tripType, cabinClassType);
                    break;
            }

            scanner.nextLine();
            System.out.print("Do you wish to reserve a flight? (Y/N) > ");
            String reserve = scanner.nextLine().trim();

            if (reserve.equals("Y")) {
                reserveFlight(tripType, numOfPassengers);
            }
        } catch (ParseException ex) {
            System.out.println("Invalid date input!\n");
        } catch (AirportNotFoundException | FlightScheduleNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
            System.out.println();
        }
    }
       
     public void searchConnectingFlights(String departureAirportiATACode, String destinationAirportiATACode, Date departureDate, 
             Date returnDate, Integer numOfPassengers, Integer tripType, CabinClassEnum cabinClassType) throws AirportNotFoundException, FlightScheduleNotFoundException {
         
     }
 
       
    public void searchDirectFlight(String departureAirportiATACode, String destinationAirportiATACode, Date departureDate, Date returnDate, Integer numOfPassengers,
            Integer tripType, CabinClassEnum cabinClassType) throws AirportNotFoundException, FlightScheduleNotFoundException {
   
       
    }   
    
}
