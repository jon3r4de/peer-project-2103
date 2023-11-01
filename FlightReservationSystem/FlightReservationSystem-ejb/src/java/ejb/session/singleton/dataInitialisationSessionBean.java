/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB31/SingletonEjbClass.java to edit this template
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeSessionBeanLocal;
import entity.Employee;
import enumeration.EmployeeEnum;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author jonang
 */

@Singleton
@LocalBean
@Startup
public class dataInitialisationSessionBean {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    @EJB(name = "EmployeeSessionBeanLocal")
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;
    
    @PostConstruct
    public void postConstruct()
    {
        System.out.println("initialised!!!!!!!!!!");
        if(em.find(Employee.class, 1L) == null)
        {
            doDataInitialisation();
        }
    }
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    private void doDataInitialisation() {

        try {
            employeeSessionBeanLocal.createEmployee("bonobo","toot","FLEETMANAGER","password", EmployeeEnum.FLEETMANAGER);
            employeeSessionBeanLocal.createEmployee("bonobo1","toot","ROUTEPLANNER","password", EmployeeEnum.ROUTEPLANNER);
            employeeSessionBeanLocal.createEmployee("bonobo2","toot","SCHEDULEMANAGER","password", EmployeeEnum.SCHEDULEMANAGER);
            employeeSessionBeanLocal.createEmployee("bonobo3","toot","SALESMANAGER","password", EmployeeEnum.SALESMANAGER);
            employeeSessionBeanLocal.createEmployee("bonobo4","toot","SYSTEMADMINISTRATOR","password", EmployeeEnum.SYSTEMADMINISTRATOR);
        } catch (UnknownPersistenceException ex) {
            System.out.println("An error has occurred.");
        }
        
    }
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
