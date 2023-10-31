/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package frsmanagementclient;

import ejb.session.stateless.EmployeeSessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author jonang
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    @EJB
    private static EmployeeSessionBeanRemote employeeSessionBeanRemote;
    
    public static void main(String[] args) {
        // TODO code application logic here
         MainApp mainApp = new MainApp(employeeSessionBeanRemote);
        
        System.out.println("deployed");
        mainApp.runApp();
    }
    
}
