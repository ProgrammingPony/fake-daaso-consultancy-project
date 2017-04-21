/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Jsf.ManagedBeans;

import javax.enterprise.context.RequestScoped;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.util.Map;

import Daaso.Bean.Remote.UserFacadeInterface;
import Daaso.Bean.Remote.UserVerificationFacadeInterface;
import Daaso.Bean.Remote.UserBelongsToGroupFacadeInterface;
import Daaso.Mail.DaasoMailerInterface;

import javax.inject.Named;

import Daaso.Entity.User;
import javax.ejb.EJB;

/**
 *
 * @author Omar
 */
@Named
@RequestScoped
public class CreateUserBean { 
    @EJB
    private UserFacadeInterface userFacade;
    
    /*
    @EJB
    private UserVerificationFacadeInterface userVerificationFacade;
    
    @EJB
    private UserBelongsToGroupFacadeInterface userBelongsToGroupFacade;
    */
    
    //@EJB(beanName="DaasoMailer")
    //private DaasoMailerInterface daasoMailer;
    
    private String email;
    private String firstName, lastName;
    private String password;
    
    
    /**
     * Creates a new instance of CreateUserBean
     */
    public CreateUserBean() {}
    
    /*METHODS*/
    /**
     * Creates new user if email not already existant
     * @return String
     */
    public String createUser() throws NoSuchAlgorithmException, EmailException {
        
        //Prepare and store user data
        User user = new User();
        user.setEmail(this.email);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        
        byte[] salt = userFacade.generatePasswordSalt();
        user.setPasswordSalt(salt);
        
        byte[] passwordHash = userFacade.generatePasswordHash(this.password, salt);        
        user.setPassword(passwordHash);
        
        userFacade.create(user);
        
        //Temporary redirect to home page until verification mail fixed
        return "index?faces-redirect=true";
        
        //Redirect to user validation page
        //return "user-verification?faces-redirect=true";
    }
    
    /**
     * Validates password matches confirm password field
     * Reference:http://www.mkyong.com/jsf2/multi-components-validator-in-jsf-2-0/
     * @param event
     */
    public void validatePassword(ComponentSystemEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();

        // get password
        UIInput uiInputPassword = (UIInput) components.findComponent("login-spassword");
        String password = uiInputPassword.getLocalValue() == null ? ""
              : uiInputPassword.getLocalValue().toString();
        String passwordId = uiInputPassword.getClientId();

        // get confirm password
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("login-scpassword");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
              : uiInputConfirmPassword.getLocalValue().toString();

        // Let required="true" do its job.
        if (password.isEmpty() || confirmPassword.isEmpty()) {
              return;
        }

        if (!password.equals(confirmPassword)) {

              FacesMessage msg = new FacesMessage("Password must match confirm password");
              msg.setSeverity(FacesMessage.SEVERITY_ERROR);
              fc.addMessage(passwordId, msg);
              fc.renderResponse();

        }
    }
    
    

    /*GETTERS AND SETTERS*/
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}