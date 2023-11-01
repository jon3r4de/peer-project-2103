/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import enumeration.CabinClassEnum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jonang
 */
@Entity
public class CabinClass implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cabinClassId;
    
    @Column(nullable = false)
    @Min(0)
    @Max(2)
    @NotNull
    private Integer numberOfAisle;
    
    @Column(nullable = false)
    @Min(1)
    @NotNull
    private Integer numberOfRows;
    
    @Column(nullable = false)
    @Min(1)
    @NotNull
    private Integer maxSeatCapacity;
    
    @Column(nullable = false)
    @Min(1)
    @NotNull
    private Integer numOfSeatsAbreast;
    
     @Column(nullable = false)
     @Size(min=1, max=5)
    @NotNull
    private String actualSeatingConfig;
    
    @Column(nullable = false)
    private CabinClassEnum cabinClassType;
     
    @ManyToOne()
    @JoinColumn()
    private FlightSchedule flightSchedule;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private AirCraftConfig airCraftConfig;
     
    List<String> listOfSeatNumber;
    
    @Min(0)
    private Integer availableSeats;
    
    @Min(0)
    private Integer reservedSeats;
    
    @Min(0)
    private Integer balanceSeats;
    
    private boolean availableForBooking;
    
    @OneToMany()
    private List<Fare> fares;
    
    //private List<Seats> seats;
    public CabinClass() {
        //numOfAisles, numOfRows, numOfSeatsAbreast, seatingConfigurationPerColumn, cabinClassCapacity
        this.reservedSeats = 0;
        this.listOfSeatNumber = new ArrayList<>(); // Initialize the list of seat numbers
        this.fares = new ArrayList<>(); // Initialize the list of fares
    }
    
    public CabinClass(Integer numOfAisles, Integer numOfRows, Integer numOfSeatsAbreast, String seatingConfiguration, Integer maxCapacity, CabinClassEnum cabinClassType) {
        this();
        this.numberOfAisle = numOfAisles;
        this.numberOfRows = numOfRows;
        this.numOfSeatsAbreast = numOfSeatsAbreast;
        this.actualSeatingConfig = seatingConfiguration;
        this.maxSeatCapacity = maxCapacity;
        this.availableSeats = maxCapacity; 
        this.cabinClassType = cabinClassType;
    } 

    private void updateAvailableForBooking() {
        this.availableForBooking = (this.availableSeats > 0);
    }
     
    public Long getCabinClassId() {
        return cabinClassId;
    }

    public void setCabinClassId(Long cabinClassId) {
        this.cabinClassId = cabinClassId;
    }

    public Integer getNumberOfAisle() {
        return numberOfAisle;
    }

    public void setNumberOfAisle(Integer numberOfAisle) {
        this.numberOfAisle = numberOfAisle;
    }

    public Integer getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(Integer numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public Integer getMaxSeatCapacity() {
        return maxSeatCapacity;
    }

    public void setMaxSeatCapacity(Integer maxSeatCapacity) {
        this.maxSeatCapacity = maxSeatCapacity;
    }

    public Integer getNumOfSeatsAbreast() {
        return numOfSeatsAbreast;
    }

    public void setNumOfSeatsAbreast(Integer numOfSeatsAbreast) {
        this.numOfSeatsAbreast = numOfSeatsAbreast;
    }

    public String getActualSeatingConfig() {
        return actualSeatingConfig;
    }

    public void setActualSeatingConfig(String actualSeatingConfig) {
        this.actualSeatingConfig = actualSeatingConfig;
    }

    public CabinClassEnum getCabinClassType() {
        return cabinClassType;
    }

    public void setCabinClassType(CabinClassEnum cabinClassType) {
        this.cabinClassType = cabinClassType;
    }

    public FlightSchedule getFlightSchedule() {
        return flightSchedule;
    }

    public void setFlightSchedule(FlightSchedule flightSchedule) {
        this.flightSchedule = flightSchedule;
    }

    public AirCraftConfig getAirCraftConfig() {
        return airCraftConfig;
    }

    public void setAirCraftConfig(AirCraftConfig airCraftConfig) {
        this.airCraftConfig = airCraftConfig;
    }

    public List<String> getListOfSeatNumber() {
        return listOfSeatNumber;
    }

    public void setListOfSeatNumber(List<String> listOfSeatNumber) {
        this.listOfSeatNumber = listOfSeatNumber;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Integer getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(Integer reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public Integer getBalanceSeats() {
        return balanceSeats;
    }

    public void setBalanceSeats(Integer balanceSeats) {
        this.balanceSeats = balanceSeats;
    }

    public List<Fare> getFares() {
        return fares;
    }

    public void setFares(List<Fare> fares) {
        this.fares = fares;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cabinClassId != null ? cabinClassId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the cabinClassId fields are not set
        if (!(object instanceof CabinClass)) {
            return false;
        }
        CabinClass other = (CabinClass) object;
        if ((this.cabinClassId == null && other.cabinClassId != null) || (this.cabinClassId != null && !this.cabinClassId.equals(other.cabinClassId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CabinClass[ id=" + cabinClassId + " ]";
    }
    
}
