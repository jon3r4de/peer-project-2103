/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.validation.constraints.NotNull;

/**
 *
 * @author jonang
 */
@Entity
public class FlightRoute implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightRouteId;
    
    @Column(nullable = false)
    @NotNull
    private boolean hasComplementaryReturnRoute;
    
    //@OneToOne(optional = true)
    private FlightRoute complementaryReturn;
    
    private String ODPair;
    
    @Column(nullable = false)
    @NotNull
    private boolean disabled;
    
    @OneToMany(mappedBy = "flightRoute", fetch = FetchType.EAGER)
    private List<Flight> flights;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Airport origin;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Airport destination;
    
    public FlightRoute() {
        this.flights = new ArrayList<>();
        this.disabled = false;
    }

    public FlightRoute(Airport origin, Airport destination) {
        this();
        this.origin = origin;
        this.destination = destination;
    }

    public Airport getOrigin() {
        return origin;
    }

    public Airport getDestination() {
        return destination;
    }
    
    public boolean isHasComplementaryReturnRoute() {
        return hasComplementaryReturnRoute;
    }

    public void setHasComplementaryReturnRoute(boolean hasComplementaryReturnRoute) {
        this.hasComplementaryReturnRoute = hasComplementaryReturnRoute;
    }

    public String getODPair() {
        return ODPair;
    }

    public void setODPair(String ODPair) {
        this.ODPair = ODPair;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
    
        public FlightRoute getComplementaryReturn() {
        return complementaryReturn;
    }

    public void setComplementaryReturn(FlightRoute complementaryReturn) {
        this.complementaryReturn = complementaryReturn;
    }

    public Long getFlightRouteId() {
        return flightRouteId;
    }

    public void setFlightRouteId(Long flightRouteId) {
        this.flightRouteId = flightRouteId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flightRouteId != null ? flightRouteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the flightRouteId fields are not set
        if (!(object instanceof FlightRoute)) {
            return false;
        }
        FlightRoute other = (FlightRoute) object;
        if ((this.flightRouteId == null && other.flightRouteId != null) || (this.flightRouteId != null && !this.flightRouteId.equals(other.flightRouteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightRoute[ id=" + flightRouteId + " ]";
    }
    
}
