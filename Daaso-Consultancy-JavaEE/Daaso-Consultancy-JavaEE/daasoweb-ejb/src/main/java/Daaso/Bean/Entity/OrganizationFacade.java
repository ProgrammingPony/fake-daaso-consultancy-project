/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Bean.Entity;

import Daaso.Entity.Organization;
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
public class OrganizationFacade implements Daaso.Bean.Remote.OrganizationFacadeRemote {

    public OrganizationFacade() {
    }
    
    @PersistenceContext(name = "Daaso-web-PU")
    private EntityManager em;
    
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    @Override
    public String create (Organization entity) {
        getEntityManager().persist(entity);
        return "";
    }

    @Override
    public void edit(Organization entity) {
        getEntityManager().merge(entity);
    }


    @Override
    public void remove(Organization entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Override
    public Organization find(Object id) {
        return getEntityManager().find(Organization.class, id);
    }

    @Override
    public List<Organization> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Organization.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<Organization> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Organization.class));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Organization> rt = cq.from(Organization.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
