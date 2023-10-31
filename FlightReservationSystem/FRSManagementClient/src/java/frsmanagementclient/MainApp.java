/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.EmployeeSessionBeanRemote;
import entity.Employee;
import java.util.Scanner;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author jonang
 */
public class MainApp {
    
    private Employee employee;
    
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;
    
    public MainApp(EmployeeSessionBeanRemote employeeSessionBeanRemote) {
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
    }
    
public void runApp() {
        Scanner scanner = new Scanner(System.in);
        int response;
        
        while (true) {
            System.out.println("*** Welcome to frs management client system ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");

            response = 0;

            while (response < 1 || response > 2) {
                System.out.print("> ");

                if (scanner.hasNextInt()) {
                    response = scanner.nextInt();

                    if (response == 1) {
                        try {
                            System.out.println("Please enter your username:");
                            scanner.nextLine(); // Consume the newline character
                            String username = scanner.nextLine();
                            System.out.println("Please enter your password:");
                            String password = scanner.nextLine();
                            
                            Employee currentEmployee = this.employeeSessionBeanRemote.doLogin(username, password);
                            this.employee = currentEmployee;
                            
                            System.out.println("Login successful. Welcome, " + currentEmployee.getUsername() + "!");
                            this.loggedInView();
                        } catch (InvalidLoginCredentialException ex) { //will be thrown by login 
                            System.out.println("Invalid login credential: " + ex.getMessage());
                        }
                    } else if (response == 2) {
                        System.out.println("Exiting the application.");
                        break;
                    } else {
                        System.out.println("Invalid option, please try again!");
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
        //1 create new employee
        //2 create new partner
        //3 create new Airport
        //4 create new aircraft type
        //5 Employee Logout 
        //6 create new AirCraft configuration
        //7 View all aircraft configuration
        //8 View aircraft configuration detail
        //9 create new flight route 
        //10 view all flight routes
        //11 delete flight route 
        //12 create new flight record
        //13 view all flights
        //14 view flight details
        //15 update flight 
        //16 delete flight
        //17 create flight schedule plan
        //18 view all flight schedule plans
        //19 view  flight schedule plan detail 
        //20 update flight schedule plan
        //21 delete flight schedule plan
        //22 view seat inventory 
        //23 view flight reservation
        
    }
    
}
