/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Bean.Remote;

import Daaso.Entity.User;
import Daaso.Entity.User;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.apache.commons.mail.EmailException;

/**
 *
 * @author Omar
 */

public interface UserFacadeInterface {
    //From Abstract Facade
    public String create(User entity) throws NoSuchAlgorithmException, EmailException;
    public void edit(User entity);
    public void remove(User entity);
    public User find(Object id);
    public List<User> findAll();
    public List<User> findRange(int[] range);
    public int count();
    
    //Additional
    public String login(String email, String password) throws NoSuchAlgorithmException;
    public byte[] generatePasswordHash (String password, byte[] salt) throws NoSuchAlgorithmException;
    public byte[] generatePasswordSalt();
    public User find(String email);
    
    public UserVerificationFacadeInterface getUserVerificationFacade();
    public UserBelongsToGroupFacadeInterface getUserBelongsToGroupFacade();
}
