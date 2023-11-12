/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB31/SingletonEjbClass.java to edit this template
 */
package ejb.session.singleton;

import ejb.session.stateless.AirportSessionBeanLocal;
import ejb.session.stateless.EmployeeSessionBeanLocal;
import ejb.session.stateless.aircraftTypeSessionBeanLocal;
import entity.AirCraftConfig;
import entity.AirCraftType;
import entity.Airport;
import entity.Employee;
import enumeration.EmployeeEnum;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.AircraftTypeExistException;
import util.exception.AirportExistException;
import util.exception.GeneralException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author jonang
 */

@Singleton
@LocalBean
//@Startup
public class dataInitialisationSessionBean {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    @EJB(name = "EmployeeSessionBeanLocal")
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;
    
    @EJB(name = "AirportSessionBeanLocal")
    private AirportSessionBeanLocal airportSessionBeanLocal;
    
    
    @EJB(name = "aircraftTypeSessionBeanLocal")
    private aircraftTypeSessionBeanLocal aircraftTypeSessionBeanLocal;
    
    
    //@PostConstruct
    public void postConstruct()
    {
        if(em.find(Employee.class, 1L) == null)
        {
            doInitialiseEmployee();
            System.out.println("initialised employee");
        } 
        
        if(em.find(Airport.class, 1l) == null)
        {
            doInitialiseAirport();
            System.out.println("initialised airport");
        }
        
        if(em.find(AirCraftType.class, 1l) == null)
        {
            doInitialiseAircraftType();
            System.out.println("initialised aircraft type");
        }
        
        //singleton.postConstruct();
    }
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    private void doInitialiseEmployee() {

        try {
            System.out.println("created employee");
            employeeSessionBeanLocal.createEmployee("Fleet","Manager","fleetmanager","password", EmployeeEnum.FLEETMANAGER);
            employeeSessionBeanLocal.createEmployee("Route","Planner","routeplanner","password", EmployeeEnum.ROUTEPLANNER);
            employeeSessionBeanLocal.createEmployee("Schedule","Manager","schedulemanager","password", EmployeeEnum.SCHEDULEMANAGER);
            employeeSessionBeanLocal.createEmployee("Sales","Manaer","salesmanager","password", EmployeeEnum.SALESMANAGER);
            employeeSessionBeanLocal.createEmployee("System ","administrator","SYSTEMADMINISTRATOR","password", EmployeeEnum.SYSTEMADMINISTRATOR);
        } catch (UnknownPersistenceException ex) {
            System.out.println("An error has occurred.");
        }
        
    }
    
    //String airportName, String iataAirportcode, String city, String stateOrProvince, String country
    private void doInitialiseAirport() {
        try {
            System.out.println("created airport");
            airportSessionBeanLocal.createNewAirport(new Airport("Changi", "SIN", "Singapore", "Singapore", "Singapore"));
            airportSessionBeanLocal.createNewAirport(new Airport("Narita", "NRT", "Narita", "Chiba", "Japan"));
            airportSessionBeanLocal.createNewAirport(new Airport("Hong Kong", "HKG", "Chek Lap Kok", "Hong Kong", "China"));
            airportSessionBeanLocal.createNewAirport(new Airport("Taoyuan", "TPE", "Taoyuan", "Taipei", "Taiwan R.O.C."));
            airportSessionBeanLocal.createNewAirport(new Airport("Sydney", "SYD", "Sydney", "New South Wales", "Australia"));
        } catch (AirportExistException | GeneralException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    
    private void doInitialiseAircraftType()
    {
        try {
            System.out.println("created airCraft Type");
            aircraftTypeSessionBeanLocal.createNewAircraftType(new AirCraftType("Boeing 737", 200));
            aircraftTypeSessionBeanLocal.createNewAircraftType(new AirCraftType("Boeing 747", 400));
        } catch (AircraftTypeExistException | GeneralException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
}
