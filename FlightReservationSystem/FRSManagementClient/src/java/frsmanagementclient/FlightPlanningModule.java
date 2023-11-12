/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.AircraftconfigSessionBeanRemote;
import ejb.session.stateless.CabinClassSessionBeanRemote;
import ejb.session.stateless.FlightRouteSessionBeanRemote;
import ejb.session.stateless.aircraftTypeSessionBeanRemote;
import entity.AirCraftConfig;
import entity.AirCraftType;
import entity.CabinClass;
import entity.FlightRoute;
import enumeration.CabinClassEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import util.exception.AirCraftTypeNotFoundException;
import util.exception.AircraftConfigExistExcetpion;
import util.exception.AircraftConfigNotFoundException;
import util.exception.AirportNotFoundException;
import util.exception.DeleteFlightRouteException;
import util.exception.ExceedMaximumSeatCapacityException;
import util.exception.FlightRouteExistException;
import util.exception.FlightRouteNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author jonang
 */
public class FlightPlanningModule {
    
    private aircraftTypeSessionBeanRemote aircraftTypeSessionBeanRemote;
    
    private AircraftconfigSessionBeanRemote aircraftconfigSessionBeanRemote;
    
    private FlightRouteSessionBeanRemote flightRouteSessionBeanRemote;
    
    private CabinClassSessionBeanRemote cabinClassSessionBeanRemote;
    
    public FlightPlanningModule(aircraftTypeSessionBeanRemote aircraftTypeSessionBeanRemote, AircraftconfigSessionBeanRemote aircraftconfigSessionBeanRemote, 
            FlightRouteSessionBeanRemote flightRouteSessionBeanRemote, CabinClassSessionBeanRemote cabinClassSessionBeanRemote) {
        this.aircraftTypeSessionBeanRemote = aircraftTypeSessionBeanRemote;
        this.aircraftconfigSessionBeanRemote = aircraftconfigSessionBeanRemote;
        this.flightRouteSessionBeanRemote = flightRouteSessionBeanRemote;
        this.cabinClassSessionBeanRemote = cabinClassSessionBeanRemote;
    }
    
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
                        try {
                        createAircraftConfig();
                        } catch (ExceedMaximumSeatCapacityException ex) {
                        System.out.println("The New Aircraft Configuration Exceed Maximum Seat Capacity of the aircraft");
                        } catch (AirCraftTypeNotFoundException ex) {
                            System.out.println(ex);
                        }
                        break;
                    case 2:
                        viewAllAircraftConfig();
                        break;
                    case 3:
                        try{
                        viewAircraftConfigDetails();
                        } catch (AircraftConfigNotFoundException ex) {
                            System.out.println(ex);
                        }
                        break;
                    case 4:
                        System.out.println("Exiting the application.");
                        return; // Exiting the switch after handling exit
                    default:
                        System.out.println("Invalid option, please try again!\n");
                        break;
                }
            }
        }
    }
    //
    private void createAircraftConfig() throws ExceedMaximumSeatCapacityException, AirCraftTypeNotFoundException {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter Aircraft Configuration Name> ");
        String aircraftConfigurationName = scanner.nextLine().trim();
        System.out.print("Enter Aircraft Type Name> ");
        String aircraftTypeName = scanner.nextLine().trim();
        System.out.print("Enter Aircraft Capacity> ");
        Integer aircraftCapacity = Integer.valueOf(scanner.nextLine().trim());
        
        AirCraftType aircraftType = aircraftTypeSessionBeanRemote.retrieveAircraftTypeByName(aircraftTypeName);
        
        if (aircraftType.getMaxPassengerSeatCapacity() < aircraftCapacity) {
            System.out.println("Current configuration exceeds the maximum capacity of the " + aircraftType.getAircraftTypeName() + "!\n");
        } else {
            
            Integer numOfCabinClasses = 0;
            while (numOfCabinClasses < 1 || numOfCabinClasses > 4) {
                System.out.print("Enter Number of Cabin Classes (1 to 4)> ");
                numOfCabinClasses = Integer.valueOf(scanner.nextLine().trim());
            }
            
            
        //Create each cabin class configuration
        List<CabinClass> cabinClasses = new ArrayList<>();
        for (int i = 0; i < numOfCabinClasses; i++) {
            
            System.out.print("Select Cabin Class Code to be created (F,J,W,Y) > ");
            String cabinClassCode = scanner.nextLine().trim();
            System.out.print("Enter Number of Aisles> ");
            Integer numOfAisles = Integer.valueOf(scanner.nextLine().trim());
            System.out.print("Enter Number of Rows> ");
            Integer numOfRows = Integer.valueOf(scanner.nextLine().trim());
            System.out.print("Enter Number of Seats Abreast> ");
            Integer numOfSeatsAbreast = Integer.valueOf(scanner.nextLine().trim());
            System.out.print("Enter Seating Configuration Per Column> ");
            String seatingConfigurationPerColumn = scanner.nextLine().trim();
            System.out.print("Enter Capacity of the Cabin Class> ");
            Integer cabinClassCapacity = Integer.valueOf(scanner.nextLine().trim());

            
            if (cabinClassCode.charAt(0) == 'F') {
            cabinClasses.add(new CabinClass(numOfAisles, numOfRows, numOfSeatsAbreast, seatingConfigurationPerColumn, cabinClassCapacity, CabinClassEnum.FIRST));
            } else if (cabinClassCode.charAt(0) == 'J') {
            cabinClasses.add(new CabinClass(numOfAisles, numOfRows, numOfSeatsAbreast, seatingConfigurationPerColumn, cabinClassCapacity,CabinClassEnum.BUSINESS));
            } else if (cabinClassCode.charAt(0) == 'W') {
            cabinClasses.add(new CabinClass(numOfAisles, numOfRows, numOfSeatsAbreast, seatingConfigurationPerColumn, cabinClassCapacity, CabinClassEnum.PREMIUMECONOMY));
            } else if (cabinClassCode.charAt(0) == 'Y') {
            cabinClasses.add(new CabinClass(numOfAisles, numOfRows, numOfSeatsAbreast, seatingConfigurationPerColumn, cabinClassCapacity, CabinClassEnum.ECONOMY));
            }
           }
            
            Long aircraftConfigurationId;
            AirCraftConfig newAircraftConfiguration = new AirCraftConfig(aircraftConfigurationName, numOfCabinClasses, aircraftCapacity);

            try {
                aircraftConfigurationId = aircraftconfigSessionBeanRemote.createNewAircraftConfiguration(newAircraftConfiguration, aircraftType, cabinClasses);
                System.out.println("\nAircraft Confirguration With ID: " + aircraftConfigurationId + " is created successfully!\n");
            } catch (AircraftConfigExistExcetpion | GeneralException ex) {
                System.out.println("Error: " + ex.getMessage());
            } 
        }  
    }
    
    public void viewAllAircraftConfig() {
        
        List<AirCraftConfig> aircraftConfigurations = aircraftconfigSessionBeanRemote.retrieveAllAircraftConfigurations();
        if (aircraftConfigurations.isEmpty()) {
            System.out.println("No Available Aircraft Configurations!\n");
        } else {
            for (AirCraftConfig aircraftConfiguration : aircraftConfigurations) {
                System.out.println(aircraftConfiguration + "\n");
            }
        }
    }
    
    public void viewAircraftConfigDetails() throws AircraftConfigNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Aircraft Configuration Name> ");
        String nameOfAircraftConfiguration = scanner.nextLine().trim();
        AirCraftConfig aircraftConfiguration = aircraftconfigSessionBeanRemote.retrieveAircraftConfigurationByName(nameOfAircraftConfiguration);
        System.out.println();
        System.out.println(aircraftConfiguration);
        for (CabinClass cabinClass: aircraftConfiguration.getCabinClasses()) {
            System.out.println("\t" + cabinClass);
        }
        System.out.println();
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

        }
    }
    
    //

    
    private void viewAllFlightRoutes() {
        
        List<FlightRoute> flightRoutes = flightRouteSessionBeanRemote.retrieveAllFlightRoutes();
        
        if (flightRoutes.isEmpty()) {
            System.out.println("No Available Flight Routes!\n");
        } else {
            //System.out.println("*** FRSManagement :: Flight Planning Module :: View All Flight Routes ***\n");
            for (FlightRoute flightRoute : flightRoutes) {
               
                /*if (flightRoute.isDisabled()) {
                    System.out.println(flightRoute + " [Disabled]");
                } else {
                    System.out.println(flightRoute);
                }*/

                System.out.println(flightRoute.isDisabled() ? flightRoute + " [Disabled]" : flightRoute);

            
                if (flightRoute.getComplementaryReturn() != null) {
                    System.out.println("[Complementary " + flightRoute.getComplementaryReturn()+ "]");
                }
            }
        }
        
        System.out.println();
    }
    
    public void deleteFlightRoutes() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Origin Airport IATA code> ");
        String originCode = scanner.nextLine().trim();

        System.out.print("Enter Destination Airport IATA code> ");
        String destinationCode = scanner.nextLine().trim();

        try {
            Long flightRouteId = flightRouteSessionBeanRemote.deleteFlightRoute(originCode, destinationCode);
            System.out.println("The Flight Route " + flightRouteId + " is deleted successfully!\n");
            
        } catch (DeleteFlightRouteException ex) {
            System.out.println("Flight route from " + originCode + " to " + destinationCode + " is set disabled!");
        } catch (FlightRouteNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
    }
    
    public void createFlightRoutes() {
        try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.print("Enter Origin Airport IATA code> ");
            String originIataCode = scanner.nextLine().trim();
            
            System.out.print("Enter Destination Airport IATA code> ");
            String destinationIataCode = scanner.nextLine().trim();
            
            Long newFlightRouteId = flightRouteSessionBeanRemote.createNewFlightRoute(originIataCode, destinationIataCode);
            System.out.println("Flight route " + newFlightRouteId + " is created!\n");
            
            System.out.print("Create complementary flight route? (Y/N)> ");
            String response = scanner.nextLine().trim();
            if ("Y".equals(response)) {
                Long newComplementaryFlightRouteId = flightRouteSessionBeanRemote.createNewFlightRoute(destinationIataCode, originIataCode);
                flightRouteSessionBeanRemote.associateComplementaryFlightRoute(newFlightRouteId, newComplementaryFlightRouteId);
                System.out.println("Complementary flight route " + newComplementaryFlightRouteId + " is created!\n");       
            }
        } catch (FlightRouteNotFoundException | GeneralException | AirportNotFoundException | FlightRouteExistException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
    }
    
}
