/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Bean.Remote;
import Daaso.Entity.UserBelongsToGroup;
import java.util.List;

/**
 *
 * @author Omar
 */

public interface UserBelongsToGroupFacadeInterface {
    //From Abstract Facade
    public String create(UserBelongsToGroup entity);
    public void edit(UserBelongsToGroup entity);
    public void remove(UserBelongsToGroup entity);
    public UserBelongsToGroup find(Object id);
    public List<UserBelongsToGroup> findAll();
    public List<UserBelongsToGroup> findRange(int[] range);
    public int count();
    
    //Additional
    public java.lang.String find(java.lang.String email);
}
