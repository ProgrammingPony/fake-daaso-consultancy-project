/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Jsf.ManagedBeans;

import javax.annotation.PostConstruct;

import javax.enterprise.context.SessionScoped;

import javax.inject.Named;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import java.security.NoSuchAlgorithmException;

import java.util.Map;

import Daaso.Entity.User;

import Daaso.Bean.Remote.UserBelongsToGroupFacadeInterface;
import Daaso.Bean.Remote.UserFacadeInterface;
import javax.ejb.EJB;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 *
 * @author Omar
 */
@Named
@SessionScoped
public class LoginBean implements java.io.Serializable {
    
    private int loginAttemptsRemaining;
    
    @EJB
    private UserFacadeInterface userFacade;
    
    @EJB
    private UserBelongsToGroupFacadeInterface userBelongsToGroupFacade;
    
    //@ManagedProperty(value="#{activeUser}")
    @Inject
    private User activeUser;
    
    //@ManagedProperty(value="#{activeUser.role}")
    @Inject
    private String activeUserRole;
    
    private String email;
    private String password;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
        this.loginAttemptsRemaining = 3;
    }
    
    @PostConstruct
    public void init() {
        this.loginAttemptsRemaining = 3;
    }
    
    /*FUNCTIONS*/
    public String login() throws NoSuchAlgorithmException {
        //Process login
        String response = userFacade.login(email, password); 
        
        //When response is empty there is no problems, otherwise display message
        if (response.isEmpty()) {
            //Set user session
            activeUser = userFacade.find(email);
            activeUserRole = userBelongsToGroupFacade.find(email);
            
            Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            sessionMap.put("activeUser", activeUser);
            sessionMap.put("activeUser.role", activeUserRole);            
            
            //If the user has not been verified yet redirect to verification page, otherwise to landing page
            if (activeUser.isVerified()) {
                return "index?faces-redirect=true";
            } else {
                return "user-verification?faces-redirect=true";
            }
            
        } else {
            //Reduce number of login attempts remaining
            loginAttemptsRemaining--;

            //Inform user that combination is wrong
            FacesMessage message = new FacesMessage(response + ". You have " + this.loginAttemptsRemaining + " tries remaining.");
            FacesContext context = FacesContext.getCurrentInstance();
            
            UIComponent loginButton = context.getViewRoot().findComponent("login-button");
            context.addMessage(loginButton.getClientId(context), message);
            
            return "";
        }      
    }
    
    /**
     * Invalidates session, starts new one and redirects to landing page.
     * @return String
     */
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
        return "index?faces-redirect=true";
    }
    
    
    /*GETTERS AND SETTERS*/
    public int getLoginAttemptsRemaining() {
        return loginAttemptsRemaining;
    }

    public void setLoginAttemptsRemaining(int loginAttemptsRemaining) {
        this.loginAttemptsRemaining = loginAttemptsRemaining;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Named("activeUser") @Produces
    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    @Named("activeUserRole") @Produces
    public String getActiveUserRole() {
        return activeUserRole;
    }

    public void setActiveUserRole(String activeUserRole) {
        this.activeUserRole = activeUserRole;
    }    
}
