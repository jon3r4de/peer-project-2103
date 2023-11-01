/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package frsreservationclient;

import ejb.session.stateless.CustomerSessionBeanRemote;
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
    private static CustomerSessionBeanRemote customerSessionBeanRemote;
    
    public static void main(String[] args) {
        // TODO code application logic here
         MainApp mainApp = new MainApp(customerSessionBeanRemote);
        
        System.out.println("deployed2");
        mainApp.runApp();
    }
    
}
