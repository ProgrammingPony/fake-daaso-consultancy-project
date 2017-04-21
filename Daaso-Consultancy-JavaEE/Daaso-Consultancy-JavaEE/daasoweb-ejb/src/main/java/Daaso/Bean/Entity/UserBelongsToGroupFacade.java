/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Bean.Entity;

import Daaso.Entity.UserBelongsToGroup;
import Daaso.Bean.Remote.UserBelongsToGroupFacadeInterface;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.inject.Named;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Omar
 */
@Named
@Stateless
@Remote(UserBelongsToGroupFacadeInterface.class)
public class UserBelongsToGroupFacade implements UserBelongsToGroupFacadeInterface {
    public UserBelongsToGroupFacade() {
    }
    
    @PersistenceContext(name = "Daaso-web-PU")
    private EntityManager em;
    
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    /**
     * Returns the role of the user with the provided email.
     * @param email
     * @return 
     */
    public String find(String email) {
        UserBelongsToGroup uBTG = (UserBelongsToGroup) getEntityManager().createQuery("SELECT e FROM UserBelongsToGroup e WHERE e.id.email = :email", UserBelongsToGroup.class).setParameter("email", email).getSingleResult();
        return uBTG.getId().getGroupId();
    }
    
    @Override
    public String create (UserBelongsToGroup entity) {
        getEntityManager().persist(entity);
        return "";
    }

    public void edit(UserBelongsToGroup entity) {
        getEntityManager().merge(entity);
    }


    public void remove(UserBelongsToGroup entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public UserBelongsToGroup find(Object id) {
        return getEntityManager().find(UserBelongsToGroup.class, id);
    }

    public List<UserBelongsToGroup> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(UserBelongsToGroup.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<UserBelongsToGroup> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(UserBelongsToGroup.class));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<UserBelongsToGroup> rt = cq.from(UserBelongsToGroup.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

   
    
}
