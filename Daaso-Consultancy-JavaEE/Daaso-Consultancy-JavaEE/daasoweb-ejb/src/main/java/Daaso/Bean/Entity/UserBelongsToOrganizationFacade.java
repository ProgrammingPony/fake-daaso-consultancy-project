/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Bean.Entity;

import Daaso.Entity.UserBelongsToOrganization;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Omar
 */
@Named
@Stateless
public class UserBelongsToOrganizationFacade implements Daaso.Bean.Remote.UserBelongsToOrganizationFacadeRemote {

    public UserBelongsToOrganizationFacade() {
    }
    
    @PersistenceContext(name = "Daaso-web-PU")
    private EntityManager em;
    
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    @Override
    public String create (UserBelongsToOrganization entity) {
        getEntityManager().persist(entity);
        return "";
    }

    public void edit(UserBelongsToOrganization entity) {
        getEntityManager().merge(entity);
    }


    public void remove(UserBelongsToOrganization entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public UserBelongsToOrganization find(Object id) {
        return getEntityManager().find(UserBelongsToOrganization.class, id);
    }

    public List<UserBelongsToOrganization> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(UserBelongsToOrganization.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<UserBelongsToOrganization> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(UserBelongsToOrganization.class));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<UserBelongsToOrganization> rt = cq.from(UserBelongsToOrganization.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
