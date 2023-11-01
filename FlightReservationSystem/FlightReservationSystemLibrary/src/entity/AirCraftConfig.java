/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 *
 * @author jonang
 */
@Entity
public class AirCraftConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airCraftConfigId;
    
    @Column(nullable = false, length = 64, unique = true)
    private String airCraftConfigName;
    
    @Column(nullable = false)
    @Min(1)
    @Max(4)
    @NotNull
    private Integer numOfCabinClasses;

    private Integer maxSeatCapacity;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private AirCraftType airCraftType;
    
    
    @OneToMany(mappedBy = "airCraftConfig", fetch = FetchType.EAGER)
    private List<Flight> flights;
    
    @OneToMany(mappedBy = "airCraftConfig", cascade = CascadeType.PERSIST)
    private List<CabinClass> cabinClasses;
    
    public AirCraftConfig() {
        this.cabinClasses = new ArrayList<>();
        this.flights = new ArrayList<>();
    }
    
    public AirCraftConfig(String airCraftConfigName, Integer numOfCabinClasses) {
        this();
        
        this.airCraftConfigName = airCraftConfigName;
        this.numOfCabinClasses = numOfCabinClasses;
    }



    public void setAirCraftConfigId(Long airCraftConfigId) {
        this.airCraftConfigId = airCraftConfigId;
    }
    
    public Long getAirCraftConfigId() {
        return airCraftConfigId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (airCraftConfigId != null ? airCraftConfigId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the airCraftConfigId fields are not set
        if (!(object instanceof AirCraftConfig)) {
            return false;
        }
        AirCraftConfig other = (AirCraftConfig) object;
        if ((this.airCraftConfigId == null && other.airCraftConfigId != null) || (this.airCraftConfigId != null && !this.airCraftConfigId.equals(other.airCraftConfigId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AirCraftConfig[ id=" + airCraftConfigId + " ]";
    }
    
}
