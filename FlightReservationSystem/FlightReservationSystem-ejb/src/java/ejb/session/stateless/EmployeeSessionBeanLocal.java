/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Employee;
import enumeration.EmployeeEnum;
import javax.ejb.Local;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author jonang
 */
@Local
public interface EmployeeSessionBeanLocal {
    
     public Employee doLogin(String username, String password) throws InvalidLoginCredentialException;
     
      public Employee createEmployee(String firstName, String lastName, String username, String password, EmployeeEnum userRole) throws UnknownPersistenceException;
    
}
