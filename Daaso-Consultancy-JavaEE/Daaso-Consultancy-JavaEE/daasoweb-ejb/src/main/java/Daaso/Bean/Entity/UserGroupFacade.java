/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Bean.Entity;

import Daaso.Entity.UserGroup;
import Daaso.Bean.Remote.UserGroupFacadeInterface;
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
@Remote(UserGroupFacadeInterface.class)
public class UserGroupFacade implements UserGroupFacadeInterface {

    public UserGroupFacade() {
    }
    
    @PersistenceContext(name = "Daaso-web-PU")
    private EntityManager em;
    
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    @Override
    public String create (UserGroup entity) {
        getEntityManager().persist(entity);
        return "";
    }

    public void edit(UserGroup entity) {
        getEntityManager().merge(entity);
    }


    public void remove(UserGroup entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public UserGroup find(Object id) {
        return getEntityManager().find(UserGroup.class, id);
    }

    public List<UserGroup> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(UserGroup.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<UserGroup> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(UserGroup.class));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<UserGroup> rt = cq.from(UserGroup.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
