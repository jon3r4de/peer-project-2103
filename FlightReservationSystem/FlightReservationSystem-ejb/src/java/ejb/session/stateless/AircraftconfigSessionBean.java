/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AirCraftConfig;
import entity.AirCraftType;
import entity.CabinClass;
import entity.Seat;
import entity.SeatInventory;
import enumeration.CabinClassEnum;
import enumeration.SeatStatusEnum;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.AircraftConfigExistExcetpion;
import util.exception.AircraftConfigNotFoundException;
import util.exception.GeneralException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author jonang
 */
@Stateless
public class AircraftconfigSessionBean implements AircraftconfigSessionBeanRemote, AircraftconfigSessionBeanLocal {

    @EJB
    private SeatSessionBeanLocal seatSessionBeanLocal;
    

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

        @Override
    public Long createNewAircraftConfiguration(AirCraftConfig newAircraftConfiguration, AirCraftType aircraftType, List<CabinClass> cabinClasses) throws AircraftConfigExistExcetpion, GeneralException
    {
        
        aircraftType = em.find(AirCraftType.class, aircraftType.getAircraftTypeId());
        
                        
                newAircraftConfiguration.setAirCraftType(aircraftType); 
                aircraftType.getConfigs().add(newAircraftConfiguration);
                //newAircraftConfiguration.setCabinClasses(cabinClasses);

                //System.out.println("dawwen debug 1");
                for (CabinClass cabinClass: cabinClasses)
                {
                    //cabinClass.setCabinClassType(CabinClassEnum.BUSINESS);
                    //System.out.println("dawwen debug 2");
                    em.persist(cabinClass);
                   // System.out.println("persisting the cabin class");
                    cabinClass.setAirCraftConfig(newAircraftConfiguration); 
                    newAircraftConfiguration.getCabinClasses().add(cabinClass);
                    //em.flush();
                }

                //newAircraftConfiguration.setCabinClasses(cabinClasses);
                em.persist(newAircraftConfiguration);
                em.flush();
                return newAircraftConfiguration.getAirCraftConfigId();
        
        //for (CabinClass cabinClass : cabinClasses) {
          //  cabinClass.setAirCraftConfig(newAircraftConfiguration);
            //em.flush();
            //try {
                //generateSeats(cabinClass);
            /*} catch (UnknownPersistenceException ex) {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }*/
        }
    
    //@Override
    //public Long createNewAircraftConfiguration(AirCraftConfig newAircraftConfiguration, AirCraftType aircraftType, List<CabinClass> cabinClasses) throws AircraftConfigExistExcetpion, GeneralException
    //{
        //try {
        //em.persist(newAircraftConfiguration);
        //} catch(PersistenceException ex) {
            //if(ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getSimpleName().equals("SQLIntegrityConstraintViolationException")) {
              //  throw new AircraftConfigExistExcetpion("Aircraft Configuration with the name: " + newAircraftConfiguration.getAirCraftConfigName() + " already exists!"); 
            //} else {
            //    throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
          //  }
        //}
        
        //aircraftType = em.find(AirCraftType.class, aircraftType.getAircraftTypeId());
        
        //for (CabinClass cabinClass : cabinClasses) {
           // cabinClass.setAirCraftConfig(newAircraftConfiguration);
            //em.flush();
            //try {
         //       generateSeats(cabinClass);
            /*} catch (UnknownPersistenceException ex) {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }*/
       // }
        
        //newAircraftConfiguration.setCabinClasses(cabinClasses);
       // newAircraftConfiguration.setAirCraftType(aircraftType);
        //aircraftType.getConfigs().add(newAircraftConfiguration);
        
        //set max seat capacity
        //newAircraftConfiguration.getMaxSeatCapacity();

       // em.flush();
           
     //   return newAircraftConfiguration.getAirCraftConfigId();
   // }
   /* private void generateSeats(CabinClass cabinClass) { //throws UnknownPersistenceException {
        //try {
            Integer numOfRows = cabinClass.getNumberOfRows();
            Integer numOfSeatsAbreast = cabinClass.getNumOfSeatsAbreast();

            for (int row = 1; row <= numOfRows; row++) {
                for (int seatLetter = 0; seatLetter < numOfSeatsAbreast; seatLetter++) {
                    char letter = (char) ('A' + seatLetter);
                    String seatNumber = String.valueOf(row) + letter;
                    Seat seat = new Seat(seatNumber, SeatStatusEnum.AVAILABLE);
                    //seatSessionBeanLocal.createSeat(new Seat(seatNumber), cabinClass.getCabinClassId());
                    
           // seat.setCabinClass(cabinClass);
            //cabinClass.getSeats().add(seat);
            
                }
            }
       // } catch (UnknownPersistenceException ex) {
        //    throw ex;
       // }        
    }*/
    @Override
    public void generateSeats(CabinClass cabinClass, SeatInventory seatInventory) {
        //try {
            Integer numOfRows = cabinClass.getNumberOfRows();
            Integer numOfSeatsAbreast = cabinClass.getNumOfSeatsAbreast();
            //System.out.println("todays daily bean 8");
            //System.out.println("CabinClass :" + cabinClass);

            for (int row = 1; row <= numOfRows; row++) {
                //System.out.println("num of rows" + numOfRows);
                //System.out.println("index" + row);
                for (int seatLetter = 0; seatLetter < numOfSeatsAbreast; seatLetter++) {
                    //System.out.println("todays daily bean 10");
                    //System.out.println("num of seat abreast" + numOfSeatsAbreast);
                    //System.out.println(seatLetter);
                    char letter = (char) ('A' + seatLetter);
                    String seatNumber = String.valueOf(row) + letter;
                    Seat seat = new Seat(seatNumber, SeatStatusEnum.AVAILABLE);
                    seatSessionBeanLocal.createSeat(seat, seatInventory);
                    
               seat.setSeatInventory(seatInventory);
               seatInventory.getSeats().add(seat);
            
                }
            }
            
        } 
    
    
    
    
    

    
    
    @Override
    public List<AirCraftConfig> retrieveAllAircraftConfigurations() {
        Query query = em.createQuery("SELECT ac FROM AirCraftConfig ac ORDER BY ac.airCraftType.aircraftTypeName, ac.airCraftConfigName");

        //lazy loading
        List<AirCraftConfig> aircraftConfiguration = (List<AirCraftConfig>) query.getResultList();
       
        for (AirCraftConfig acc : aircraftConfiguration) {
            acc.getFlights().size();
            acc.getCabinClasses().size();
        }
        //lazy loading
        
        return aircraftConfiguration;
    }
    
    @Override
    public AirCraftConfig retrieveAircraftConfigurationByName(String name) throws AircraftConfigNotFoundException
    {
        Query query = em.createQuery("SELECT ac FROM AirCraftConfig ac WHERE ac.airCraftConfigName = :name");
        query.setParameter("name", name);
        
        try
        {
            AirCraftConfig aircraftConfiguration = (AirCraftConfig) query.getSingleResult();
            aircraftConfiguration.getCabinClasses().size();
            aircraftConfiguration.getFlights().size();
            
            return aircraftConfiguration;
        }
        catch(NoResultException ex)
        {
            throw new AircraftConfigNotFoundException("Aircraft configuration " + name + " does not exist!\n");
        }                
    }
    
    @Override
    public AirCraftConfig retrieveAircraftConfigurationById(Long aircraftConfigurationId) throws AircraftConfigNotFoundException
    {
        AirCraftConfig aircraftConfiguration = em.find(AirCraftConfig.class, aircraftConfigurationId);
        
        if(aircraftConfiguration != null)
        {
            aircraftConfiguration.getCabinClasses().size();
            aircraftConfiguration.getFlights().size();
            return aircraftConfiguration;
        }
        else
        {
            throw new AircraftConfigNotFoundException("Aircraft configuration ID " + aircraftConfigurationId + " does not exist");
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
