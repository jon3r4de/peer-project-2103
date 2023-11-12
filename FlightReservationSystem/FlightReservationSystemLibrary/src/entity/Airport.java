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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jonang
 */
@Entity
public class Airport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airportId;
    
    @Column(nullable = false, length = 64)
    @Size(min = 1, max = 64)
    @NotNull
    private String airportName;
    
    @Column(nullable = false, unique = true, length = 3)
    @Size(min = 3, max = 3)
    @NotNull
    private String iataAirportcode;
    
    @Column(nullable = false, length = 64)
    @Size(min = 1, max = 64)
    @NotNull
    private String city;
    
    @Column(nullable = false, length = 64)
    @Size(min = 1, max = 64)
    @NotNull
    private String stateOrProvince;
        
    @Column(nullable = false, length = 64)
    @Size(min = 1, max = 64)
    @NotNull
    private String country;
    
    
    @OneToMany(mappedBy = "origin")// fetch = FetchType.EAGER)
    private List<FlightRoute> flightsFromAirport;
    @OneToMany(mappedBy = "destination")// fetch = FetchType.EAGER)
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

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getIataAirportcode() {
        return iataAirportcode;
    }

    public void setIataAirportcode(String iataAirportcode) {
        this.iataAirportcode = iataAirportcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<FlightRoute> getFlightsFromAirport() {
        return flightsFromAirport;
    }

    public void setFlightsFromAirport(List<FlightRoute> flightsFromAirport) {
        this.flightsFromAirport = flightsFromAirport;
    }

    public List<FlightRoute> getFlightsToAirport() {
        return flightsToAirport;
    }

    public void setFlightsToAirport(List<FlightRoute> flightsToAirport) {
        this.flightsToAirport = flightsToAirport;
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
        return "airport iata code :" + iataAirportcode + " in the city :" + city;
    }
    
}
