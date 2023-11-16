/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Partner;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidLoginCredentialException;
import util.exception.PartnerNotFoundException;

/**
 *
 * @author tristan
 */
@Stateless
public class PartnerSessionBean implements PartnerSessionBeanRemote, PartnerSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public Partner login(String username, String password) throws InvalidLoginCredentialException {
        try {
            Partner p = this.retrievePartnerByUsername(username);
            if (p.getPassword().equals(password)) {
                return p;
            } else {
                throw new InvalidLoginCredentialException("Incorrect password.");
            }
        } catch (PartnerNotFoundException ex) {
            throw new InvalidLoginCredentialException(ex.getMessage());
        }
    }
    
    private Partner retrievePartnerByUsername(String username) throws PartnerNotFoundException {
        PartnerNotFoundException noPartnerException = new PartnerNotFoundException("No partner with that username exists.");
    
        try {
            Query query = em.createQuery("SELECT p FROM Partner p WHERE p.username = :username");
            query.setParameter("username", username);
        
            // Execute the query and retrieve the result
            Partner result = (Partner) query.getSingleResult();

            // Check if there's a matching customer
            if (result != null) {
                return result;
            } else {
                // No customer found with the provided username
                throw noPartnerException;
            }
        } catch (NoResultException ex) {
            // Handle the case where no result is found
            throw noPartnerException;
        }
    }
    
}
