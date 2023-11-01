/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Remote;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author jonang
 */
@Remote
public interface CustomerSessionBeanRemote {
    public Long registerCustomer(Customer c) throws UnknownPersistenceException;
    public Customer login(String username, String password) throws InvalidLoginCredentialException;
}
