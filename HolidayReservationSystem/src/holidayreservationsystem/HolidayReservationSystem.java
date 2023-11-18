/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package holidayreservationsystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import ws.frs.AirportNotFoundException_Exception;
import ws.frs.CabinClassEnum;
import ws.frs.Fare;
import ws.frs.FlightSchedule;
import ws.frs.FlightScheduleNotFoundException_Exception;
import ws.frs.FlightSchedulePlan;
import ws.frs.InvalidLoginCredentialException_Exception;
import ws.frs.Partner;
import ws.frs.SeatInventory;

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
    
//    private static void searchFlight() {
//         try {
//            Scanner scanner = new Scanner(System.in);
//            //SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd MMM", Locale.US);
//            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMM", Locale.US);
//            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd MMM yyyy");
//            Integer tripType;
//            String departureAirportiATACode = "";
//            String destinationAirportiATACode = "";
//            Date departureDate;
//            Date returnDate = null;
//            Integer numOfPassengers;
//            Integer flightTypePreference;
//            Integer cabinClass;
//
//            System.out.println("*** Holiday Reservation System :: Search Flight ***\n");
//            System.out.print("Enter Trip Type:  1: One-way, 2: Round-trip > ");
//            tripType = scanner.nextInt();
//            scanner.nextLine();
//            System.out.print("Enter Departure Airport IATA Code> ");
//            departureAirportiATACode = scanner.nextLine().trim();
//            System.out.print("Enter Destination Airport IATA Code> ");
//            destinationAirportiATACode = scanner.nextLine().trim();
//            System.out.print("Enter Departure Date (dd MMM yyyy  '01 JAN 2023')> ");
//            departureDate = inputDateFormat.parse(scanner.nextLine().trim());
//            
//            if (tripType == 2) {
//                System.out.print("Enter Return Date (dd MMM yyyy '01 JAN 2023')> ");
//                returnDate = inputDateFormat.parse(scanner.nextLine().trim());
//            }
//            
//            System.out.print("Enter Number Of Passengers> ");
//            numOfPassengers = scanner.nextInt();
//            System.out.print("Enter Flight Type Preference:  1: Direct Flight, 2: Connecting Flight, 3: No Preference > ");
//            flightTypePreference = scanner.nextInt();
//            System.out.print("Enter Cabin Class Preference:  1: First Class, 2: Business Class, 3: Premium Economy Class, 4: Economy Class, 5: No Preference > ");
//            cabinClass = scanner.nextInt();
//
//            CabinClassEnum cabinClassType = null;
//            
//            switch (cabinClass) {
//                case 1:
//                    cabinClassType = CabinClassEnum.FIRST;
//                    break;
//                case 2:
//                    cabinClassType = CabinClassEnum.BUSINESS;
//                    break;
//                case 3:
//                    cabinClassType = CabinClassEnum.PREMIUMECONOMY;
//                    break;
//                case 4:
//                    cabinClassType = CabinClassEnum.ECONOMY;
//                    break;
//                default:
//                    cabinClassType = null;
//                    break;
//            }
//            
//            List<FlightSchedule> fsList = new ArrayList<>();
//
//            switch (flightTypePreference) {
//                case 1:
//                    List<FlightSchedule> directList = searchDirectFlight(departureAirportiATACode, destinationAirportiATACode, departureDate, returnDate, numOfPassengers, tripType, cabinClassType);
//                    fsList.addAll(directList);
//                    break;
//                case 2:
//                    List<FlightSchedule> connectList = searchConnectingFlights(departureAirportiATACode, destinationAirportiATACode, departureDate, returnDate, numOfPassengers, tripType, cabinClassType);
//                    fsList.addAll(connectList);
//                    break;
//                default:
//                    List<FlightSchedule> directList2 = searchDirectFlight(departureAirportiATACode, destinationAirportiATACode, departureDate, returnDate, numOfPassengers, tripType, cabinClassType);
//                    List<FlightSchedule> connectList2 = searchConnectingFlights(departureAirportiATACode, destinationAirportiATACode, departureDate, returnDate, numOfPassengers, tripType, cabinClassType);
//                    fsList.addAll(directList2);
//                    fsList.addAll(connectList2);
//                    break;
//            }
//
//            scanner.nextLine();
//            if (partner == null) {
//                return;
//            }
//            
//            System.out.print("Do you wish to reserve a flight? (Y/N) > ");
//            String reserve = scanner.nextLine().trim();
//
//            if (reserve.equals("Y")) {
//                if (partner != null) {
//                reserveFlight(tripType, numOfPassengers, fsList);
//                } else {
//                    System.out.println("log in first !");
//                }
//            }
//                } catch (ParseException ex) {
//                    System.out.println("Invalid date input!\n");
//                } catch (AirportNotFoundException_Exception | FlightScheduleNotFoundException_Exception ex) {
//                    System.out.println(ex.getMessage());
//                    System.out.println();
//                } catch (InputMismatchException ex) {
//                    System.out.println("Input mismatch: " + ex.getMessage());
//                    System.out.println();
//                }
//    }
//    
//    public static List<FlightSchedule> searchDirectFlight(String departureAirportiATACode, String destinationAirportiATACode, Date departureDate, Date returnDate, Integer numOfPassengers,
//            Integer tripType, CabinClassEnum cabinClassType) throws AirportNotFoundException_Exception, FlightScheduleNotFoundException_Exception {
//        List<FlightSchedule> returnList = new ArrayList<>();
//        
//        try {
//            System.out.println("Departure Flight Information :: Direct Flight\n");
//            //on required departure date
//            //System.out.println("sdf debug1  : "  + departureDate);
//            
//            List<FlightSchedule> flightSchedules = new ArrayList<>();
//           
//            try {
//                flightSchedules = searchDirectFlightSchedules(departureAirportiATACode, destinationAirportiATACode, departureDate, cabinClassType);
//                returnList.addAll(flightSchedules);
//            } catch(FlightScheduleNotFoundException_Exception ex) {
//                flightSchedules = new ArrayList<>();
//            }
//            
//            if (flightSchedules.isEmpty()) {
//                System.out.println("No available flights departing on " + departureDate + " !\n");
//            } else {
//                System.out.println("----- Departure On " + departureDate + "\n");
//                printDirectFlightSchedulesTable(flightSchedules, cabinClassType, numOfPassengers);
//            }
//
//            //System.out.println("sdf debug3");
//            //3 days before
//            for (int i = 3; i > 0; --i) {
//                Date newDepartureDate = new Date(departureDate.getTime() - i * 24 * 60 * 60 * 1000);
//               // System.out.println("sdf debug2 :" + newDepartureDate);
//            try {
//                flightSchedules = searchDirectFlightSchedules(departureAirportiATACode, destinationAirportiATACode, newDepartureDate, cabinClassType);
//                returnList.addAll(flightSchedules);
//            } catch(FlightScheduleNotFoundException_Exception ex) {
//                flightSchedules = new ArrayList<>();
//            }
//            
//                if (flightSchedules.isEmpty()) {
//                    System.out.println("No available flights departing on " + newDepartureDate + " !\n");
//                } else {
//                    System.out.println("----- Departure On " + newDepartureDate + "\n");
//                    printDirectFlightSchedulesTable(flightSchedules, cabinClassType, numOfPassengers);
//                }
//            }
//
//            //3 days after
//            for (int i = 1; i < 4; ++i) {
//                Date newDepartureDate = new Date(departureDate.getTime() + i * 24 * 60 * 60 * 1000);
//                //System.out.println("sdf debug3 :" + newDepartureDate);
//                try {
//                    flightSchedules = searchDirectFlightSchedules(departureAirportiATACode, destinationAirportiATACode, newDepartureDate, cabinClassType);
//                    returnList.addAll(flightSchedules);
//                } catch(FlightScheduleNotFoundException_Exception ex) {
//                    flightSchedules = new ArrayList<>();
//                }
//            
//                if (flightSchedules.isEmpty()) {
//                    System.out.println("No available flights departing on " + newDepartureDate + " !\n");
//                } else {
//                    System.out.println("----- Departure On " + newDepartureDate + "\n");
//                    printDirectFlightSchedulesTable(flightSchedules, cabinClassType, numOfPassengers);
//                }
//            }
//
//            if (tripType == 2) {
//                System.out.println("\nReturn Flight Information :: Direct Flights\n");
//                //on required departure date
//                
//                try {
//                    flightSchedules = searchDirectFlightSchedules(destinationAirportiATACode, departureAirportiATACode, returnDate, cabinClassType);
//                    returnList.addAll(flightSchedules);
//                } catch(FlightScheduleNotFoundException_Exception ex) {
//                    flightSchedules = new ArrayList<>();
//                }
//                
//                if (flightSchedules.isEmpty()) {
//                    System.out.println("No available flights departing on " + returnDate + " !\n");
//                } else {
//                    System.out.println("----- Return On " + returnDate + "\n");
//                    printDirectFlightSchedulesTable(flightSchedules, cabinClassType, numOfPassengers);
//                }
//
//                //3 days before
//                for (int i = 3; i > 0; --i) {
//                    Date newReturnDate = new Date(returnDate.getTime() - i * 24 * 60 * 60 * 1000);
//                    
//                    try {
//                        flightSchedules = searchDirectFlightSchedules(destinationAirportiATACode, departureAirportiATACode, newReturnDate, cabinClassType);
//                        returnList.addAll(flightSchedules);
//                    } catch(FlightScheduleNotFoundException_Exception ex) {
//                        flightSchedules = new ArrayList<>();
//                    }
//                    
//                    if (flightSchedules.isEmpty()) {
//                        System.out.println("No available flights departing on " + newReturnDate + " !\n");
//                    } else {
//                        System.out.println("----- Return On " + newReturnDate + "\n");
//                        printDirectFlightSchedulesTable(flightSchedules, cabinClassType, numOfPassengers);
//                    }
//                }
//
//                //3 days after
//                for (int i = 1; i < 4; ++i) {
//                    Date newReturnDate = new Date(returnDate.getTime() + i * 24 * 60 * 60 * 1000);
//                   
//                    try {
//                        flightSchedules = searchDirectFlightSchedules(destinationAirportiATACode, departureAirportiATACode, newReturnDate, cabinClassType);
//                        returnList.addAll(flightSchedules);
//                    } catch(FlightScheduleNotFoundException_Exception ex) {
//                        flightSchedules = new ArrayList<>();
//                    }
//
//                    if (flightSchedules.isEmpty()) {
//                        System.out.println("No available flights departing on " + newReturnDate + " !\n");
//                    } else {
//                        System.out.println("----- Return On " + newReturnDate + "\n");
//                        printDirectFlightSchedulesTable(flightSchedules, cabinClassType, numOfPassengers);
//
//                    }
//                }
//            }
//            return returnList;
//        } catch (AirportNotFoundException_Exception /*| FlightScheduleNotFoundException*/ ex) {
//            throw ex;
//        }
//    }
//    
//    public static void printDirectFlightSchedulesTable(List<FlightSchedule> flightSchedules, CabinClassEnum cabinClassType, Integer numOfPassengers) {
//
//        System.out.printf("%-20s%-20s%-20s%-20s%-30s%-25s%-25s%-20s%-40s%-40s%-40s%-40s%-40s%-40s%-40s%-40s%-40s\n", "   ", "Flight Schedule ID", "Flight Number", "Departure Airport", "Departure Time (Local Time)",
//                "Flight Duration Hours","Flight Duration Minutes" , "Destination Airport", "Arrival Time (Local Time)", "First Class Available Seats", "First Class Price", "Business Class Available Seats",
//                "Business Class Price", "Premium Economy Class Available Seats", "Premium Economy Class Price", "Economy Class Available Seats", "Economy Class Price");
//
//        String firstClassAvailableSeats = "/";
//        String businessClassAvailableSeats = "/";
//        String premiumEcoClassAvailableSeats = "/";
//        String economyClassAvailableSeats = "/";
//
//        Double lowestFareFirstClass = Double.MAX_VALUE;
//        Double lowestFareBusinessClass = Double.MAX_VALUE;
//        Double lowestFarePremiumEconomyClass = Double.MAX_VALUE;
//        Double lowestFareEconomyClass = Double.MAX_VALUE;
//
//        Integer num = 1;
//
//        //System.out.println(cabinClassType.toString().equals("FIRST"));
//        
//        for (FlightSchedule flightSchedule : flightSchedules) {
//            
//            //System.out.println(flightSchedule.getCabinClasses().size());
// 
//            
//            for (SeatInventory ss : flightSchedule.getSeatInventories()) {
//                
//                
//                if (cabinClassType != null && (cabinClassType.equals(CabinClassEnum.FIRST) && ss.getCabinClass().getCabinClassType().equals(CabinClassEnum.FIRST))) {
//
//                    firstClassAvailableSeats = ss.getNumberOfBalanceSeats().toString();
//                    
//                    FlightSchedulePlan fsp = getFlightSchedulePlanById(flightSchedule.getFlightSchedulePlan().getFlightSchedulePlanId());
//                    
//                    for (Fare fare : fsp.getFares()) {
//                        if (fare.getCabinClassType().equals(CabinClassEnum.FIRST)) {
//                            lowestFareFirstClass = Math.min(lowestFareFirstClass, fare.getFareAmount().doubleValue());
//                        }
//                    }
//                }
//                
//                else if (cabinClassType != null && (cabinClassType.equals(CabinClassEnum.BUSINESS) && ss.getCabinClass().getCabinClassType().equals(CabinClassEnum.BUSINESS))) {
//                    businessClassAvailableSeats = ss.getNumberOfBalanceSeats().toString();
//                    
//                    FlightSchedulePlan fsp = getFlightSchedulePlanById(flightSchedule.getFlightSchedulePlan().getFlightSchedulePlanId());
//                    
//                    for (Fare fare : fsp.getFares()) {
//                         if (fare.getCabinClassType().equals(CabinClassEnum.BUSINESS)) {
//                            lowestFareBusinessClass = Math.min(lowestFareBusinessClass, fare.getFareAmount().doubleValue());
//                         }
//                    }
//                }
//                
//                else if (cabinClassType != null && (cabinClassType.equals(CabinClassEnum.PREMIUMECONOMY) && ss.getCabinClass().getCabinClassType().equals(CabinClassEnum.PREMIUMECONOMY))) {
//                    premiumEcoClassAvailableSeats = ss.getNumberOfBalanceSeats().toString();
//                    
//                     FlightSchedulePlan fsp = getFlightSchedulePlanById(flightSchedule.getFlightSchedulePlan().getFlightSchedulePlanId());
//                    
//                     for (Fare fare : fsp.getFares()) {
//                         if (fare.getCabinClassType().equals(CabinClassEnum.PREMIUMECONOMY)) {
//                            lowestFarePremiumEconomyClass = Math.min(lowestFarePremiumEconomyClass, fare.getFareAmount().doubleValue());
//                         }
//                    }
//                }
//                
//                else if (cabinClassType != null && (cabinClassType.equals(CabinClassEnum.ECONOMY) && ss.getCabinClass().getCabinClassType().equals(CabinClassEnum.ECONOMY))) {
//                    economyClassAvailableSeats = ss.getNumberOfBalanceSeats().toString();
//                    
//                     FlightSchedulePlan fsp = getFlightSchedulePlanById(flightSchedule.getFlightSchedulePlan().getFlightSchedulePlanId());
//                     
//                    for (Fare fare : fsp.getFares()) {
//                        if (fare.getCabinClassType().equals(CabinClassEnum.ECONOMY)) {
//                        lowestFareEconomyClass = Math.min(lowestFareEconomyClass, fare.getFareAmount().doubleValue());
//                        }
//                    }
//                    
//                } else {
//                    
//                    FlightSchedulePlan fsp = getFlightSchedulePlanById(flightSchedule.getFlightSchedulePlan().getFlightSchedulePlanId());
//                    
//                    if (ss.getCabinClass().getCabinClassType().equals(CabinClassEnum.FIRST)) {
//                        firstClassAvailableSeats = ss.getNumberOfBalanceSeats().toString();
//                    } 
//                    
//                    if (ss.getCabinClass().getCabinClassType().equals(CabinClassEnum.BUSINESS)) {
//                        businessClassAvailableSeats = ss.getNumberOfBalanceSeats().toString();
//                    }
//                    
//                    if (ss.getCabinClass().getCabinClassType().equals(CabinClassEnum.PREMIUMECONOMY)) {
//                        premiumEcoClassAvailableSeats = ss.getNumberOfBalanceSeats().toString();
//                    }
//                    
//                    if (ss.getCabinClass().getCabinClassType().equals(CabinClassEnum.ECONOMY)) {
//                        economyClassAvailableSeats = ss.getNumberOfBalanceSeats().toString();
//                    }
//                     
//                    for (Fare fare : fsp.getFares()) {
//                        
//                        if (fare.getCabinClassType().equals(CabinClassEnum.FIRST)) {
//                            lowestFareFirstClass = Math.min(lowestFareFirstClass, fare.getFareAmount().doubleValue());
//                        }
//                        
//                        if (fare.getCabinClassType().equals(CabinClassEnum.BUSINESS)) {
//                            lowestFareBusinessClass = Math.min(lowestFareBusinessClass, fare.getFareAmount().doubleValue());
//                         }
//
//                        if (fare.getCabinClassType().equals(CabinClassEnum.PREMIUMECONOMY)) {
//                            lowestFarePremiumEconomyClass = Math.min(lowestFarePremiumEconomyClass, fare.getFareAmount().doubleValue());
//                         }
//                        
//                        if (fare.getCabinClassType().equals(CabinClassEnum.ECONOMY)) {
//                        lowestFareEconomyClass = Math.min(lowestFareEconomyClass, fare.getFareAmount().doubleValue());
//                        }
//                    }                   
//                    
//                }
//            }
//
//            String firstClassFare;
//            String businessClassFare;
//            String premiumEconomyClassFare;
//            String economyClassFare;
//
//            if (lowestFareFirstClass == Double.MAX_VALUE) {
//                firstClassFare = "/";
//            } else {
//                firstClassFare = lowestFareFirstClass.toString();
//            }
//            if (lowestFareBusinessClass == Double.MAX_VALUE) {
//                businessClassFare = "/";
//            } else {
//                businessClassFare = lowestFareBusinessClass.toString();
//            }
//            if (lowestFarePremiumEconomyClass == Double.MAX_VALUE) {
//                premiumEconomyClassFare = "/";
//            } else {
//                premiumEconomyClassFare = lowestFarePremiumEconomyClass.toString();
//            }
//            if (lowestFareEconomyClass == Double.MAX_VALUE) {
//                economyClassFare = "/";
//            } else {
//                economyClassFare = lowestFareEconomyClass.toString();
//            }
//
//            System.out.printf("%-20s%-20s%-20s%-20s%-30s%-25s%-25s%-20s%-40s%-40s%-40s%-40s%-40s%-40s%-40s%-40s%-40s\n", 
//              num.toString() + ": ",
//              flightSchedule.getFlightScheduleId(),
//              flightSchedule.getFlightNumber(),
//              flightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getOrigin().getIataAirportcode(),
//              flightSchedule.getDepartureDateTime(),
//              flightSchedule.getEstimatedFlightDurationHours(),
//              flightSchedule.getEstimatedFlightDurationMinutes(),
//              flightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getIataAirportcode(),
//              flightSchedule.getArrivalDateTime(),
//              firstClassAvailableSeats,
//              firstClassFare,
//              businessClassAvailableSeats,
//              businessClassFare,
//              premiumEcoClassAvailableSeats,
//              premiumEconomyClassFare,
//              economyClassAvailableSeats,
//              economyClassFare);
//
//            num++;
//        }
//    }
    
    private static void doLogOut() {
        partner = null;
    }

//    private static java.util.List<ws.frs.FlightSchedule> searchDirectFlightSchedules(java.lang.String destinationAirportiATACode, 
//            java.lang.String departureAirportiATACode, java.util.Date departureDate, ws.frs.CabinClassEnum cabinClassType) 
//            throws AirportNotFoundException_Exception, FlightScheduleNotFoundException_Exception {
//        ws.frs.PartnerWebService_Service service = new ws.frs.PartnerWebService_Service();
//        ws.frs.PartnerWebService port = service.getPartnerWebServicePort();
//        return port.searchDirectFlightSchedules(destinationAirportiATACode, departureAirportiATACode, departureDate, cabinClassType);
//    }
//    
//    private static FlightSchedulePlan getFlightSchedulePlanById(java.lang.Long flightSchedulePlanId) {
//        ws.frs.PartnerWebService_Service service = new ws.frs.PartnerWebService_Service();
//        ws.frs.PartnerWebService port = service.getPartnerWebServicePort();
//        return port.getFlightSchedulePlanById(flightSchedulePlanId);
//    }
    
    private static Partner partnerLogin(java.lang.String username, java.lang.String password) throws InvalidLoginCredentialException_Exception {
        ws.frs.PartnerWebService_Service service = new ws.frs.PartnerWebService_Service();
        ws.frs.PartnerWebService port = service.getPartnerWebServicePort();
        return port.partnerLogin(username, password);
    }
}
