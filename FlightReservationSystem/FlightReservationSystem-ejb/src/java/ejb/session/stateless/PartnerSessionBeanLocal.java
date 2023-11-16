/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Partner;
import javax.ejb.Local;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author tristan
 */
@Local
public interface PartnerSessionBeanLocal {
    public Partner login(String username, String password) throws InvalidLoginCredentialException;
}
