/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB31/SingletonEjbClass.java to edit this template
 */
package ejb.session.singleton;

import ejb.session.stateless.AircraftconfigSessionBeanLocal;
import ejb.session.stateless.AircraftconfigSessionBeanRemote;
import ejb.session.stateless.AirportSessionBeanLocal;
import ejb.session.stateless.EmployeeSessionBeanLocal;
import ejb.session.stateless.FlightRouteSessionBeanLocal;
import ejb.session.stateless.FlightSessionBeanLocal;
import ejb.session.stateless.aircraftTypeSessionBeanLocal;
import entity.AirCraftConfig;
import entity.AirCraftType;
import entity.Airport;
import entity.CabinClass;
import entity.Employee;
import entity.Flight;
import entity.FlightRoute;
import enumeration.CabinClassEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AircraftConfigExistExcetpion;
import util.exception.AircraftConfigNotFoundException;
import util.exception.AirportExistException;
import util.exception.AirportNotFoundException;
import util.exception.FlightExistException;
import util.exception.FlightRouteDisabledException;
import util.exception.FlightRouteExistException;
import util.exception.FlightRouteNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author jonang
 */
@Singleton
@LocalBean
//@Startup should be after datainit
public class testDataSessionBean {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    @EJB(name = "EmployeeSessionBeanLocal")
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;
    
    @EJB(name = "AirportSessionBeanLocal")
    private AirportSessionBeanLocal airportSessionBeanLocal;
    
    
    @EJB(name = "aircraftTypeSessionBeanLocal")
    private aircraftTypeSessionBeanLocal aircraftTypeSessionBeanLocal;
    
    @EJB(name = "AircraftconfigSessionBeanLocal")
    private AircraftconfigSessionBeanLocal aircraftconfigSessionBeanLocal;
    
    @EJB(name = "FlightRouteSessionBeanLocal")
    private FlightRouteSessionBeanLocal flightRouteSessionBeanLocal;
    


    
    public void postConstruct()
    {
        if(em.find(AirCraftConfig.class, 1L) == null)
        {
            doInitialiseAirCraftConfig();
            System.out.println("initialised air craft config");
        } 
        
        if(em.find(FlightRoute.class, 1l) == null)
        {
            doInitialiseFlightRoute();
            System.out.println("initialised flight route");
        }
        
        if(em.find(Flight.class, 1l) == null)
        {
            doInitialiseFlight();
            System.out.println("initialised flight");
        }
        
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    private void doInitialiseAirCraftConfig() {
        try {
        String airCraftType1 = "Boeing 737";
        String airCraftType2 = "Boeing 747";

        // First Query
        Query query1 = em.createQuery("SELECT act FROM AirCraftType act WHERE act.aircraftTypeName = :aircraftTypeName1");
        query1.setParameter("aircraftTypeName1", airCraftType1);
        AirCraftType ac1 = (AirCraftType) query1.getSingleResult();

        // Second Query
        Query query2 = em.createQuery("SELECT act FROM AirCraftType act WHERE act.aircraftTypeName = :aircraftTypeName2");
        query2.setParameter("aircraftTypeName2", airCraftType2);
        AirCraftType ac2 = (AirCraftType) query2.getSingleResult();

// CabinClass(Integer numOfAisles, Integer numOfRows, Integer numOfSeatsAbreast, String seatingConfiguration, Integer maxCapacity, CabinClassEnum cabinClassType) 
            
            CabinClass cabinClassOne = new CabinClass(1,30,6,"3-3", 180, CabinClassEnum.ECONOMY);
            List<CabinClass> firstCCList = new ArrayList<>();
            firstCCList.add(cabinClassOne);
            
           aircraftconfigSessionBeanLocal.createNewAircraftConfiguration(new AirCraftConfig("Boeing 737 ALL ECONOMY", 1, 180), ac1, firstCCList);
           
            List<CabinClass> secondCCList = new ArrayList<>();
            
            secondCCList.add(new CabinClass(1, 5, 2, "1-1", 10, CabinClassEnum.FIRST));
            secondCCList.add(new CabinClass(1, 5, 4, "2-2", 20, CabinClassEnum.BUSINESS));
            secondCCList.add(new CabinClass(1, 25, 6, "3-3", 150, CabinClassEnum.ECONOMY));
                 
            
            aircraftconfigSessionBeanLocal.createNewAircraftConfiguration(new AirCraftConfig("Boeing 737 Three Classes", 3, 180), ac1, secondCCList);
            
            List<CabinClass> thirdCCList = new ArrayList<>();
            
            thirdCCList.add(new CabinClass(2, 38, 10, "3-4-3", 380, CabinClassEnum.ECONOMY));
            
            aircraftconfigSessionBeanLocal.createNewAircraftConfiguration(new AirCraftConfig("Boeing 747 All Economy", 1, 380), ac2, thirdCCList);
            
            List<CabinClass> fourthCCList = new ArrayList<>();
            
            fourthCCList.add(new CabinClass(1, 5, 2, "1-1", 10, CabinClassEnum.FIRST));
            fourthCCList.add(new CabinClass(2, 5, 6, "2-2-2", 30, CabinClassEnum.BUSINESS));
            fourthCCList.add(new CabinClass(2, 32, 10, "3-4-3", 320, CabinClassEnum.ECONOMY));
            
             aircraftconfigSessionBeanLocal.createNewAircraftConfiguration(new AirCraftConfig("Boeing 747 Three Classes", 3, 360), ac2, fourthCCList);
           
        }catch (AircraftConfigExistExcetpion | GeneralException ex) {
                System.out.println("Error: " + ex.getMessage());
        } 
        
        System.out.println("air craft configs");
    }

    private void doInitialiseFlightRoute() {
        try {
            Long f1 = flightRouteSessionBeanLocal.createNewFlightRoute("SIN", "HKG");
            Long f2 = flightRouteSessionBeanLocal.createNewFlightRoute("HKG", "SIN");
            flightRouteSessionBeanLocal.associateComplementaryFlightRoute(f1, f2);
            
            Long f3 = flightRouteSessionBeanLocal.createNewFlightRoute("SIN", "TPE");
            Long f4 = flightRouteSessionBeanLocal.createNewFlightRoute("TPE", "SIN"); 
            flightRouteSessionBeanLocal.associateComplementaryFlightRoute(f3, f4);
            
            Long f5 = flightRouteSessionBeanLocal.createNewFlightRoute("SIN", "NRT");
            Long f6 = flightRouteSessionBeanLocal.createNewFlightRoute("NRT", "SIN"); 
            flightRouteSessionBeanLocal.associateComplementaryFlightRoute(f5, f6);
            
            Long f7 = flightRouteSessionBeanLocal.createNewFlightRoute("HKG", "NRT");
            Long f8 = flightRouteSessionBeanLocal.createNewFlightRoute("NRT", "HKG"); 
            flightRouteSessionBeanLocal.associateComplementaryFlightRoute(f7, f8);
            
            Long f9 = flightRouteSessionBeanLocal.createNewFlightRoute("TPE", "NRT");
            Long f10 = flightRouteSessionBeanLocal.createNewFlightRoute("NRT", "TPE"); 
            flightRouteSessionBeanLocal.associateComplementaryFlightRoute(f9, f10);
            
            Long f11 = flightRouteSessionBeanLocal.createNewFlightRoute("SIN", "SYD");
            Long f12 = flightRouteSessionBeanLocal.createNewFlightRoute("SYD", "SIN"); 
            flightRouteSessionBeanLocal.associateComplementaryFlightRoute(f11, f12);
            
            Long f13 = flightRouteSessionBeanLocal.createNewFlightRoute("SYD", "NRT");
            Long f14 = flightRouteSessionBeanLocal.createNewFlightRoute("NRT", "SYD"); 
            flightRouteSessionBeanLocal.associateComplementaryFlightRoute(f8, f9);
            
            System.out.println("created flight routes");
   
        } catch (GeneralException | FlightRouteExistException | AirportNotFoundException | FlightRouteNotFoundException  ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void doInitialiseFlight() {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
