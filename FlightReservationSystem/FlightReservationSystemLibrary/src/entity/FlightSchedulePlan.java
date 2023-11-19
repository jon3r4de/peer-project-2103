/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import enumeration.FlightScheduleEnum;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author jonang
 */
@Entity
public class FlightSchedulePlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightSchedulePlanId;
        
    private FlightSchedulePlan complementaryReturnSchedulePlan;
    
    @Column(nullable = true, length = 64)
    private String flightSchedulePlanName;
    
    //only for n days recurrence
    private int recurrence;
    
    //(Monday - Sunday) Only for reccurent by week
    private String reccurrentDay;
    private Date startDate;
    private Date endDate;
    
    @Column(nullable = true)
    private Duration layoverDuration;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Flight flight;
    
    @Column(nullable = false)
    private String flightNumber;
    
    @Column(nullable = false)
    private FlightScheduleEnum flightScheduleType;
    
    @OneToMany(mappedBy = "flightSchedulePlan")
    private List<FlightSchedule> flightSchedules;
    
    @OneToMany(mappedBy = "flightSchedulePlan", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Fare> fares;
    
    private Boolean disabled;
    
    public FlightSchedulePlan() {
        flightSchedules = new ArrayList<>();
        fares = new ArrayList<>();
        this.disabled = false;
        this.flightSchedulePlanName = "Flight schedule plan "+ this.nameGenerator();
        this.disabled = false; // auto set to false
    }
    
    public FlightSchedulePlan(FlightScheduleEnum flightScheduleType, List<Fare> fares) {
        this();
        this.flightScheduleType = flightScheduleType;
        this.fares = fares;
       
    }


    public String getFlightSchedulePlanName() {
        return flightSchedulePlanName;
    }

    public void setFlightSchedulePlanName(String flightSchedulePlanName) {
        this.flightSchedulePlanName = flightSchedulePlanName;
    }
    
    public Long getFlightSchedulePlanId() {
        return flightSchedulePlanId;
    }

    public void setFlightSchedulePlanId(Long flightSchedulePlanId) {
        this.flightSchedulePlanId = flightSchedulePlanId;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public List<Fare> getFares() {
        return fares;
    }

    public void setFares(List<Fare> fares) {
        this.fares = fares;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public List<FlightSchedule> getFlightSchedules() {
        return flightSchedules;
    }

    public void setFlightSchedules(List<FlightSchedule> flightSchedules) {
        this.flightSchedules = flightSchedules;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Duration getLayoverDuration() {
        return layoverDuration;
    }

    public void setLayoverDuration(Duration layoverDuration) {
        this.layoverDuration = layoverDuration;
    }

    public FlightScheduleEnum getFlightScheduleType() {
        return flightScheduleType;
    }

    public void setFlightScheduleType(FlightScheduleEnum flightScheduleType) {
        this.flightScheduleType = flightScheduleType;
    }

    public int getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(Integer recurrence) {
        this.recurrence = recurrence;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public FlightSchedulePlan getComplementaryReturnSchedulePlan() {
        return complementaryReturnSchedulePlan;
    }

    public void setComplementaryReturnSchedulePlan(FlightSchedulePlan complementaryReturnSchedulePlan) {
        this.complementaryReturnSchedulePlan = complementaryReturnSchedulePlan;
    }

    public String getReccurrentDay() {
        return reccurrentDay;
    }

    public void setReccurrentDay(String reccurrentDay) {
        this.reccurrentDay = reccurrentDay;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flightSchedulePlanId != null ? flightSchedulePlanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the flightSchedulePlanId fields are not set
        if (!(object instanceof FlightSchedulePlan)) {
            return false;
        }
        FlightSchedulePlan other = (FlightSchedulePlan) object;
        if ((this.flightSchedulePlanId == null && other.flightSchedulePlanId != null) || (this.flightSchedulePlanId != null && !this.flightSchedulePlanId.equals(other.flightSchedulePlanId))) {
            return false;
        }
        return true;
    }
    
    public static String nameGenerator() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
          .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
          .limit(targetStringLength)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();

        return generatedString;
    }

    @Override
    public String toString() {
        SimpleDateFormat outputEndDateFormat = new SimpleDateFormat("dd MMM yy");
        SimpleDateFormat outputLayoverFormat = new SimpleDateFormat("hh 'Hours' mm 'Minutes'");
        //String endDateString = outputEndDateFormat.format(endDate);
        String endDateString = (endDate != null) ? outputEndDateFormat.format(endDate) : "N/A";
        if (this.getFlightScheduleType().equals(FlightScheduleEnum.RECURRENTWEEK)) {
            return "[Flight = " + flight + "Flight Schedule Type = " + flightScheduleType + ", End Date = " + endDateString + ']';
        } else if (this.getFlightScheduleType().equals(FlightScheduleEnum.RECURRENTDAY)) {
            return "[Flight = " + flight + "Flight Schedule Type = " + flightScheduleType + ", Recurrence = " + recurrence + ", End Date = " + endDateString +  ']';
        } else {
            return "[Flight = " + flight + "Flight Schedule Type = " + flightScheduleType +  ']';
        }
    }
    
    /*
        Duration layoverDuration = Duration.ofHours(2);

        // Extracting hours and minutes
        long hours = layoverDuration.toHours();
        long minutes = layoverDuration.minusHours(hours).toMinutes();

        // Constructing the formatted string
        String layoverString = String.format("%d Hours %d Minutes", hours, minutes);
    */
    
}
