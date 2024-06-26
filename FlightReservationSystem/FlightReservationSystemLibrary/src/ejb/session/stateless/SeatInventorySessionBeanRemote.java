/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.SeatInventory;
import javax.ejb.Remote;

/**
 *
 * @author jonang
 */
@Remote
public interface SeatInventorySessionBeanRemote {

    public SeatInventory searchForSeatInvenotry(SeatInventory seatInventory);

    public SeatInventory adjustSeatCapacity(SeatInventory seatInventory);
    
}
