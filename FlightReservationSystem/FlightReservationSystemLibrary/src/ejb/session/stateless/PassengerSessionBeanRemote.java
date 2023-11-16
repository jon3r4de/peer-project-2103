/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Passenger;
import javax.ejb.Remote;

/**
 *
 * @author jonang
 */
@Remote
public interface PassengerSessionBeanRemote {

    public Passenger createPassenger(Passenger passenger);
    
}
