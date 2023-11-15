/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author jonang
 */
@Stateless
public class CustomerSessionBean implements CustomerSessionBeanRemote, CustomerSessionBeanLocal {
    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public CustomerSessionBean() {
    }
    
    @Override
    public Long registerCustomer(Customer c) throws UnknownPersistenceException {
        try {
            em.persist(c);
            em.flush();
            return c.getCustomerId(); 
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }
    
    @Override
    public Customer login(String username, String password) throws InvalidLoginCredentialException {
        try {
            Customer c = this.retrieveCustomerByUsername(username);
            if (c.getPassword().equals(password)) {
                return c;
            } else {
                throw new InvalidLoginCredentialException("Incorrect password.");
            }
        } catch (CustomerNotFoundException ex) {
            throw new InvalidLoginCredentialException(ex.getMessage());
        }
    }
    
    private Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException {
        CustomerNotFoundException noCustomerException = new CustomerNotFoundException("No customer with that username exists.");
    
        try {
            Query query = em.createQuery("SELECT c FROM Customer c WHERE c.username = :username");
            query.setParameter("username", username);
        
            // Execute the query and retrieve the result
            Customer result = (Customer) query.getSingleResult();

            // Check if there's a matching customer
            if (result != null) {
                return result;
            } else {
                // No customer found with the provided username
                throw noCustomerException;
            }
        } catch (NoResultException ex) {
            // Handle the case where no result is found
            throw noCustomerException;
        }
    }
    
    @Override
    public Customer retrieveCustomerById(Long custId) {
        Customer managedCustomer = em.find(Customer.class, custId);
        
        managedCustomer.getReservations().size();
        return managedCustomer;
    }
}
