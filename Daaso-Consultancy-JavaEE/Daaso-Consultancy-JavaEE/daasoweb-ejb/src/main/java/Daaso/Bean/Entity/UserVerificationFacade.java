/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Bean.Entity;

import Daaso.Common.DaasoConverter;
import Daaso.Entity.UserVerification;
import Daaso.Bean.Remote.UserVerificationFacadeInterface;

import java.security.SecureRandom;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.inject.Named;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.Query;

/**
 *
 * @author Omar
 */
@Named
@Stateless
@Remote(UserVerificationFacadeInterface.class)
public class UserVerificationFacade implements UserVerificationFacadeInterface {
    public UserVerificationFacade() {
    }
    
    @PersistenceContext(name = "Daaso-web-PU")
    private EntityManager em;
    
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    @Override
    public String generateCode() {
        SecureRandom random = new SecureRandom();
        byte verificationBytes[] = new byte[10];
        random.nextBytes(verificationBytes);
        String verificationString = DaasoConverter.toHexString(verificationBytes);
        
        return verificationString;
    }
    
    public String verify(String email, String code) {
        //TODO
        TypedQuery q1 = getEntityManager().createQuery("SELECT e FROM UserVerification e WHERE e.email = :email", UserVerification.class);
        UserVerification actualVerificationCombo = (UserVerification) q1.setParameter("email", email).getSingleResult();
        
        if (actualVerificationCombo == null) {
            return "No verification combination exists for this email";
            //TODO: check if user valid if not then create combo
        } else {
            //Check if it matches
            String actualCode = actualVerificationCombo.getCode();
            String requestCode = code;
            
            //When the two codes match
            if (actualCode.equals(requestCode))  {
                //Set user as verified
                //TODO: try using abstract edit method for this
                Query q2 = getEntityManager().createQuery("UPDATE User e SET e.verified = true WHERE e.email = :email");
                q2.setParameter("email", email).executeUpdate();
                
                //Delete code
                //TODO: try using abstract remove method instead
                Query q3 = getEntityManager().createQuery("DELETE FROM UserVerification e WHERE e.email = :email");
                q3.setParameter("email", email).executeUpdate();
                return "";
            
            //When they don't match
            } else {
                return "Verification code does not match";
            }
        }
    }
    
    @Override
    public String create (UserVerification entity) {
        getEntityManager().persist(entity);
        return "";
    }

    public void edit(UserVerification entity) {
        getEntityManager().merge(entity);
    }


    public void remove(UserVerification entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public UserVerification find(Object id) {
        return getEntityManager().find(UserVerification.class, id);
    }

    public List<UserVerification> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(UserVerification.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<UserVerification> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(UserVerification.class));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<UserVerification> rt = cq.from(UserVerification.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
