/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author jonang
 */
@Entity
public class Flight implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;
    
    @Column(nullable = false, length = 6, unique = true)
    private String flightNumber;
    private boolean disabled;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private FlightRoute flightRoute;
    
    @OneToMany(mappedBy = "flight", fetch = FetchType.EAGER)
    private List<FlightSchedulePlan> flightSchedulePlans;
    
    //@OneToOne(optional = true)
    private boolean hasComplementaryReturnFlight;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private AirCraftConfig airCraftConfig;
   
    
    public Flight() {
        this.flightSchedulePlans = new ArrayList<>();
        this.disabled = false;
    }

    public Flight(String flightNumber) {
        this();
        this.flightNumber = flightNumber;
    }


    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public List<FlightSchedulePlan> getFlightSchedulePlans() {
        return flightSchedulePlans;
    }

    public void setFlightSchedulePlans(List<FlightSchedulePlan> flightSchedulePlans) {
        this.flightSchedulePlans = flightSchedulePlans;
    }

    public boolean isHasComplementaryReturnFlight() {
        return hasComplementaryReturnFlight;
    }

    public void setHasComplementaryReturnFlight(boolean hasComplementaryReturnFlight) {
        this.hasComplementaryReturnFlight = hasComplementaryReturnFlight;
    }

    
    
    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flightId != null ? flightId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the flightId fields are not set
        if (!(object instanceof Flight)) {
            return false;
        }
        Flight other = (Flight) object;
        if ((this.flightId == null && other.flightId != null) || (this.flightId != null && !this.flightId.equals(other.flightId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Flight[ id=" + flightId + " ]";
    }
    
}
