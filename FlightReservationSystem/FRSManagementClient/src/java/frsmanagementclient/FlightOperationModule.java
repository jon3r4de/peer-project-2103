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
import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import enumeration.CabinClassEnum;
import enumeration.FlightScheduleEnum;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import util.exception.AircraftConfigNotFoundException;
import util.exception.DeleteFlightException;
import util.exception.DeleteFlightScheduleException;
import util.exception.DeleteFlightSchedulePlanException;
import util.exception.FareExistException;
import util.exception.FlightExistException;
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteDisabledException;
import util.exception.FlightRouteNotFoundException;
import util.exception.FlightScheduleExistException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.GeneralException;
import util.exception.UpdateFlightSchedulePlanException;

/**
 *
 * @author jonang
 */
public class FlightOperationModule {
    
    private FlightSessionBeanRemote flightSessionBeanRemote;
    
    private FlightRouteSessionBeanRemote flightRouteSessionBeanRemote;
    
    private FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote;
    
    private static DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd MMM yy hh:mm aa");
    private static DateFormat ESTIMATED_FLIGHT_DURATION_FORMAT = new SimpleDateFormat("hh 'Hours' mm 'Minute'");
    
    FlightOperationModule(FlightSessionBeanRemote flightSessionBeanRemote, FlightRouteSessionBeanRemote flightRouteSessionBeanRemote, FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote) {
        this.flightSessionBeanRemote = flightSessionBeanRemote;
        this.flightRouteSessionBeanRemote = flightRouteSessionBeanRemote;
        this.flightSchedulePlanSessionBeanRemote = flightSchedulePlanSessionBeanRemote;
    }
    
    public void menuFlightOperation() {
        Scanner scanner = new Scanner(System.in);
        int response = 0;

        while (true) {
            System.out.println("*** FRSManagement :: Flight Operation Module ***\n");
            System.out.println("1: Create Flight ");
            System.out.println("2: View All Flights");
            System.out.println("3: View Flight Details");
            System.out.println("-----------------------");
            System.out.println("4: Create Flight Schedule Plan");
            System.out.println("5: View All Flight Schedule Plans");
            System.out.println("6: View Flight Schedule Plan Details");
            System.out.println("-----------------------");
            System.out.println("7: Logout\n");

            response = 0;

            while (response < 1 || response > 7) {
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
                        createFlightSchedulePlan();
                        break;
                    case 5:
                        viewAllFlightSchedulePlans();
                        break;
                    case 6:
                        viewFlightSchedulePlanDetails();
                        break;                  
                    case 7:
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
        System.out.println("*** FRSManagement :: Flight Operation Module :: View All Flights ***\n");
        
        if (flights.isEmpty()) {
            System.out.println("No Available Flights!\n");
            return;
        }

        System.out.printf("%-20s%-20s%-20s%-20s\n", "Flight ID", "Flight Number", "Disabled", "Route ID");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");

        List<Flight> printedFlights = new ArrayList<>();
        for (Flight flight : flights) {
            Long flightId = flight.getFlightId();
            String flightNumber = flight.getFlightNumber();
            boolean isDisabled = flight.isDisabled();
            Long routeId = flight.getFlightRoute().getFlightRouteId();

            if (!printedFlights.contains(flight)) {
                System.out.printf("%-20d%-20s%-20s%-20d\n",
                    flightId, flightNumber, (isDisabled ? "Yes" : "No"), routeId);
                printedFlights.add(flight);
                
                if (flight.isHasComplementaryReturnFlight() && flight.getComplementaryReturnFlight() != null) {
                    Flight complementaryReturnFlight = flight.getComplementaryReturnFlight();
                    Long complementaryFlightId = complementaryReturnFlight.getFlightId();
                    String complementaryFlightNumber = complementaryReturnFlight.getFlightNumber();
                    isDisabled = complementaryReturnFlight.isDisabled();
                    routeId = complementaryReturnFlight.getFlightRoute().getFlightRouteId();

                    // Print complementary return flight only if it hasn't been printed before
                    if (!complementaryReturnFlightAlreadyPrinted(complementaryFlightId, flights)) {
                        System.out.printf("%-20d%-20s%-20s%-20s\n",
                                complementaryFlightId, complementaryFlightNumber, isDisabled, routeId);
                        printedFlights.add(complementaryReturnFlight);
                    }
                }
            }
        }
        System.out.println();
    }
    
    private static boolean complementaryReturnFlightAlreadyPrinted(Long complementaryFlightId, List<Flight> flights) {
        for (Flight f : flights) {
            if (f.getFlightId().equals(complementaryFlightId)) {
                return true;
            }
        }
        return false;
    }
    
    public void viewFlightDetails() {
        try {
            System.out.println("*** FRSManagement :: Flight Operation Module :: View Flight Details ***\n");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Flight Number> ");
            String flightNumber = scanner.nextLine().trim();
            System.out.println();

            Flight flight = flightSessionBeanRemote.retrieveFlightByFlightNumber(flightNumber);

            flightNumber = flight.getFlightNumber();
            boolean isDisabled = flight.isDisabled();
            String originStr = flight.getFlightRoute().getOrigin().getAirportName();
            String destinationStr = flight.getFlightRoute().getDestination().getAirportName();
            
            System.out.printf("%-20s%-20s%-20s%-20s\n", "Flight Number", "Disabled", "Origin", "Destination");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");

            System.out.printf("%-20s%-20s%-20s%-20s\n", flightNumber, (isDisabled ? "Yes" : "No"), originStr, destinationStr);
            System.out.println();
            
            System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", "Cabin Class Type", "Num Of Aisles", "Num Of Rows", "Max Seat Capacity", "Actual Seating Config");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
            List<CabinClass> cabinClasses = flight.getAirCraftConfig().getCabinClasses();

            for (CabinClass cc : cabinClasses) {
                CabinClassEnum cabinClassType = cc.getCabinClassType();
                
                Integer numberOfAisle = cc.getNumberOfAisle();
                Integer numberOfRows = cc.getNumberOfRows();
                Integer maxSeatCapacity = cc.getMaxSeatCapacity();
                String actualSeatingConfig = cc.getActualSeatingConfig();
                System.out.printf("%-20s%-20s%-20s%-20s%-20s\n",
                    cabinClassType, numberOfAisle, numberOfRows, maxSeatCapacity, actualSeatingConfig);
                
            }
            System.out.println();

            
            // do it such that 1 is linked to update, 2 is linked ot delete and 3 is contiunue
            System.out.print("Would you like to Update or Delete flight?    \n");
            System.out.println("1: Update Flight ");
            System.out.println("2: Delete Flight");
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
                    System.out.println("Invalid input. Try again.");
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
            System.out.println("5: Quit");
            System.out.print("> ");
            int response = scanner.nextInt();
            scanner.nextLine();
            
            switch(response) {
                case 1:
                    doCreateSingleFlightSchedulePlan(scanner, flight);
                    break;
                case 2:
                    doCreateMultipleFlightSchedulePlan(scanner, flight);
                    break;
                case 3:
                    doCreateRecurrentByDayFlightSchedulePlan(scanner, flight);
                    break;
                case 4: 
                    doCreateRecurrentByWeekFlightSchedulePlan(scanner, flight);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid input. Try again.");
                    break;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    private Long createComplementaryReturnFlight(Scanner scanner, Flight flight, FlightSchedulePlan newFlightSchedulePlan) throws Exception {
        try {
            if (flight.isHasComplementaryReturnFlight()) {
                System.out.println("There is a complementary return flight tagged to this flight.");
                System.out.println("Create a flight schedule plan for this? (Y/N).");
                if (scanner.nextLine().trim().equals("N")) {
                    return null;
                }
                
                System.out.println("Enter Layover Duration (hr:min)");
                String layoverDurationString = scanner.nextLine().trim();
                DateFormat layoverDurationFormat = new SimpleDateFormat("hr:min");
                Date layoverDurationTime = layoverDurationFormat.parse(layoverDurationString);
                
                FlightSchedulePlan returnFlightSchedulePlan = new FlightSchedulePlan(newFlightSchedulePlan.getFlightScheduleType(), newFlightSchedulePlan.getFares());
                Long returnFlightSchedulePlanId;
                
                if (returnFlightSchedulePlan.getFlightScheduleType().equals(FlightScheduleEnum.SINGLE)) {
                    Date estimatedFlightDuration;
                    Date newDepartureDateTime;
                    
                    estimatedFlightDuration = newFlightSchedulePlan.getFlightSchedules().get(0).getEstimatedFlightDuration();
                    newDepartureDateTime = new Date(newFlightSchedulePlan.getFlightSchedules().get(0).getArrivalDateTime().getTime() + layoverDurationTime.getTime());

                    returnFlightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewSingleFlightSchedulePlan(returnFlightSchedulePlan, flight.getComplementaryReturnFlight().getFlightId(), newDepartureDateTime, estimatedFlightDuration);
                } else if (newFlightSchedulePlan.getFlightScheduleType().equals(FlightScheduleEnum.MULTIPLE)) {                            
                    List<Date> estimatedFlightDurations = new ArrayList<>();
                    List<Date> newDepartureDateTimes = new ArrayList<>();

                    for (FlightSchedule flightSchedule : newFlightSchedulePlan.getFlightSchedules()) {
                        estimatedFlightDurations.add(flightSchedule.getEstimatedFlightDuration());
                        newDepartureDateTimes.add(new Date(flightSchedule.getArrivalDateTime().getTime() + layoverDurationTime.getTime()));
                    }

                    returnFlightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewMultipleFlightSchedulePlan(returnFlightSchedulePlan, flight.getComplementaryReturnFlight().getFlightId(), newDepartureDateTimes, estimatedFlightDurations);
                } else {                            
                    Date estimatedFlightDuration;
                    Date newDepartureDateTime;
                            
                    estimatedFlightDuration = newFlightSchedulePlan.getFlightSchedules().get(0).getEstimatedFlightDuration();
                    newDepartureDateTime = new Date(newFlightSchedulePlan.getFlightSchedules().get(0).getArrivalDateTime().getTime() + layoverDurationTime.getTime());

                    returnFlightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewRecurrentFlightSchedulePlan(newFlightSchedulePlan, flight.getComplementaryReturnFlight().getFlightId(), newDepartureDateTime, estimatedFlightDuration, newFlightSchedulePlan.getEndDate(), newFlightSchedulePlan.getRecurrence());
                }
                
                flightSchedulePlanSessionBeanRemote.setReturnFlightSchedulePlan(newFlightSchedulePlan.getFlightSchedulePlanId(),returnFlightSchedulePlan.getFlightSchedulePlanId());
                return returnFlightSchedulePlanId;
            } else {
                return null;
            }
        } catch (Exception ex) {
            throw ex;
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

            System.out.print("Enter Cabin Class Code. Available Cabins: " + cabinClassStrings.toString() + "> ");
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
            System.out.print("Enter Departure Date and Time (day MONTH year hr:min AM/PM - eg: 12 Nov 23 03:30 PM)> ");
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
            
            Long complementaryFlightSchedulePlanId = createComplementaryReturnFlight(scanner, flight, newFlightSchedulePlan);
            if (complementaryFlightSchedulePlanId != null) {
                System.out.println("Return Flight Schedule Plan with ID: " + complementaryFlightSchedulePlanId + " has been created.\n");
            }
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
                System.out.print("Enter Departure Date and Time (day MONTH year hr:min AM/PM - eg: 12 Nov 23 03:30 PM)> ");
                String departureDateTimeString = scanner.nextLine().trim();
                Date departureDate = DATE_TIME_FORMAT.parse(departureDateTimeString);

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
            
            FlightSchedulePlan newFlightSchedulePlan = new FlightSchedulePlan(FlightScheduleEnum.MULTIPLE, fares);
            Long newFlightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewMultipleFlightSchedulePlan(newFlightSchedulePlan, flight.getFlightId(), departureDateTimes, estimatedFlightDurations);

            System.out.println("Flight Schedule Plan with ID: " + newFlightSchedulePlanId + " has been created.\n");
            
            Long complementaryFlightSchedulePlanId = createComplementaryReturnFlight(scanner, flight, newFlightSchedulePlan);
            if (complementaryFlightSchedulePlanId != null) {
                System.out.println("Return Flight Schedule Plan with ID: " + complementaryFlightSchedulePlanId + " has been created.\n");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void doCreateRecurrentByDayFlightSchedulePlan(Scanner scanner, Flight flight) {
        try {
            System.out.println("*** Create Recurrent by Day Schedule ***\n");
              
            int recurrence = 0;
            while (recurrence <= 0) {
                System.out.print("Recurrent every _ days?> ");
                if (scanner.hasNextInt()) {
                    recurrence = scanner.nextInt();
                    if (recurrence <= 0) {
                        System.out.print("Input must be a non-negative integer. Try again.\n");
                    } else {
                        System.out.println("Recurrence set to " + recurrence + " day(s).");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    scanner.next();
                }
            }
            

            System.out.print("Enter Departure Date and Time (day MONTH year hr:min AM/PM - eg: 12 Nov 23 03:30 PM)> ");
            String departureDateTimeString = scanner.nextLine().trim();
            Date departureDateTime = DATE_TIME_FORMAT.parse(departureDateTimeString);

            System.out.print("Enter Flight Duration (hr:min)> ");
            String estimatedFlightDurationString = scanner.nextLine().trim();
            Date estimatedFlightDuration = ESTIMATED_FLIGHT_DURATION_FORMAT.parse(estimatedFlightDurationString);

            System.out.print("Enter End Date (day MONTH year))> ");
            String endDateString = scanner.nextLine().trim();
            DateFormat endDateFormat = new SimpleDateFormat("dd MMM yy");
            Date endDate = endDateFormat.parse(endDateString);

            System.out.println("*** Create Recurrent by Day Flight Schedule :: Enter Fares ***\n");
            List<Fare> fares = createFares(scanner, flight);
            
            FlightSchedulePlan newFlightSchedulePlan = new FlightSchedulePlan(FlightScheduleEnum.RECURRENTDAY, fares); 
            Long newFlightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewRecurrentFlightSchedulePlan(newFlightSchedulePlan, flight.getFlightId(), departureDateTime, estimatedFlightDuration, endDate, recurrence);

            System.out.println("Flight Schedule Plan with ID: " + newFlightSchedulePlanId + " has been created.\n");
            
            Long complementaryFlightSchedulePlanId = createComplementaryReturnFlight(scanner, flight, newFlightSchedulePlan);
            if (complementaryFlightSchedulePlanId != null) {
                System.out.println("Return Flight Schedule Plan with ID: " + complementaryFlightSchedulePlanId + " has been created.\n");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void doCreateRecurrentByWeekFlightSchedulePlan(Scanner scanner, Flight flight) {
        try {
            System.out.println("*** Create Recurrent by Week Schedule ***\n");
              
            int recurrence = 7;
            
            System.out.print("Enter Departure Date and Time (day MONTH year hr:min AM/PM - eg: 12 Nov 23 03:30 PM)> ");
            String departureDateTimeString = scanner.nextLine().trim();
            Date departureDateTime = DATE_TIME_FORMAT.parse(departureDateTimeString);
            
            // Use SimpleDateFormat to format the day of the week
            SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEE");
            String dayOfWeek = dayOfWeekFormat.format(departureDateTime);

            System.out.println("Recurrent day of week chosen: " + dayOfWeek + ".");

            System.out.print("Enter Flight Duration (hr:min)> ");
            String estimatedFlightDurationString = scanner.nextLine().trim();
            Date estimatedFlightDuration = ESTIMATED_FLIGHT_DURATION_FORMAT.parse(estimatedFlightDurationString);

            System.out.print("Enter End Date (day MONTH year))> ");
            String endDateString = scanner.nextLine().trim();
            DateFormat endDateFormat = new SimpleDateFormat("dd MMM yy");
            Date endDate = endDateFormat.parse(endDateString);

            System.out.println("*** Create Recurrent by Day Flight Schedule :: Enter Fares ***\n");
            List<Fare> fares = createFares(scanner, flight);
            
            FlightSchedulePlan newFlightSchedulePlan = new FlightSchedulePlan(FlightScheduleEnum.RECURRENTWEEK, fares); 
            Long newFlightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewRecurrentFlightSchedulePlan(newFlightSchedulePlan, flight.getFlightId(), departureDateTime, estimatedFlightDuration, endDate, recurrence);

            System.out.println("Flight Schedule Plan with ID: " + newFlightSchedulePlanId + " has been created.\n");
            
            Long complementaryFlightSchedulePlanId = createComplementaryReturnFlight(scanner, flight, newFlightSchedulePlan);
            if (complementaryFlightSchedulePlanId != null) {
                System.out.println("Return Flight Schedule Plan with ID: " + complementaryFlightSchedulePlanId + " has been created.\n");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void viewAllFlightSchedulePlans() {
        System.out.println("*** FRSManagement :: Flight Operation Module :: View All Flight Schedule Plans ***\n");

        List<FlightSchedulePlan> flightSchedulePlans = flightSchedulePlanSessionBeanRemote.retrieveAllFlightSchedulePlans();
        if (flightSchedulePlans.isEmpty()) {
            System.out.println("No Available Flight Schedule Plans!\n");
        } else {

            for (FlightSchedulePlan flightSchedulePlan : flightSchedulePlans) {
                System.out.println(flightSchedulePlan);
                if (flightSchedulePlan.getComplementaryReturnSchedulePlan() != null) {
                    System.out.println("Complementary return schedule plan [ " + flightSchedulePlan.getComplementaryReturnSchedulePlan() + " ]");
                }
            }
            System.out.println();
        }
    }
    
    private void viewFlightSchedulePlanDetails() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("*** FRSManagement :: Flight Operation Module :: View Flight Schedule Plan Details ***\n");
            System.out.print("Enter Flight Number of the Flight Schedule Plan> ");
            String flightNumber = scanner.nextLine().trim();

            List<FlightSchedulePlan> flightSchedulePlans = flightSchedulePlanSessionBeanRemote.retrieveFlightSchedulePlansByFlightNumber(flightNumber);
            Integer selection = 1;
            for (FlightSchedulePlan flightSchedulePlan : flightSchedulePlans) {
                System.out.println("No." + selection + " " + flightSchedulePlan);
                selection++;
            }

            System.out.print("Enter no. to view details> ");
            Integer userSelection = Integer.valueOf(scanner.nextLine().trim());
            FlightSchedulePlan flightSchedulePlanSelected = flightSchedulePlans.get(userSelection - 1);

            System.out.println("\nFlight O-D pair=[" + flightSchedulePlanSelected.getFlight() + "]");
            System.out.println("Flight schedule: ");
            for (FlightSchedule flightSchedule : flightSchedulePlanSelected.getFlightSchedules()) {
                System.out.println("\t" + flightSchedule);
            }
            System.out.println("Fare: ");
            for (Fare fare : flightSchedulePlanSelected.getFares()) {
                System.out.println("\t" + fare);
            }

            System.out.print("Update details of this flight schedule plan? (Y/N)> ");
            if (scanner.nextLine().trim().equals("Y")) {
                updateFlightSchedulePlan(flightSchedulePlanSelected);
            }

            System.out.print("Delete this flight schedule plan? (Y/N)> ");
            if (scanner.nextLine().trim().equals("Y")) {
                flightSchedulePlanSessionBeanRemote.deleteFlightSchedulePlan(flightSchedulePlanSelected);
                System.out.println("Flight Schedule Plan deleted Successfully!");
            }
        } catch (FlightSchedulePlanNotFoundException | DeleteFlightScheduleException | ParseException | FlightScheduleExistException
                | GeneralException | FareExistException | FlightNotFoundException | DeleteFlightSchedulePlanException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public void  deleteFlightSchedulePlan(FlightSchedulePlan flightSchedulePlanSelected) {
        
        try {
        flightSchedulePlanSessionBeanRemote.deleteFlightSchedulePlan(flightSchedulePlanSelected);
        System.out.println("Flight Schedule Plan deleted Successfully!");
        } catch (FlightSchedulePlanNotFoundException | DeleteFlightSchedulePlanException ex){
            System.out.println("Error: " + ex.getMessage());
        }
        
    }
    
     private void updateFlightSchedulePlan(FlightSchedulePlan flightSchedulePlanSelected) throws DeleteFlightScheduleException, ParseException,
            FlightScheduleExistException, GeneralException, FareExistException, FlightNotFoundException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** FRSManagement :: Flight Operation Module :: Update Flight Schedule Plan ***\n");
        System.out.print("Enter Flight Number of Schedule Plan> ");
        String flightNumber = scanner.nextLine().trim();
        Flight flight = flightSessionBeanRemote.retrieveFlightByFlightNumber(flightNumber);

        System.out.println("Select Type of Update> ");
        System.out.println("1: Update Fares");
        System.out.println("2: Update Flight Schedules");
        System.out.print("> ");
        Integer response = Integer.valueOf(scanner.nextLine().trim());

        if (response == 1) {

            System.out.println("*** FRSManagement :: Flight Operation Module :: Update Flight Schedule Plan :: Update Fares ***\n");
            
//            FlightSchedulePlan chosenPlan = flightSchedulePlanSessionBeanRemote.retrieveFlightSchedulePlansByFlightNumber(flightNumber);
            
//            boolean option = true;
//            while (option) {
//                System.out.print("Enter Cabin Class Code> ");
//                String cabinClassCode = scanner.nextLine().trim();
//                System.out.print("Enter Fare Basis Code> ");
//                String fareBasisCode = scanner.nextLine().trim();
//                System.out.print("Enter Amount> $ ");
//                BigDecimal fareAmount =  scanner.nextBigDecimal();
//
//                CabinClassEnum cabinClassType;
//                if (cabinClassCode.charAt(0) == 'F') {
//                    cabinClassType = CabinClassEnum.FIRST;
//                } else if (cabinClassCode.charAt(0) == 'J') {
//                    cabinClassType = CabinClassEnum.BUSINESS;
//                } else if (cabinClassCode.charAt(0) == 'W') {
//                    cabinClassType = CabinClassEnum.PREMIUMECONOMY;
//                } else {
//                    cabinClassType = CabinClassEnum.ECONOMY;
//                }
//
//                Fare fare = new Fare(fareBasisCode, cabinClassType, fareAmount);
//                fares.add(fare);
//
//                System.out.print("Continue to create more fare? (Y/N)> ");
//                String optionString = scanner.nextLine().trim();
//                if (optionString.charAt(0) == 'N') {
//                    option = false;
//                }
//            }
        } else if (response == 2) {
            System.out.println("*** FRSManagement :: Flight Operation Module :: Update Flight Schedule Plan :: Update Flight Schedules***\n");

            String departureDateString;
            String departureTimeString;
            DateFormat departureTimeFormat = new SimpleDateFormat("hh:mm aa");
            DateFormat departureDateFormat = new SimpleDateFormat("dd MMM yy");
            Date departureDate;
            Date departureTime;
            String durationString;
            DateFormat durationFormat = new SimpleDateFormat("hh Hours mm Minute");
            Date durationTime;
            boolean option = true;
            Long firstDepartureTimeLong = Long.MIN_VALUE;
            FlightSchedulePlan newFlightSchedulePlan = new FlightSchedulePlan();
            Date endDateTime;
            Integer recurrence = 0;

            if (flightSchedulePlanSelected.getFlightScheduleType().equals(FlightScheduleEnum.SINGLE)) {
                try {
                    System.out.println("*** Update Single Flight Schedule ***\n");
                    System.out.print("Enter New Departure Date > ");
                    departureDateString = scanner.nextLine().trim();
                    departureDate = departureTimeFormat.parse(departureDateString);
                    
                    System.out.print("Enter New Departure Time > ");
                    departureTimeString = scanner.nextLine().trim();
                    departureTime = departureDateFormat.parse(departureTimeString);

                    System.out.print("Enter Flight Duration (hr:min)> ");
                    durationString = scanner.nextLine().trim();
                    durationTime = durationFormat.parse(durationString);

                    flightSchedulePlanSessionBeanRemote.updateSingleFlightSchedule(flightSchedulePlanSelected,
                            flightSchedulePlanSelected.getFlightSchedules().get(0), departureDate, departureTime, durationTime);
                    System.out.println("Flight Schedule Plan with ID: " + flightSchedulePlanSelected.getFlightSchedulePlanId() + " is created successfully!\n");
                } catch (UpdateFlightSchedulePlanException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }

            } else if (flightSchedulePlanSelected.getFlightScheduleType().equals(FlightScheduleEnum.MULTIPLE)) {

                try {
                    System.out.println("*** Update Multiple Flight Schedule ***\n");
                    System.out.println("*** Current Flight schedules Under The Plan ***\n");

                    Integer selection = 1;
                    for (FlightSchedule flightSchedule : flightSchedulePlanSelected.getFlightSchedules()) {
                        System.out.println("No." + selection + " " + flightSchedule);
                        selection++;
                    }

                    System.out.print("Enter no. to update the flight schedule> ");
                    Integer userSelection = Integer.valueOf(scanner.nextLine().trim());
                    FlightSchedule flightScheduleSelected = flightSchedulePlanSelected.getFlightSchedules().get(userSelection - 1);

                    System.out.print("Enter New Departure Date > ");
                    departureDateString = scanner.nextLine().trim();
                    departureDate = departureTimeFormat.parse(departureDateString);
                    
                    System.out.print("Enter New Departure Time > ");
                    departureTimeString = scanner.nextLine().trim();
                    departureTime = departureDateFormat.parse(departureTimeString);

                    System.out.print("Enter Flight Duration (hr:min)> ");
                    durationString = scanner.nextLine().trim();
                    durationTime = durationFormat.parse(durationString);

                    flightSchedulePlanSessionBeanRemote.updateSingleFlightSchedule(flightSchedulePlanSelected, flightScheduleSelected, departureDate, departureTime, durationTime);
                    System.out.println("Flight Schedule Plan with ID: " + flightSchedulePlanSelected.getFlightSchedulePlanId() + " is created successfully!\n");
                } catch (UpdateFlightSchedulePlanException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }

            } else if (flightSchedulePlanSelected.getFlightScheduleType().equals(FlightScheduleEnum.RECURRENTDAY)) {

                try {
                    System.out.print("Recurrence by how many days> ");
                    recurrence = Integer.valueOf(scanner.nextLine().trim());

                    System.out.print("Enter End Date (day MONTH year))> ");
                    String endDateString = scanner.nextLine().trim();
                    DateFormat endDateFormat = new SimpleDateFormat("dd MMM yy");
                    endDateTime = endDateFormat.parse(endDateString);

                    flightSchedulePlanSessionBeanRemote.updateRecurrentDayFlightSchedule(flightSchedulePlanSelected, recurrence, endDateTime);
                    System.out.println("Flight Schedule Plan with ID: " + flightSchedulePlanSelected.getFlightSchedulePlanId() + " is created successfully!\n");
                } catch (UpdateFlightSchedulePlanException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }

            } else if (flightSchedulePlanSelected.getFlightScheduleType().equals(FlightScheduleEnum.RECURRENTWEEK)) {
                try {
                    System.out.print("Enter End Date (day MONTH year))> ");
                    String endDateString = scanner.nextLine().trim();
                    DateFormat endDateFormat = new SimpleDateFormat("dd MMM yy");
                    endDateTime = endDateFormat.parse(endDateString);

                    flightSchedulePlanSessionBeanRemote.updateRecurrentWeekFlightSchedule(flightSchedulePlanSelected, endDateTime);
                    System.out.println("Flight Schedule Plan with ID: " + flightSchedulePlanSelected.getFlightSchedulePlanId() + " is created successfully!\n");
                } catch (UpdateFlightSchedulePlanException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        }
    }
    
}
