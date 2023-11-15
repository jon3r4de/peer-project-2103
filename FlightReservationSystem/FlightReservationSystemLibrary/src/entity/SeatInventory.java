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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jonang
 */
@Entity
public class SeatInventory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatInventoryId;

    @Column(nullable = false)
    @Min(0)
    @NotNull
    private Integer numberOfAvailableSeats;
    
    @Column(nullable = false)
    @Min(0)
    @NotNull
    private Integer numberOfReservedSeats;
    
    @Column(nullable = false)
    @Min(0)
    @NotNull
    private Integer numberOfBalanceSeats;
    
    @ManyToOne
    private FlightSchedule flightSchedule;
    
    @Column(nullable = false)
    private CabinClassEnum cabinClassType;
    
    @ManyToOne
    private CabinClass cabinClass;
    
    @OneToMany(mappedBy = "seatInventory")
    private List<Seat> seats;
    
    public SeatInventory() {
        this.seats = new ArrayList<>();
    }
    
    public SeatInventory(CabinClass cabinClass, FlightSchedule flightSchedule, Integer numberOfAvailableSeats) {
        this.cabinClass = cabinClass;
        this.cabinClassType = cabinClass.getCabinClassType();
        this.flightSchedule = flightSchedule;
        this.numberOfAvailableSeats = numberOfAvailableSeats;
        this.numberOfReservedSeats = 0;
        this.numberOfBalanceSeats = numberOfAvailableSeats;
    }

    public FlightSchedule getFlightSchedule() {
        return flightSchedule;
    }

    public void setFlightSchedule(FlightSchedule flightSchedule) {
        this.flightSchedule = flightSchedule;
    }

    public CabinClassEnum getCabinClassType() {
        return cabinClassType;
    }

    public void setCabinClassType(CabinClassEnum cabinClassType) {
        this.cabinClassType = cabinClassType;
    }

    public CabinClass getCabinClass() {
        return cabinClass;
    }

    public void setCabinClass(CabinClass cabinClass) {
        this.cabinClass = cabinClass;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
    
    
    public Long getSeatInventoryId() {
        return seatInventoryId;
    }

    public void setSeatInventoryId(Long seatInventoryId) {
        this.seatInventoryId = seatInventoryId;
    }

    public Integer getNumberOfAvailableSeats() {
        return numberOfAvailableSeats;
    }

    public void setNumberOfAvailableSeats(Integer numberOfAvailableSeats) {
        this.numberOfAvailableSeats = numberOfAvailableSeats;
    }

    public Integer getNumberOfReservedSeats() {
        return numberOfReservedSeats;
    }

    public void setNumberOfReservedSeats(Integer numberOfReservedSeats) {
        this.numberOfReservedSeats = numberOfReservedSeats;
    }

    public Integer getNumberOfBalanceSeats() {
        return numberOfBalanceSeats;
    }

    public void setNumberOfBalanceSeats(Integer numberOfBalanceSeats) {
        this.numberOfBalanceSeats = numberOfBalanceSeats;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (seatInventoryId != null ? seatInventoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the seatInventoryId fields are not set
        if (!(object instanceof SeatInventory)) {
            return false;
        }
        SeatInventory other = (SeatInventory) object;
        if ((this.seatInventoryId == null && other.seatInventoryId != null) || (this.seatInventoryId != null && !this.seatInventoryId.equals(other.seatInventoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SeatInventory[ id=" + seatInventoryId + " ]";
    }
    
}
