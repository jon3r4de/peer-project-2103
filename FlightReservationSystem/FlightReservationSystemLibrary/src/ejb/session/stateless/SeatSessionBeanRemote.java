/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Seat;
import javax.ejb.Remote;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author tristan
 */
@Remote
public interface SeatSessionBeanRemote {
    public Long createSeat(Seat seat, Long cabinClassId) throws UnknownPersistenceException;
}
