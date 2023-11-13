/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.FlightScheduleSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import entity.CabinClass;
import entity.FlightSchedule;
import entity.Passenger;
import entity.Reservation;
import enumeration.CabinClassEnum;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author jonang
 */
public class SalesManagementModule {
    
    private FlightSessionBeanRemote flightSessionBeanRemote;

    private FlightScheduleSessionBeanRemote flightScheduleSessionBeanRemote;
    
    public SalesManagementModule() {
    }

    public SalesManagementModule(FlightSessionBeanRemote flightSessionBeanRemote, FlightScheduleSessionBeanRemote flightScheduleSessionBeanRemote) {
        this.flightSessionBeanRemote = flightSessionBeanRemote;
        this.flightScheduleSessionBeanRemote = flightScheduleSessionBeanRemote;
    }
    
    public void menuSalesManagement() {
        Scanner scanner = new Scanner(System.in);
        int response = 0;

        while (true) {
            System.out.println("*** FRS Management :: Sales Management Module ***\n");
            System.out.println("1: View Seats Inventory ");
            System.out.println("2: View Flight Reservations");
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

        }
    }
        
    public void viewSeatInventory() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** FRS Management :: Sales Management Module :: View Seats Inventory ***\n");
        try {
            System.out.print("Enter Flight Number> ");
            String flightNumber = scanner.nextLine().trim();
            List<FlightSchedule> flightSchedules = flightSessionBeanRemote.retrieveFlightSchedulesByFlightNumber(flightNumber);
            
            System.out.println("Flight schedules for flight: " + flightNumber);
            System.out.printf("%-20s%-20s%-20s\n", "Index", "Departure Date Time", "Arrival Date Time");
            System.out.println("--------------------------------------------------------------------------------------------");

            int choice;
            while (true) {
                int index = 1;
                for (FlightSchedule fs : flightSchedules) {
                    DateFormat dateTimeFormat = new SimpleDateFormat("dd MMM yy hh:mm aa");
                    String departureDateTimeString = dateTimeFormat.format(fs.getDepartureDateTime());
                    String arrivalDateTimeString = dateTimeFormat.format(fs.getArrivalDateTime());

                    System.out.printf("%-20s%-20s%-20s\n", index, departureDateTimeString, arrivalDateTimeString);
                    System.out.println();
                    index++;
                }
                System.out.print("Choose available flight schedules> ");
                choice = scanner.nextInt();
                if (choice - 1 > flightSchedules.size()) {
                    System.out.println("Invalid flight schedule chosen. Try again.");
                } else {
                    break;
                }
            }
            
            scanner.next();
            FlightSchedule chosenFs = flightSchedules.get(choice - 1);
            List<CabinClass> ccInFs = chosenFs.getCabinClasses();
            
            Integer totalAvailSeats = 0;
            Integer totalResSeats = 0;
            Integer totalBalSeats = 0;
            
            for (CabinClass cc : ccInFs) {
                System.out.println("Seats Balance for Cabin Class: " + cc.getCabinClassType());
                System.out.printf("%-20s%-20s%-20s\n", "Available Seats", "Reserved Seats", "Balance Seats");
                System.out.println("--------------------------------------------------------------------------------------------");
                
                Integer availSeats = cc.getAvailableSeats();
                Integer resSeats = cc.getReservedSeats();
                Integer balSeats = cc.getBalanceSeats();
                System.out.printf("%-20s%-20s%-20s\n", availSeats, resSeats, balSeats);
                System.out.println();
                totalAvailSeats += availSeats;
                totalResSeats += resSeats;
                totalBalSeats += balSeats;
            }
            
            System.out.println("Total Seats Balance for All Cabin Classes");
            System.out.printf("%-20s%-20s%-20s\n", "Available Seats", "Reserved Seats", "Balance Seats");
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.printf("%-20s%-20s%-20s\n", totalAvailSeats, totalResSeats, totalBalSeats);
            System.out.println();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void viewFlightReservations() {
//        Scanner scanner = new Scanner(System.in);
//        
//        System.out.println("*** FRS Management :: Sales Management Module :: View Flight Reservations ***\n");
//        try {
//            System.out.print("Enter Flight Number> ");
//            String flightNumber = scanner.nextLine().trim();
//            List<FlightSchedule> flightSchedules = flightSessionBeanRemote.retrieveFlightSchedulesByFlightNumber(flightNumber);
//            
//            System.out.println("Flight schedules for flight: " + flightNumber);
//            System.out.printf("%-20s%-20s%-20s\n", "Index", "Departure Date Time", "Arrival Date Time");
//            System.out.println("--------------------------------------------------------------------------------------------");
//
//            int choice;
//            while (true) {
//                int index = 1;
//                for (FlightSchedule fs : flightSchedules) {
//                    DateFormat dateTimeFormat = new SimpleDateFormat("dd MMM yy hh:mm aa");
//                    String departureDateTimeString = dateTimeFormat.format(fs.getDepartureDateTime());
//                    String arrivalDateTimeString = dateTimeFormat.format(fs.getArrivalDateTime());
//
//                    System.out.printf("%-20s%-20s%-20s\n", index, departureDateTimeString, arrivalDateTimeString);
//                    System.out.println();
//                    index++;
//                }
//                System.out.print("Choose available flight schedules> ");
//                choice = scanner.nextInt();
//                if (choice - 1 > flightSchedules.size()) {
//                    System.out.println("Invalid flight schedule chosen. Try again.");
//                } else {
//                    break;
//                }
//            }
//            
//            scanner.next();
//            FlightSchedule chosenFs = flightSchedules.get(choice - 1);
//            
//            List<Reservation> reservations = flightScheduleSessionBeanRemote.viewReservations(chosenFs);
//            List<CabinClass> fsCcs = chosenFs.getCabinClasses();
//            boolean economyTrue = false;
//            boolean firstTrue = false;
//            boolean businessTrue = false;
//            boolean premEcoTrue = false;
//            
//            for (CabinClass cc : fsCcs) {
//                if (cc.getCabinClassType() == CabinClassEnum.ECONOMY) {
//                    economyTrue = true;
//                } else if (cc.getCabinClassType() == CabinClassEnum.FIRST) {
//                    firstTrue = true;
//                } else if (cc.getCabinClassType() == CabinClassEnum.BUSINESS) {
//                    businessTrue = true;
//                } else if (cc.getCabinClassType() == CabinClassEnum.PREMIUMECONOMY) {
//                    premEcoTrue = true;
//                }
//            }
//            
//            if (economyTrue) {
//                System.out.println("Reservations for Cabin Class: Economy");
//                System.out.printf("%-20s%-20s%-20s\n", "Reservation Id", "Seat Number", "Passenger Name");
//                System.out.println("--------------------------------------------------------------------------------------------");
//                List<Reservation> economyClassReservations = reservations.stream()
//                    .filter(reservation -> reservation.getPassengers() != null &&
//                            reservation.getPassengers().stream()
//                                    .anyMatch(passenger -> passenger.getSeat() != null &&
//                                            passenger.getSeat().getCabinClass() != null &&
//                                            passenger.getSeat().getCabinClass().getCabinClassType() == CabinClassEnum.ECONOMY))
//                    .collect(Collectors.toList());
//                
//                printReservations(economyClassReservations, CabinClassEnum.ECONOMY);
//                System.out.println();
//            }
//            
//            if (firstTrue) {
//                System.out.println("Reservations for Cabin Class: First");
//                System.out.printf("%-20s%-20s%-20s\n", "Reservation Id", "Seat Number", "Passenger Name");
//                System.out.println("--------------------------------------------------------------------------------------------");
//                List<Reservation> firstClassReservations = reservations.stream()
//                    .filter(reservation -> reservation.getPassengers() != null &&
//                            reservation.getPassengers().stream()
//                                    .anyMatch(passenger -> passenger.getSeat() != null &&
//                                            passenger.getSeat().getCabinClass() != null &&
//                                            passenger.getSeat().getCabinClass().getCabinClassType() == CabinClassEnum.FIRST))
//                    .collect(Collectors.toList());
//                
//                printReservations(firstClassReservations, CabinClassEnum.FIRST);
//                System.out.println();
//            }
//            
//            if (businessTrue) {
//                System.out.println("Reservations for Cabin Class: Business");
//                System.out.printf("%-20s%-20s%-20s\n", "Reservation Id", "Seat Number", "Passenger Name");
//                System.out.println("--------------------------------------------------------------------------------------------");
//                List<Reservation> businessClassReservations = reservations.stream()
//                    .filter(reservation -> reservation.getPassengers() != null &&
//                            reservation.getPassengers().stream()
//                                    .anyMatch(passenger -> passenger.getSeat() != null &&
//                                            passenger.getSeat().getCabinClass() != null &&
//                                            passenger.getSeat().getCabinClass().getCabinClassType() == CabinClassEnum.BUSINESS))
//                    .collect(Collectors.toList());
//                
//                printReservations(businessClassReservations, CabinClassEnum.BUSINESS);
//                System.out.println();
//            }
//            
//            if (premEcoTrue) {
//                System.out.println("Reservations for Cabin Class: Premium Economy");
//                System.out.printf("%-20s%-20s%-20s\n", "Reservation Id", "Seat Number", "Passenger Name");
//                System.out.println("--------------------------------------------------------------------------------------------");
//                List<Reservation> premEcoReservations = reservations.stream()
//                    .filter(reservation -> reservation.getPassengers() != null &&
//                            reservation.getPassengers().stream()
//                                    .anyMatch(passenger -> passenger.getSeat() != null &&
//                                            passenger.getSeat().getCabinClass() != null &&
//                                            passenger.getSeat().getCabinClass().getCabinClassType() == CabinClassEnum.PREMIUMECONOMY))
//                    .collect(Collectors.toList());
//                
//                printReservations(premEcoReservations, CabinClassEnum.PREMIUMECONOMY);
//                System.out.println();
//            }
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
    }
    
//    private void printReservations(List<Reservation> reservations, CabinClassEnum cabinClassType) {
//        for (Reservation r : reservations) {
//            Long reservationId = r.getReservationId();
//            List<Passenger> passengers = r.getPassengerList();
//            for (Passenger p : passengers) {
//                if (p.getSeat().getCabinClass().getCabinClassType() == cabinClassType) {
//                    String seatNum = p.getSeat().getSeatNumber();
//                    String passName = p.getFirstName() + " " + p.getLastName();
//                    System.out.printf("%-20s%-20s%-20s\n", reservationId, seatNum, passName);
//                    System.out.println();
//                }
//            }
//        }
//    }
}
