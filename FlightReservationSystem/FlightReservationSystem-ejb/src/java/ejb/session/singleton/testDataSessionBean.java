/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB31/SingletonEjbClass.java to edit this template
 */
package ejb.session.singleton;

import ejb.session.stateless.AircraftconfigSessionBeanLocal;
import ejb.session.stateless.AirportSessionBeanLocal;
import ejb.session.stateless.EmployeeSessionBeanLocal;
import ejb.session.stateless.FlightRouteSessionBeanLocal;
import ejb.session.stateless.FlightSchedulePlanSessionBeanLocal;
import ejb.session.stateless.FlightSessionBeanLocal;
import ejb.session.stateless.aircraftTypeSessionBeanLocal;
import entity.AirCraftConfig;
import entity.AirCraftType;
import entity.Airport;
import entity.CabinClass;
import entity.Employee;
import entity.Fare;
import entity.Flight;
import entity.FlightRoute;
import entity.FlightSchedulePlan;
import entity.Partner;
import enumeration.CabinClassEnum;
import enumeration.EmployeeEnum;
import enumeration.FlightScheduleEnum;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import util.exception.AircraftTypeExistException;
import util.exception.AirportExistException;
import util.exception.AirportNotFoundException;
import util.exception.FlightExistException;
import util.exception.FlightRouteDisabledException;
import util.exception.FlightRouteExistException;
import util.exception.FlightRouteNotFoundException;
import util.exception.GeneralException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author jonang
 */
@Singleton
@LocalBean
@Startup
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
    
    @EJB(name = "FlightSessionBeanLocal")
    private FlightSessionBeanLocal flightSessionBeanLocal;
    
    @EJB(name = "FlightSchedulePlanSessionBeanLocal")
    private FlightSchedulePlanSessionBeanLocal flightSchedulePlanSessionBeanLocal;
    
     static DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd MMM yy hh:mm aa");
    private static DateFormat ESTIMATED_FLIGHT_DURATION_FORMAT = new SimpleDateFormat("hh 'Hours' mm 'Minute'");
    

    @PostConstruct
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
        
        if(em.find(Partner.class, 1l) == null)
        {
            doInitialisePartner();
            System.out.println("initialised partner");
        }
        
        //errata now
        if(em.find(FlightSchedulePlan.class, 1l) == null){
            doInitialiseFlightSchedulePlan();
            System.out.println("initialised fsp");
        }
        
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private void doInitialisePartner(){
        Partner partner = new Partner("partner", "password");
        //session bean create line
    }

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
            flightRouteSessionBeanLocal.associateComplementaryFlightRoute(f13, f14);
            
            System.out.println("created flight routes");
   
        } catch (GeneralException | FlightRouteExistException | AirportNotFoundException | FlightRouteNotFoundException  ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
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

    //createNewFlight(Flight newFlight, String originAirportIATACode, String destinationAirportIATACode, String aircraftConfigurationName)
    private void doInitialiseFlight() {
        try {
            
            Flight flight1 = new Flight("ML111");
            Long f1Id = flightSessionBeanLocal.createNewFlight(flight1, "SIN", "HKG", "Boeing 737 Three Classes");
            
            Flight flight2 = new Flight("ML112");
            Long f2Id = flightSessionBeanLocal.createNewFlight(flight2, "HKG", "SIN", "Boeing 737 Three Classes");
            
            flightSessionBeanLocal.associateComplementaryFlight(f1Id, f2Id);
            
            Flight flight3 = new Flight("ML211");
            Long f3Id = flightSessionBeanLocal.createNewFlight(flight3, "TPE", "SIN", "Boeing 737 Three Classes");
            
            Flight flight4 = new Flight("ML212");
            Long f4Id = flightSessionBeanLocal.createNewFlight(flight4, "SIN", "TPE", "Boeing 737 Three Classes");
            flightSessionBeanLocal.associateComplementaryFlight(f3Id, f4Id);
            
            Flight flight5 = new Flight("ML311");
            Long f5Id = flightSessionBeanLocal.createNewFlight(flight5, "SIN", "NRT", "Boeing 747 Three Classes");
            
            Flight flight6 = new Flight("ML312");
            Long f6Id = flightSessionBeanLocal.createNewFlight(flight6, "NRT", "SIN", "Boeing 747 Three Classes");
            flightSessionBeanLocal.associateComplementaryFlight(f5Id, f6Id);
            
            Flight flight7 = new Flight("ML411");
            Long f7Id = flightSessionBeanLocal.createNewFlight(flight7, "HKG", "NRT", "Boeing 737 Three Classes");
            
            Flight flight8 = new Flight("ML412");
            Long f8Id = flightSessionBeanLocal.createNewFlight(flight8, "NRT", "HKG", "Boeing 737 Three Classes");
            flightSessionBeanLocal.associateComplementaryFlight(f7Id, f8Id);
            
            Flight flight9 = new Flight("ML511");
            Long f9Id = flightSessionBeanLocal.createNewFlight(flight9, "TPE", "NRT", "Boeing 737 Three Classes");
            
            Flight flight10 = new Flight("ML512");
            Long f10Id = flightSessionBeanLocal.createNewFlight(flight10, "NRT", "TPE", "Boeing 737 Three Classes");
            flightSessionBeanLocal.associateComplementaryFlight(f9Id, f10Id);
            
            Flight flight11 = new Flight("ML611");
            Long f11Id = flightSessionBeanLocal.createNewFlight(flight11, "SIN", "SYD", "Boeing 737 Three Classes");
            
            Flight flight12 = new Flight("ML612");
            Long f12Id = flightSessionBeanLocal.createNewFlight(flight12, "SYD", "SIN", "Boeing 737 Three Classes");
            flightSessionBeanLocal.associateComplementaryFlight(f11Id, f12Id);
            
            Flight flight13 = new Flight("ML621");
            Long f13Id = flightSessionBeanLocal.createNewFlight(flight13, "SIN", "SYD", "Boeing 737 Three Classes");
            
            Flight flight14 = new Flight("ML622");
            Long f14Id = flightSessionBeanLocal.createNewFlight(flight14, "SYD", "SIN", "Boeing 737 Three Classes");
            flightSessionBeanLocal.associateComplementaryFlight(f13Id, f14Id);
            
            Flight flight15 = new Flight("ML711");
            Long f15Id = flightSessionBeanLocal.createNewFlight(flight15, "SYD", "NRT", "Boeing 737 Three Classes");
            
            Flight flight16 = new Flight("ML712");
            Long f16Id = flightSessionBeanLocal.createNewFlight(flight16, "NRT", "SYD", "Boeing 747 Three Classes");
            flightSessionBeanLocal.associateComplementaryFlight(f15Id, f16Id);

            
        }  catch (FlightExistException | GeneralException | FlightRouteNotFoundException | AircraftConfigNotFoundException | FlightRouteDisabledException ex) {
            System.out.println("Error: " + ex.getMessage());
        } 

    }
    
    public void doInitialiseFlightSchedulePlan() {
        
         //private void doCreateRecurrentByWeekFlightSchedulePlan(Scanner scanner, Flight flight) {
        try {
          
            // fsp test case 1
            int recurrence = 7;
            
            Date departureDateTime = DATE_TIME_FORMAT.parse("01 Dec 23 09:00 AM");
 
            String dayOfWeek = "Mon";

            //String estimatedFlightDurationString = "14 Hours 00 Minute";
            //Date estimatedFlightDuration = ESTIMATED_FLIGHT_DURATION_FORMAT.parse(estimatedFlightDurationString);

            String endDateString = "31 Dec 23";
            DateFormat endDateFormat = new SimpleDateFormat("dd MMM yy");
            Date endDate = endDateFormat.parse(endDateString);

            List<Fare> fares = new ArrayList<>();
            
            //String fareBasisCode, CabinClassEnum cabinClassType, BigDecimal fareAmount
            fares.add(new Fare("ML711_F1", CabinClassEnum.FIRST,new BigDecimal("6000")));
            fares.add(new Fare("ML711_J1", CabinClassEnum.BUSINESS,new BigDecimal("3000")));
            fares.add(new Fare("ML711_Y1", CabinClassEnum.ECONOMY,new BigDecimal("1000")));
            
            Duration duration = Duration.ofHours(2);
            
            
            FlightSchedulePlan newFlightSchedulePlan = new FlightSchedulePlan(FlightScheduleEnum.RECURRENTWEEK, fares);
            
            for(Fare f : fares) {
                f.setFlightSchedulePlan(newFlightSchedulePlan);
            }
            
            newFlightSchedulePlan.setLayoverDuration(duration);
            newFlightSchedulePlan.setReccurrentDay(dayOfWeek);
            
            Query query1 = em.createQuery("SELECT f FROM Flight f WHERE f.flightNumber = :inFlightNumber");
            query1.setParameter("inFlightNumber", "ML711");
            Flight f1 = (Flight) query1.getSingleResult();
            
            
            Long newFlightSchedulePlanId = flightSchedulePlanSessionBeanLocal.createNewRecurrentFlightSchedulePlan(newFlightSchedulePlan, f1.getFlightId(), departureDateTime, 14, 0, endDate, recurrence);
            
            // fsp test case 1
            
            // fsp test case 2
            
            Date departureDateTime1 = DATE_TIME_FORMAT.parse("01 Dec 23 12:00 PM");
 
            String dayOfWeek1 = "Sun";

            //String estimatedFlightDurationString1 = "08 Hours 00 Minute";
            //Date estimatedFlightDuration1 = ESTIMATED_FLIGHT_DURATION_FORMAT.parse(estimatedFlightDurationString1);

            String endDateString1 = "31 Dec 23";
            Date endDate1 = endDateFormat.parse(endDateString1);

            List<Fare> fares1 = new ArrayList<>();
            
            //String fareBasisCode, CabinClassEnum cabinClassType, BigDecimal fareAmount
            fares1.add(new Fare("ML611_F1", CabinClassEnum.FIRST,new BigDecimal("3000")));
            fares1.add(new Fare("ML611_J1", CabinClassEnum.BUSINESS,new BigDecimal("1500")));
            fares1.add(new Fare("ML611_Y1", CabinClassEnum.ECONOMY,new BigDecimal("500")));
            
            Duration duration1 = Duration.ofHours(2);
            
            
            FlightSchedulePlan newFlightSchedulePlan1 = new FlightSchedulePlan(FlightScheduleEnum.RECURRENTWEEK, fares1);
            
            for(Fare f : fares1) {
                f.setFlightSchedulePlan(newFlightSchedulePlan1);
            }
                        
            newFlightSchedulePlan1.setLayoverDuration(duration1);
            newFlightSchedulePlan1.setReccurrentDay(dayOfWeek1);
            
            Query query11 = em.createQuery("SELECT f FROM Flight f WHERE f.flightNumber = :inFlightNumber");
            query11.setParameter("inFlightNumber", "ML611");
            Flight f11 = (Flight) query11.getSingleResult();

            Long newFlightSchedulePlanId1 = flightSchedulePlanSessionBeanLocal.createNewRecurrentFlightSchedulePlan(newFlightSchedulePlan1, f11.getFlightId(), departureDateTime1, 8, 0, endDate1, recurrence);

            // fsp test case 2
            
            // fsp test case 3
            
            Date departureDateTime2 = DATE_TIME_FORMAT.parse("01 Dec 23 09:00 AM");
 
            String dayOfWeek2 = "Tue";

            //String estimatedFlightDurationString2 = "08 Hours 00 Minute";
            //Date estimatedFlightDuration2 = ESTIMATED_FLIGHT_DURATION_FORMAT.parse(estimatedFlightDurationString2);

            String endDateString2 = "31 Dec 23";
            Date endDate2 = endDateFormat.parse(endDateString2);

            List<Fare> fares2 = new ArrayList<>();
            
            //String fareBasisCode, CabinClassEnum cabinClassType, BigDecimal fareAmount
            fares2.add(new Fare("ML621_Y1", CabinClassEnum.ECONOMY,new BigDecimal("700")));
            
            Duration duration2 = Duration.ofHours(2);
            
            
            FlightSchedulePlan newFlightSchedulePlan2 = new FlightSchedulePlan(FlightScheduleEnum.RECURRENTWEEK, fares2);
            
            for(Fare f : fares2) {
                f.setFlightSchedulePlan(newFlightSchedulePlan2);
            }
            newFlightSchedulePlan2.setLayoverDuration(duration);
            newFlightSchedulePlan2.setReccurrentDay(dayOfWeek2);
            
            Query query12 = em.createQuery("SELECT f FROM Flight f WHERE f.flightNumber = :inFlightNumber");
            query12.setParameter("inFlightNumber", "ML621");
            Flight f12 = (Flight) query12.getSingleResult();
            

            Long newFlightSchedulePlanId2 = flightSchedulePlanSessionBeanLocal.createNewRecurrentFlightSchedulePlan(newFlightSchedulePlan2, f12.getFlightId(), departureDateTime2, 8, 0, endDate2, recurrence);

            // fsp test case 3
            
            // fsp test case 4
            
            Date departureDateTime3 = DATE_TIME_FORMAT.parse("01 Dec 23 09:00 AM");
 
            String dayOfWeek3 = "Mon";

            //String estimatedFlightDurationString3 = "06 Hours 30 Minute";
            //Date estimatedFlightDuration3 = ESTIMATED_FLIGHT_DURATION_FORMAT.parse(estimatedFlightDurationString3);

            String endDateString3 = "31 Dec 23";
            Date endDate3 = endDateFormat.parse(endDateString3);

            List<Fare> fares3 = new ArrayList<>();
            
            //String fareBasisCode, CabinClassEnum cabinClassType, BigDecimal fareAmount
            fares3.add(new Fare("ML312_F1", CabinClassEnum.FIRST,new BigDecimal("3100")));
            fares3.add(new Fare("ML312_J1", CabinClassEnum.BUSINESS,new BigDecimal("1600")));
            fares3.add(new Fare("ML312_Y1", CabinClassEnum.ECONOMY,new BigDecimal("600")));
            
            Duration duration3 = Duration.ofHours(3);
            
            
            FlightSchedulePlan newFlightSchedulePlan3 = new FlightSchedulePlan(FlightScheduleEnum.RECURRENTWEEK, fares3);
            
            for(Fare f : fares3) {
                f.setFlightSchedulePlan(newFlightSchedulePlan3);
            }
            newFlightSchedulePlan3.setLayoverDuration(duration);
            newFlightSchedulePlan3.setReccurrentDay(dayOfWeek3);
            
            Query query13 = em.createQuery("SELECT f FROM Flight f WHERE f.flightNumber = :inFlightNumber");
            query13.setParameter("inFlightNumber", "ML312");
            Flight f13 = (Flight) query13.getSingleResult();
            

            Long newFlightSchedulePlanId3 = flightSchedulePlanSessionBeanLocal.createNewRecurrentFlightSchedulePlan(newFlightSchedulePlan3, f13.getFlightId(), departureDateTime3, 6, 30, endDate3, recurrence);

            // fsp test case 4
            
            // fsp test case 5
        int reccurence = 2;
        Date departureDateTime4 = DATE_TIME_FORMAT.parse("1 Dec 23 01:00 PM");
        //Date estimatedFlightDuration4 = ESTIMATED_FLIGHT_DURATION_FORMAT.parse("04 Hours 00 Minute");
    

            String endDateString4 = "31 Dec 23";
            Date endDate4 = endDateFormat.parse(endDateString4);

            List<Fare> fares4 = new ArrayList<>();
            
            //String fareBasisCode, CabinClassEnum cabinClassType, BigDecimal fareAmount
            fares4.add(new Fare("ML412_F1", CabinClassEnum.FIRST,new BigDecimal("2900")));
            fares4.add(new Fare("ML412_J1", CabinClassEnum.BUSINESS,new BigDecimal("1400")));
            fares4.add(new Fare("ML412_Y1", CabinClassEnum.ECONOMY,new BigDecimal("400")));
            
            Duration duration4 = Duration.ofHours(4);
            
            FlightSchedulePlan newFlightSchedulePlan4 = new FlightSchedulePlan(FlightScheduleEnum.RECURRENTDAY, fares4); 
           
            for(Fare f : fares4) {
                f.setFlightSchedulePlan(newFlightSchedulePlan4);
            }
            
            Query query14 = em.createQuery("SELECT f FROM Flight f WHERE f.flightNumber = :inFlightNumber");
            query14.setParameter("inFlightNumber", "ML412");
            Flight f14 = (Flight) query14.getSingleResult();
            
            newFlightSchedulePlan4.setLayoverDuration(duration4);
            
            Long newFlightSchedulePlanId4 = flightSchedulePlanSessionBeanLocal.createNewRecurrentFlightSchedulePlan(newFlightSchedulePlan4, f14.getFlightId(), departureDateTime4, 4, 0, endDate4, reccurence);
           
        // fsp test case 5
        
        //fsp test case 6
        
            List<Date> departureDateTimes = new ArrayList<>();
            List<Integer> estimatedFlightDurationsHours = new ArrayList<>();
            List<Integer> estimatedFlightDurationsMinutes = new ArrayList<>();


                String departureDateTimeString = "7 Dec 23 05:00 PM";
                Date departureDate = DATE_TIME_FORMAT.parse(departureDateTimeString);

                departureDateTimes.add(departureDate);
                
                String departureDateTimeString2 = "8 Dec 23 05:00 PM";
                Date departureDate2 = DATE_TIME_FORMAT.parse(departureDateTimeString2);

                departureDateTimes.add(departureDate2);
                
                String departureDateTimeString3 = "9 Dec 23 05:00 PM";
                Date departureDate3 = DATE_TIME_FORMAT.parse(departureDateTimeString3);

                departureDateTimes.add(departureDate3);
                

                //Date estimatedFlightDuration5 = ESTIMATED_FLIGHT_DURATION_FORMAT.parse("03 Hours 00 Minute");
                //Date estimatedFlightDuration6 = ESTIMATED_FLIGHT_DURATION_FORMAT.parse("03 Hours 00 Minute");
                //Date estimatedFlightDuration7 = ESTIMATED_FLIGHT_DURATION_FORMAT.parse("03 Hours 00 Minute");
                estimatedFlightDurationsHours.add(3);
                estimatedFlightDurationsHours.add(3);
                estimatedFlightDurationsHours.add(3);
                
                estimatedFlightDurationsMinutes.add(0);
                estimatedFlightDurationsMinutes.add(0);
                estimatedFlightDurationsMinutes.add(0);
                
            List<Fare> fares5 = new ArrayList<>();
            
            //String fareBasisCode, CabinClassEnum cabinClassType, BigDecimal fareAmount
            fares5.add(new Fare("ML512_F1", CabinClassEnum.FIRST,new BigDecimal("3100")));
            fares5.add(new Fare("ML512_J1", CabinClassEnum.BUSINESS,new BigDecimal("1600")));
            fares5.add(new Fare("ML512_Y1", CabinClassEnum.ECONOMY,new BigDecimal("600")));
            
            Duration duration5 = Duration.ofHours(2);
            

            FlightSchedulePlan newFlightSchedulePlan5 = new FlightSchedulePlan(FlightScheduleEnum.MULTIPLE, fares5);
            
            for(Fare f : fares5) {
                f.setFlightSchedulePlan(newFlightSchedulePlan5);
            }
            
            newFlightSchedulePlan5.setLayoverDuration(duration5);
            
            Query query15 = em.createQuery("SELECT f FROM Flight f WHERE f.flightNumber = :inFlightNumber");
            query15.setParameter("inFlightNumber", "ML512");
            Flight f15 = (Flight) query15.getSingleResult();

            Long newFlightSchedulePlanId5 = flightSchedulePlanSessionBeanLocal.createNewMultipleFlightSchedulePlan(newFlightSchedulePlan5, f15.getFlightId(), departureDateTimes, estimatedFlightDurationsHours, estimatedFlightDurationsMinutes);
           
        //fsp test case 6
            
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    
        
    }


}
