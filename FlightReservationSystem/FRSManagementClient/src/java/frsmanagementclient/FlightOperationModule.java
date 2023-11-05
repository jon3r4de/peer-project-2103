/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.FlightRouteSessionBeanRemote;
import ejb.session.stateless.FlightSchedulePlanSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import entity.CabinClass;
import entity.Fare;
import entity.Flight;
import entity.FlightSchedulePlan;
import enumeration.CabinClassEnum;
import enumeration.FlightScheduleEnum;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import util.exception.AircraftConfigNotFoundException;
import util.exception.DeleteFlightException;
import util.exception.FlightExistException;
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteDisabledException;
import util.exception.FlightRouteNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author jonang
 */
public class FlightOperationModule {
    
    private FlightSessionBeanRemote flightSessionBeanRemote;
    
    private FlightRouteSessionBeanRemote flightRouteSessionBeanRemote;
    
    private FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote;
    
    private static DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd MMM yy hh:mm aa");
    private static DateFormat ESTIMATED_FLIGHT_DURATION_FORMAT = new SimpleDateFormat("hh Hours mm Minute");
    
    FlightOperationModule(FlightSessionBeanRemote flightSessionBeanRemote, FlightRouteSessionBeanRemote flightRouteSessionBeanRemote, FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote) {
        this.flightSessionBeanRemote = flightSessionBeanRemote;
        this.flightRouteSessionBeanRemote = flightRouteSessionBeanRemote;
        this.flightSchedulePlanSessionBeanRemote = flightSchedulePlanSessionBeanRemote;
    }
    
    public void menuFlightOperation() {
        Scanner scanner = new Scanner(System.in);
        int response = 0;

        while (true) {
            System.out.println("*** FRS Management :: Flight Operation Module ***\n");
            System.out.println("1: Create Flight ");
            System.out.println("2: View All Flights");
            System.out.println("3: View Flight Details");
            //System.out.println("4: update Flight");
            //System.out.println("5: delete Flight");
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
                        //updateFlight();
                        //includes viewFlightDetails();
                        break;
                    case 5:
                        //deleteFlight();
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

        }
    }
    
    public void createFlight() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("*** FRSManagement :: Flight Operation Module :: Create New Flight ***\n");

            System.out.print("Enter Flight Number> ");
            String flightNumber = scanner.nextLine().trim();
            Flight newFlight = new Flight(flightNumber);

            System.out.print("Enter Origin Airport IATA code> ");
            String originCode = scanner.nextLine().trim();

            System.out.print("Enter Destination Airport IATA code> ");
            String destinationCode = scanner.nextLine().trim();

            System.out.print("Enter Aircraft Configuration Name> ");
            String aircraftConfigurationName = scanner.nextLine().trim();
            
            Long newFlightId = flightSessionBeanRemote.createNewFlight(newFlight, originCode, destinationCode, aircraftConfigurationName);
            System.out.println("Flight with ID: " + newFlightId + " is created!\n");

            if (flightRouteSessionBeanRemote.checkIfComplementaryFlightRouteExist(originCode, destinationCode)) {
                System.out.print("Create a complementary return flight? (Y/N)> ");
                String createOption = scanner.nextLine().trim();
                if (createOption.equals("Y")) {

                    System.out.print("Enter Complementary Flight Number> ");
                    String complementaryFlightNumber = scanner.nextLine().trim();
                    Flight newComplementaryFlight = new Flight(complementaryFlightNumber);

                    Long newComplementaryReturnFlightId = flightSessionBeanRemote.createNewFlight(newComplementaryFlight, destinationCode, originCode, aircraftConfigurationName);
                    flightSessionBeanRemote.associateComplementaryFlight(newFlightId, newComplementaryReturnFlightId);
                    System.out.println("Complementary Flight with ID: " + newComplementaryReturnFlightId + " is created!\n");
                }
            }
        } catch (FlightExistException | GeneralException | FlightRouteNotFoundException | AircraftConfigNotFoundException | FlightRouteDisabledException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
    }
    
    public void viewAllFlights() {
       
        List<Flight> flights = flightSessionBeanRemote.retrieveAllFlights();
        
        if (flights.isEmpty()) {
            System.out.println("No Available Flights!\n");
        }
        
        flights.stream().map(flight -> {
            System.out.println(flight);
            return flight;
        }).filter(flight -> (flight.getComplementaryReturnFlight() != null)).forEachOrdered(flight -> {
            System.out.println("Complementary Flight = [" + flight.getComplementaryReturnFlight() + "]");
        });
        
        System.out.println();
        
    }
    
    public void viewFlightDetails() {
            try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Flight Number> ");
            String flightNumber = scanner.nextLine().trim();

            Flight flight = flightSessionBeanRemote.retrieveFlightByFlightNumber(flightNumber);

            System.out.println(flight);
            System.out.println(flight.getFlightRoute());
            for (CabinClass cabinClass : flight.getAirCraftConfig().getCabinClasses()) {
                System.out.println(cabinClass.getCabinClassType());
                System.out.println("Available seats = " + cabinClass.getMaxSeatCapacity());
            }
            System.out.println();

            // do it such that 1 is linked to update, 2 is linked ot delete and 3 is contiunue
            System.out.print("would you like to update or delete flight?    \n");
            System.out.println("1: Update Flight ");
            System.out.println("2: Delete Flights");
            System.out.println("3: out of here queen ~slay~ ");
            
            int response = 0;
            
             while (response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();
                scanner.nextLine();

                switch (response) {
                    
                    case 1:
                        System.out.print("Update details of this flight? (Y/N)> ");
                        
                        if (scanner.nextLine().trim().equals("Y")) {
                            updateFlight(flight);
                        }
                    break;
                    
                    case 2:
                    System.out.print("Delete this flight? (Y/N)> ");
                    if (scanner.nextLine().trim().equals("Y")) {
                        deleteFlight(flight);
                    }
                    break;
                    
                    case 3:
                       
                    return;
                    
                    default:
                    System.out.println("incorrect input please try again");
                }
            }

        } catch (FlightNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
    }
    
    public void updateFlight(Flight flight) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** FRSManagement :: Flight Operation Module :: Update Flight ***\n");
        System.out.print("Enter New Aircraft Configuration Name> ");
        String newAircraftConfigurationName = scanner.nextLine().trim();

        try {
            flightSessionBeanRemote.updateFlight(flight, newAircraftConfigurationName);
        } catch (AircraftConfigNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }        
    }
    
    public void deleteFlight(Flight flight) {
        try {
            flightSessionBeanRemote.deleteFlight(flight);
        } catch (FlightNotFoundException | DeleteFlightException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public void createFlightSchedulePlan() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("*** FRSManagement :: Flight Operation Module :: Create New Flight Schedule Plan ***\n");
            System.out.print("Enter Flight Number> ");
            String flightNumber = scanner.nextLine().trim();

            Flight flight = flightSessionBeanRemote.retrieveFlightByFlightNumber(flightNumber);
            if (flight.isDisabled() == true) {
                System.out.println("Unable to create schedule plan for disabled flight. Try again.");
                return;
            }

            System.out.println("Select Type of Flight Schedule Plan> ");
            System.out.println("1: Single");
            System.out.println("2: Multiple");
            System.out.println("3: Recurrent schedule every n day");
            System.out.println("4: Recurrent schedule every week");
            System.out.print("> ");
            int response = scanner.nextInt();
            scanner.nextLine();

            String departureDateString;
            String departureTimeString;
            DateFormat departureTimeFormat = new SimpleDateFormat("hh:mm aa");
            DateFormat departureDateFormat = new SimpleDateFormat("dd MMM yy");
            Date departureDate;
            Date departureTime;
            String durationString;
            DateFormat durationFormat = new SimpleDateFormat("hh Hours mm Minute");
            Date durationTime;
            List<Fare> fares = new ArrayList<>();
            boolean option = true;
            Long firstDepartureTimeLong = Long.MIN_VALUE;
            FlightSchedulePlan newFlightSchedulePlan = new FlightSchedulePlan();
            Date startDateTime;
            Date endDateTime;
            Integer recurrence = 0;
            
            switch(response) {
                case 1:
                    doCreateSingleFlightSchedulePlan(scanner, flight);
                    break;
                case 2:
                    doCreateMultipleFlightSchedulePlan(scanner, flight);
                    break;
                default:
                    System.out.println("not yet");
                    break;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    private static String mapCabinClassToString(CabinClass cabinClass) {
        // Mapping logic based on cabinClassType
        switch (cabinClass.getCabinClassType()) {
            case FIRST:
                return "F";
            case BUSINESS:
                return "J";
            case PREMIUMECONOMY:
                return "W";
            case ECONOMY:
                return "Y";
            default:
                throw new IllegalArgumentException("Invalid cabin class type");
        }
    }
    
    private List<Fare> createFares(Scanner scanner, Flight flight) {
        boolean option = true;
        List<Fare> fares = new ArrayList<>();
        List<String> inputtedCabinClasses = new ArrayList<>();
        
        while (option) {
            List<CabinClass> cabinClassesInFlight = flight.getAirCraftConfig().getCabinClasses();
            List<String> cabinClassStrings = cabinClassesInFlight.stream()
                .map(cabinClass -> mapCabinClassToString(cabinClass))
                .collect(Collectors.toList());

            System.out.print("Enter Cabin Class Code. Available Cabins: " + cabinClassStrings.toString() + ">");
            String cabinClassCode = scanner.nextLine().trim();
            System.out.print("Enter Fare Basis Code> ");
            String fareBasisCode = scanner.nextLine().trim();
            System.out.print("Enter Amount> $ ");
            BigDecimal fareAmount = new BigDecimal(scanner.nextLine().trim());


            CabinClassEnum cabinClassType;

            if (!cabinClassStrings.contains(cabinClassCode)) {
                System.out.println("There is no such cabin class in this flight.");
                break;
            }

            inputtedCabinClasses.add(cabinClassCode);

            if (cabinClassCode.charAt(0) == 'F') {
                cabinClassType = CabinClassEnum.FIRST;
            } else if (cabinClassCode.charAt(0) == 'J') {
                cabinClassType = CabinClassEnum.BUSINESS;
            } else if (cabinClassCode.charAt(0) == 'W') {
                cabinClassType = CabinClassEnum.PREMIUMECONOMY;
            } else if (cabinClassCode.charAt(0) == 'Y') {
                cabinClassType = CabinClassEnum.ECONOMY;
            } else {
                System.out.println("Invalid Cabin Class Code Entered. Try Again.");
                break;
            }

            Fare fare = new Fare(fareBasisCode, cabinClassType, fareAmount);
            fares.add(fare);

            boolean isCabinClassFareMissing = false;
            for (String cabinClass : cabinClassStrings) {
                if (!inputtedCabinClasses.contains(cabinClass)) {
                    isCabinClassFareMissing = true; // Found a cabin class that is not in the inputted list
                    break;
                }
            }

            System.out.print("Continue to create more fares? (Y/N)> ");
            String optionString = scanner.nextLine().trim();

            if (optionString.charAt(0) == 'N') {
                if (isCabinClassFareMissing) {
                    System.out.print("Not all cabin classes have a fare. Please continue to create more.");
                    break;
                } else {
                    System.out.print("Proceeding...");
                    option = false;  
                }  
            }
        }
        return fares;
    }
    
    private void doCreateSingleFlightSchedulePlan(Scanner scanner, Flight flight) {
        try {
            System.out.println("*** Create Single Flight Schedule ***\n");
            System.out.print("Enter Departure Date and Time (day MONTH year hr:min AM/PM)> ");
            String departureDateTimeString = scanner.nextLine().trim();

            // Parse the combined date and time string
            Date departureDateTime = DATE_TIME_FORMAT.parse(departureDateTimeString);

            System.out.print("Enter Flight Duration (xx Hours xx Minute)> ");
            String estimatedFlightDurationString = scanner.nextLine().trim();
            Date estimatedFlightDuration = ESTIMATED_FLIGHT_DURATION_FORMAT.parse(estimatedFlightDurationString);

            System.out.println("*** Create Single Flight Schedule :: Enter Fares ***\n");
            List<Fare> fares = createFares(scanner, flight);
            
            FlightSchedulePlan newFlightSchedulePlan = new FlightSchedulePlan(FlightScheduleEnum.SINGLE, fares);
            Long newFlightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewSingleFlightSchedulePlan(newFlightSchedulePlan, flight.getFlightId(), departureDateTime, estimatedFlightDuration);

            System.out.println("Flight Schedule Plan with ID: " + newFlightSchedulePlanId + " has been created.\n");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void doCreateMultipleFlightSchedulePlan(Scanner scanner, Flight flight) {
        try {
            System.out.println("*** Create Multiple Flight Schedule ***\n");
            System.out.println("*** Create Single Flight Schedule :: Enter Schedules ***\n");
            List<Date> departureDateTimes = new ArrayList<>();
            List<Date> estimatedFlightDurations = new ArrayList<>();

            boolean option = true;
            while (option) {
                System.out.print("Enter Departure Date and Time (day MONTH year hr:min AM/PM)> ");
                String departureDateTimeString = scanner.nextLine().trim();
                Date departureDate = DATE_TIME_FORMAT.parse(departureDateTimeString);

//                if (departureDateTimes.isEmpty()) {
//                    firstDepartureTimeLong = departureDate.getTime() + departureTime.getTime();
//                }

                departureDateTimes.add(departureDate);

                System.out.print("Enter Flight Duration (xx Hours xx Minute) > ");
                String estimatedFlightDurationString = scanner.nextLine().trim();
                Date estimatedFlightDuration = ESTIMATED_FLIGHT_DURATION_FORMAT.parse(estimatedFlightDurationString);
                estimatedFlightDurations.add(estimatedFlightDuration);

                System.out.print("Continue to create more schedules? (Y/N)> ");
                String optionString = scanner.nextLine().trim();
                if (optionString.charAt(0) == 'N') {
                    option = false;
                }
            }

            System.out.println("*** Create Multiple Flight Schedule :: Enter Fares ***\n");
            List<Fare> fares = createFares(scanner, flight);
            
            FlightSchedulePlan newFlightSchedulePlan = new FlightSchedulePlan(FlightScheduleEnum.MULTIPLE, fares); //rmb to merge flight
            Long newFlightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewMultipleFlightSchedulePlan(newFlightSchedulePlan, flight.getFlightId(), departureDateTimes, estimatedFlightDurations);

            System.out.println("Flight Schedule Plan with ID: " + newFlightSchedulePlanId + " has been created.\n");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
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
