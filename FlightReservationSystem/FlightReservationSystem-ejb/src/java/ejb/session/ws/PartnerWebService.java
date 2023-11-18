/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/EjbWebService.java to edit this template
 */
package ejb.session.ws;

import ejb.session.stateless.FlightSchedulePlanSessionBeanLocal;
import ejb.session.stateless.FlightScheduleSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import entity.Partner;
import enumeration.CabinClassEnum;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.AirportNotFoundException;
import util.exception.FlightScheduleNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author tristan
 */
@WebService(serviceName = "PartnerWebService")
@Stateless()
public class PartnerWebService {

    @EJB
    private FlightSchedulePlanSessionBeanLocal flightSchedulePlanSessionBeanLocal;

    @EJB
    private FlightScheduleSessionBeanLocal flightScheduleSessionBeanLocal;

    @EJB
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    @WebMethod(operationName = "partnerLogin")
    public Partner partnerLogin(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws InvalidLoginCredentialException {
        Partner partner = partnerSessionBeanLocal.login(username, password);
        return partner;
    }
    
//    @WebMethod(operationName = "searchDirectFlightSchedules")
//    public List<FlightSchedule> searchDirectFlightSchedules(@WebParam(name = "destinationAirportiATACode") String destinationAirportiATACode,
//            @WebParam(name = "departureAirportiATACode") String departureAirportiATACode, @WebParam(name = "departureDate") Date departureDate,
//            @WebParam(name = "cabinClassType") CabinClassEnum cabinClassType) throws AirportNotFoundException, FlightScheduleNotFoundException {
//        List<FlightSchedule> flightSchedules = flightScheduleSessionBeanLocal.searchDirectFlightSchedules(departureAirportiATACode, destinationAirportiATACode, departureDate, cabinClassType);
//        return flightSchedules;
//    }
//    
//    @WebMethod(operationName = "getFlightSchedulePlanById")
//    public FlightSchedulePlan getFlightSchedulePlanById(@WebParam(name = "flightSchedulePlanId") Long flightSchedulePlanId) {
//        return flightSchedulePlanSessionBeanLocal.retrieveById(flightSchedulePlanId);
//    }
}
