/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Local;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author jonang
 */
@Local
public interface CustomerSessionBeanLocal {
    public Long registerCustomer(Customer c) throws UnknownPersistenceException;
    public Customer login(String username, String password) throws InvalidLoginCredentialException;
}
