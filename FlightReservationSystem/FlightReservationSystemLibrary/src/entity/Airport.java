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
import javax.persistence.OneToMany;

/**
 *
 * @author jonang
 */
@Entity
public class Airport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long airportId;
    @Column(nullable = false, length = 64)
    private String airportName;
    @Column(nullable = false, length = 3, unique = true)
    private String iataAirportcode;
    @Column(nullable = false)
    private String city;
    private String stateOrProvince;
    @Column(nullable = false)
    private String country;
    
    //need timezone?
    
    @OneToMany(mappedBy = "origin", fetch = FetchType.EAGER)
    private List<FlightRoute> flightsFromAirport;
    @OneToMany(mappedBy = "destination", fetch = FetchType.EAGER)
    private List<FlightRoute> flightsToAirport;
    
    public Airport() {
        flightsFromAirport = new ArrayList<>();
        flightsToAirport = new ArrayList<>();
    }

    public Airport(String airportName, String iataAirportcode, String city, String stateOrProvince, String country) {
        this();
        this.airportName = airportName;
        this.iataAirportcode = iataAirportcode;
        this.city = city;
        this.stateOrProvince = stateOrProvince;
        this.country = country;
    }

    public Long getAirportId() {
        return airportId;
    }

    public void setAirportId(Long airportId) {
        this.airportId = airportId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (airportId != null ? airportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the airportId fields are not set
        if (!(object instanceof Airport)) {
            return false;
        }
        Airport other = (Airport) object;
        if ((this.airportId == null && other.airportId != null) || (this.airportId != null && !this.airportId.equals(other.airportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Airport[ id=" + airportId + " ]";
    }
    
}
