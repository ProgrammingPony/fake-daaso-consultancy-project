/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Bean.Remote;

import Daaso.Entity.UserBelongsToOrganization;
import java.util.List;

/**
 *
 * @author Omar
 */
@javax.ejb.Remote
public interface UserBelongsToOrganizationFacadeRemote {

    String create(UserBelongsToOrganization userBelongsToOrganization);

    void edit(UserBelongsToOrganization userBelongsToOrganization);

    void remove(UserBelongsToOrganization userBelongsToOrganization);

    UserBelongsToOrganization find(Object id);

    List<UserBelongsToOrganization> findAll();

    List<UserBelongsToOrganization> findRange(int[] range);

    int count();
    
}
