/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Bean.Remote;

import Daaso.Entity.UserGroup;
import Daaso.Entity.UserGroup;
import java.util.List;

/**
 *
 * @author Omar
 */

public interface UserGroupFacadeInterface {
    //From Abstract Facade
    public String create(UserGroup entity);
    public void edit(UserGroup entity);
    public void remove(UserGroup entity);
    public UserGroup find(Object id);
    public List<UserGroup> findAll();
    public List<UserGroup> findRange(int[] range);
    public int count();
    
    //Additional
}
