/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 *
 * @author jonang
 */
@Entity
public class FlightSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightScheduleId;
   
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date departureDateTime;
  
    @Column(nullable = false)
    @Min(0)
    private Integer estimatedFlightDurationHours;
   
    @Column(nullable = false)
    @Max(60)
    @Min(0)
    private Integer estimatedFlightDurationMinutes;
    //actual flight duration can be calculated from manipulation of arrival and departure time
   
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date arrivalDateTime;
   
    @Column(nullable = false, length = 6, unique = false)
    private String flightNumber;

    @ManyToMany(mappedBy = "flightSchedules")
    private List<Reservation> reservations;

    
    @OneToMany(mappedBy = "flightSchedule")
    private List<CabinClass> cabinClasses;
   
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private FlightSchedulePlan flightSchedulePlan;

    public FlightSchedule() {
        this.reservations = new ArrayList<>();
        this.cabinClasses = new ArrayList<>();
    }

    public FlightSchedule(Date departureDateTime, Integer estimatedFlightDurationHours, Integer estimatedFlightDurationMinutes,String flightNumber, List<CabinClass> cabinClasses, FlightSchedulePlan flightSchedulePlan) {
        this.departureDateTime = departureDateTime;
        this.estimatedFlightDurationHours = estimatedFlightDurationHours;
        this.estimatedFlightDurationMinutes = estimatedFlightDurationMinutes;
        this.flightNumber = flightNumber;
        this.cabinClasses = cabinClasses;
        this.flightSchedulePlan = flightSchedulePlan;
    }

    public Date getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(Date departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public Integer getEstimatedFlightDurationHours() {
        return estimatedFlightDurationHours;
    }

    public void setEstimatedFlightDurationHours(Integer estimatedFlightDurationHours) {
        this.estimatedFlightDurationHours = estimatedFlightDurationHours;
    }

    public Integer getEstimatedFlightDurationMinutes() {
        return estimatedFlightDurationMinutes;
    }

    public void setEstimatedFlightDurationMinutes(Integer estimatedFlightDurationMinutes) {
        this.estimatedFlightDurationMinutes = estimatedFlightDurationMinutes;
    }
    


  public Date getArrivalDateTime() {
        if (this.arrivalDateTime == null) {
            calculateArrivalTime();
        }
        return arrivalDateTime;
    }

    public void setArrivalDateTime(Date arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }
    
    public void calculateArrivalTime() {
        this.arrivalDateTime = Date.from(this.departureDateTime.toInstant().plus(this.estimatedFlightDurationHours, ChronoUnit.HOURS).plus(this.estimatedFlightDurationMinutes, ChronoUnit.MINUTES));
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Long getFlightScheduleId() {
        return flightScheduleId;
    }

    public void setFlightScheduleId(Long flightScheduleId) {
        this.flightScheduleId = flightScheduleId;
    }

    public FlightSchedulePlan getFlightSchedulePlan() {
        return flightSchedulePlan;
    }

    public void setFlightSchedulePlan(FlightSchedulePlan flightSchedulePlan) {
        this.flightSchedulePlan = flightSchedulePlan;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<CabinClass> getCabinClasses() {
        return cabinClasses;
    }

    public void setCabinClasses(List<CabinClass> cabinClasses) {
        this.cabinClasses = cabinClasses;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flightScheduleId != null ? flightScheduleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the flightScheduleId fields are not set
        if (!(object instanceof FlightSchedule)) {
            return false;
        }
        FlightSchedule other = (FlightSchedule) object;
        if ((this.flightScheduleId == null && other.flightScheduleId != null) || (this.flightScheduleId != null && !this.flightScheduleId.equals(other.flightScheduleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        DateFormat departureTimeFormat = new SimpleDateFormat("hh:mm aa");
        DateFormat departureDateFormat = new SimpleDateFormat("dd MMM yy");
        SimpleDateFormat outputDurationFormat = new SimpleDateFormat("hh Hours mm Minutes");
        String outputDepartDateString = departureDateFormat.format(this.departureDateTime);
        String flightDurationString = "hours : " + this.estimatedFlightDurationHours + " , Minutes : " + this.estimatedFlightDurationMinutes;
        return "[ Departure date time = " + outputDepartDateString + ", Flight duration = " + flightDurationString + " ]";
    }
    
}
