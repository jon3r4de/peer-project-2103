/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Employee;
import enumeration.EmployeeEnum;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import util.exception.EmployeeNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author jonang
 */
@Stateless
public class EmployeeSessionBean implements EmployeeSessionBeanRemote, EmployeeSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;
    
    @Override
    public Employee doLogin(String username, String password) throws InvalidLoginCredentialException {
        System.out.println("debug 2");
        
        try {
            System.out.println("debug 3");
            Employee employee = retrieveEmployeebyUserName(username);
            System.out.println("debug 4");
            if (employee.getPassword().equals(password)) {
                System.out.println("debug 5");
                return employee;
            } else {
                System.out.println("debug 6");
                throw new InvalidLoginCredentialException("Password is incorrect, please try again!");
            }
        } catch (EmployeeNotFoundException ex) {
            throw new InvalidLoginCredentialException(ex.getMessage());
        } catch (InvalidLoginCredentialException ex) {
            throw ex;
        }
    }
    
    @Override
    public Employee createEmployee(String firstName, String lastName, String username, String password, EmployeeEnum userRole) throws UnknownPersistenceException {
            Employee newEmployee = new Employee(firstName, lastName, username, password, userRole);
        try {
            em.persist(newEmployee);
            em.flush();
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
        
        return newEmployee;
    }
    
    private Employee retrieveEmployeebyUserName(String username) throws EmployeeNotFoundException {
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.username = :inputUsername");
        query.setParameter("inputUsername", username);

        try {
            return (Employee) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new EmployeeNotFoundException("Employee UserName " + username + " does not exist!");
        }
    }


}
