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
public class AirCraftType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aircraftTypeId;
    @Column(nullable = false, length = 18, unique = true)
    private String aircraftTypeName;
    private Integer maxPassengerSeatCapacity;
    
    @OneToMany(mappedBy = "airCraftType")// fetch = FetchType.EAGER)
    List<AirCraftConfig> configs;

    public AirCraftType() {
        this.configs = new ArrayList<>();
    }

    public AirCraftType(String aircraftTypeName, Integer maxPassengerSeatCapacity) {
        this();
        this.aircraftTypeName = aircraftTypeName;
        this.maxPassengerSeatCapacity = maxPassengerSeatCapacity;
    }

    public Long getAircraftTypeId() {
        return aircraftTypeId;
    }

    public void setAircraftTypeId(Long aircraftTypeId) {
        this.aircraftTypeId = aircraftTypeId;
    }

    public String getAircraftTypeName() {
        return aircraftTypeName;
    }

    public void setAircraftTypeName(String aircraftTypeName) {
        this.aircraftTypeName = aircraftTypeName;
    }

    public Integer getMaxPassengerSeatCapacity() {
        return maxPassengerSeatCapacity;
    }

    public void setMaxPassengerSeatCapacity(Integer maxPassengerSeatCapacity) {
        this.maxPassengerSeatCapacity = maxPassengerSeatCapacity;
    }

    public List<AirCraftConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<AirCraftConfig> configs) {
        this.configs = configs;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aircraftTypeId != null ? aircraftTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AirCraftType)) {
            return false;
        }
        AirCraftType other = (AirCraftType) object;
        if ((this.aircraftTypeId == null && other.aircraftTypeId != null) || (this.aircraftTypeId != null && !this.aircraftTypeId.equals(other.aircraftTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AirCraftType[ id=" + aircraftTypeId+ " ]";
    }
    
}
