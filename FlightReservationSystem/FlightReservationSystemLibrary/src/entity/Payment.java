/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author jonang
 */
@Entity
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Long flightId;

    private LocalDate paymentDate;
    private String status;
    //creditCard: cardNumber, nameOnCard, expiryDate, CVV
    private List<String> creditCard;
    //optional - either customer or partner
    private Customer customer;
    //optional - either customer or partner
    
    private Partner partner;
    
    //@OneToOne
    //private Reservation reservation;
    
    public Payment () {
        this.creditCard = new ArrayList<>();        
    }

    public Long getFlightId() {
        return flightId;
    }
    
    public List<String> getCreditCard() {
        return this.creditCard;
    }
    
    public void setCreditCard(String cardNumber, String nameOnCard, String expiryDate, String cvv) {

        this.creditCard.add(cardNumber);
        this.creditCard.add(nameOnCard);
        this.creditCard.add(expiryDate);
        this.creditCard.add(cvv);
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentId != null ? paymentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the paymentId fields are not set
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.paymentId == null && other.paymentId != null) || (this.paymentId != null && !this.paymentId.equals(other.paymentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Payment[ id=" + paymentId + " ]";
    }
    
}
