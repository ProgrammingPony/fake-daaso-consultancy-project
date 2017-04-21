/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Bean.Entity;

import Daaso.Bean.Remote.UserBelongsToGroupFacadeInterface;
import Daaso.Entity.User;
import Daaso.Bean.Remote.UserFacadeInterface;
import Daaso.Bean.Remote.UserVerificationFacadeInterface;
import Daaso.Entity.UserBelongsToGroup;
import Daaso.Entity.UserBelongsToGroupId;
import Daaso.Entity.UserVerification;

import javax.ejb.Stateless;
import javax.ejb.Remote;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.apache.commons.mail.EmailException;

/**
 *
 * @author Omar
 */
@Named
@Stateless
@Remote(UserFacadeInterface.class)
public class UserFacade implements UserFacadeInterface {
   
    @EJB
    private UserVerificationFacadeInterface userVerificationFacade;
    
    @EJB
    private UserBelongsToGroupFacadeInterface userBelongsToGroupFacade;
    
    @PersistenceContext(name = "Daaso-web-PU")
    private EntityManager em;
    
    protected EntityManager getEntityManager() {
        return this.em;
    }

    @Override
    public UserVerificationFacadeInterface getUserVerificationFacade() {
        return userVerificationFacade;
    }

    @Override
    public UserBelongsToGroupFacadeInterface getUserBelongsToGroupFacade() {
        return userBelongsToGroupFacade;
    }

    public UserFacade() {
    }
    
    @Override
    @Transactional
    public String create (User entity) {        
        //Ensure user does not already exist
        try {
            find( entity.getEmail() );
            return "The provided email is already registered";
        } catch (NoResultException e) {
            //This means that the email is not in use
            
        }
        
        //Set user to common group
        UserBelongsToGroupId userBTGId = new UserBelongsToGroupId();
        userBTGId.setEmail(entity.getEmail());
        userBTGId.setGroupId("COMMON");
        
        UserBelongsToGroup userBTG = new UserBelongsToGroup();        
        userBTG.setId(userBTGId);
        
        userBelongsToGroupFacade.create(userBTG);
        
        //Prepare and store verification code
        String verificationCode = userVerificationFacade.generateCode();

        UserVerification userVerification = new UserVerification();
        userVerification.setCode(verificationCode);
        userVerification.setEmail(entity.getEmail());            

        userVerificationFacade.create(userVerification);

        //TEMPORARY: print verification code
        System.out.println("Verification Code:" + verificationCode);

        //TODO: Send validation mail (Unable to initialize daasoMailer)
        /*
        SimpleEmail email = new SimpleEmail();

        email.setFrom("verification@daaso-consultancy.com", "DaaSo Consultancy");
        email.setSubject("DaaSo Account Verification Code");
        email.setMsg( "Your Account Verification Code is " + verificationCode );
        email.addTo(this.email, "DaaSo Verification");
        email.setStartTLSRequired(false); //Only for dev

        daasoMailer.sendMail(email, SenderType.VERIFICATION);
        */
        
        //Log user in automatically
        Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put( "activeUser", find(entity.getEmail()) );
        sessionMap.put( "activeUser.role", "COMMON");
        
        getEntityManager().persist(entity);
        return "";
    }
    
    @Override
    @Transactional
    public String login(String email, String password) throws NoSuchAlgorithmException {        
        //Fetch actual password hash and salt
        User user;
        
        try {
            user = (User) getEntityManager().createQuery("SELECT e from User e WHERE e.email = :email", User.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return "No such email is registered";
        }
        
        byte [] actualPasswordHash = user.getPassword();
        byte [] salt = user.getPasswordSalt();

        //Calculate hash with salt and password        
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] passwordByte = password.getBytes();
        byte [] preDigest = new byte[salt.length + passwordByte.length];
        System.arraycopy(salt, 0, preDigest, 0, salt.length);
        System.arraycopy(passwordByte, 0, preDigest, salt.length, passwordByte.length);

        md.update(preDigest);
        byte[] hashPassword = md.digest();

        //Compare with actual password hash
        if ( Arrays.equals(actualPasswordHash,hashPassword) ) {
            //TODO: set user session

            //Update last login
            this.updateLastLogin(email);

            return "";
        } else {
            return "The password combination is not valid";
        }
    }
    
       
    /**
     * Generate password hash given a salt and a plaintext password
     * @param password
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException 
     */
    @Override
    public byte[] generatePasswordHash (String password, byte[] salt) throws NoSuchAlgorithmException {
        //Hash password
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        
        byte[] passwordByte =  password.getBytes();
        byte [] preDigest = new byte[salt.length + passwordByte.length];
        System.arraycopy(salt, 0, preDigest, 0, salt.length);
        System.arraycopy(passwordByte, 0, preDigest, salt.length, passwordByte.length);
        
        md.update(preDigest);
        byte[] hashPassword = md.digest();
        
        return hashPassword;
    }
    
    /**
     * Generates a password salt using SecureRandom
     * @return byte[20]
     */
    @Override
    public byte[] generatePasswordSalt() {
        SecureRandom random = new SecureRandom();
        byte salt[] = new byte[20];
        random.nextBytes(salt);
        
        return salt;
    }
    
    /**
     * Returns a user entity given its unique email. Entity is not checked before return.
     * @param email
     * @return User
     */
    @Override
    @Transactional
    public User find(String email) {        
        return (User) getEntityManager().createQuery("SELECT e FROM User e WHERE e.email = :email", User.class).setParameter("email", email).getSingleResult();
    }

    /**
     * Set user's last login to the current time.
     * @param email 
     */
    @Transactional
    private void updateLastLogin(String email) {
        getEntityManager().createQuery("UPDATE User e SET e.lastLogin = current_timestamp() WHERE e.email = :email").setParameter("email", email).executeUpdate();
    }

    public void edit(User entity) {
        getEntityManager().merge(entity);
    }


    public void remove(User entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public User find(Object id) {
        return getEntityManager().find(User.class, id);
    }

    public List<User> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<User> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<User> rt = cq.from(User.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
