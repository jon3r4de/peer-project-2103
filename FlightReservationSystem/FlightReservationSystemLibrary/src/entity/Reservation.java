/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;

/**
 *
 * @author jonang
 */
@Entity
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;
    //passenger: firstName, lastName, passportNumber
    
    @OneToMany(mappedBy = "reservation")
    private List<Passenger> passengers;
    
    @Column(nullable = false)
    @Min(1)
    private Integer numOfPassengers;
    
    private Long flightId;
    private LocalDate reservationDate;
    private String status;
    private String departureAirport;
    private String destinationAirport;
    private Date departureDate;
    
    @Column(nullable = true)
    private Date returnDate;
    
    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private Customer customer;
    
    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private Partner partner;
    
    @Column(nullable = false)
    private BigDecimal totalAmount;
    
    @Column(nullable = false)
    private List<String> creditCardInfo;
    
    @ManyToMany
    @JoinColumn(nullable = false)
    private List<FlightSchedule> flightSchedules;
    
    public Reservation() {
       this.passengers = new ArrayList<>();
       this.flightSchedules = new ArrayList<>();
    }
    
    public Reservation(BigDecimal totalAmount, Integer numOfPassengers, List<String> creditCardInfo) {
        this();
        this.numOfPassengers = numOfPassengers;
        this.totalAmount = totalAmount;
        this.creditCardInfo = creditCardInfo;
    }
    
    public Reservation(Integer numOfPassengers, List<Passenger> passengers, List<String> creditCard, String departureAirport, String destinationAirport, Date departureDate, Date returnDate, Customer customer) {
        this.numOfPassengers = numOfPassengers;
        this.passengers = passengers;
        this.creditCardInfo = creditCard;
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.customer = customer;
    }


    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengerDetails(List<Passenger> passengerList) {
        this.passengers = passengerList;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public List<Passenger> getPassengerList() {
        return passengers;
    }

    public void setPassengerList(List<Passenger> passengerList) {
        this.passengers = passengerList;
    }

    public Integer getNumOfPassengers() {
        return numOfPassengers;
    }

    public void setNumOfPassengers(Integer numOfPassengers) {
        this.numOfPassengers = numOfPassengers;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<String> getCreditCardInfo() {
        return creditCardInfo;
    }

    public void setCreditCardInfo(List<String> creditCardInfo) {
        this.creditCardInfo = creditCardInfo;
    }

    public List<FlightSchedule> getFlightSchedules() {
        return flightSchedules;
    }

    public void setFlightSchedules(List<FlightSchedule> flightSchedules) {
        this.flightSchedules = flightSchedules;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservationId != null ? reservationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the reservationId fields are not set
        if (!(object instanceof Reservation)) {
            return false;
        }
        Reservation other = (Reservation) object;
        if ((this.reservationId == null && other.reservationId != null) || (this.reservationId != null && !this.reservationId.equals(other.reservationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reservation{" + "numOfPassengers=" + numOfPassengers + ", passengers=" + passengers + ", creditCard=" + creditCardInfo + 
                 ", customer=" + customer;
    }
    
}
