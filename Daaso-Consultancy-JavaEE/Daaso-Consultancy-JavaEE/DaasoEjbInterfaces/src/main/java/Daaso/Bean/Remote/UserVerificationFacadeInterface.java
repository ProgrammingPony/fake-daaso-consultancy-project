/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Bean.Remote;

import Daaso.Entity.UserVerification;

import Daaso.Entity.UserVerification;
import java.util.List;

/**
 *
 * @author Omar
 */

public interface UserVerificationFacadeInterface {
    //From Abstract Facade
    public String create(UserVerification entity);
    public void edit(UserVerification entity);
    public void remove(UserVerification entity);
    public UserVerification find(Object id);
    public List<UserVerification> findAll();
    public List<UserVerification> findRange(int[] range);
    public int count();
    
    //Additional
    public String generateCode();
    public String verify(String email, String code);
}
