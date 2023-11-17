/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Partner;
import javax.ejb.Remote;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author tristan
 */
@Remote
public interface PartnerSessionBeanRemote {
    public Long registerPartner(Partner p) throws UnknownPersistenceException;
}
