/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Bean.Remote;

import Daaso.Entity.Organization;
import java.util.List;

/**
 *
 * @author Omar
 */
@javax.ejb.Remote
public interface OrganizationFacadeRemote {

    String create(Organization organization);

    void edit(Organization organization);

    void remove(Organization organization);

    Organization find(Object id);

    List<Organization> findAll();

    List<Organization> findRange(int[] range);

    int count();
    
}
