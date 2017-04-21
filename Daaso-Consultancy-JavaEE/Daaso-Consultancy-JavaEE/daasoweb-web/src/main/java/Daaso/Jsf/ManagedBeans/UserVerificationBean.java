/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Jsf.ManagedBeans;

import javax.inject.Named;

import javax.faces.bean.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;

import javax.ejb.EJB;

import java.util.Map;

import Daaso.Entity.User;

import Daaso.Bean.Remote.UserFacadeInterface;
import Daaso.Bean.Remote.UserVerificationFacadeInterface;
import Daaso.Bean.Remote.UserBelongsToGroupFacadeInterface;

/**
 *
 * @author Omar
 */
@Named
@RequestScoped
public class UserVerificationBean {
    @EJB(beanName="UserFacade")
    private UserFacadeInterface userFacade;
    
    @EJB(beanName="UserVerificationFacade")
    private UserVerificationFacadeInterface userVerificationFacade;
    
    @EJB(beanName="UserBelongsToGroupFacade")
    private UserBelongsToGroupFacadeInterface userBelongsToGroupFacade;
    
    private String email;
    private String verificationCode;
    

    /**
     * Creates a new instance of userVerificationBean
     */
    public UserVerificationBean() {
        //Load email automatically
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
        this.email = user.getEmail();
    }
    
    /*METHODS*/
    public String verify() {
        //Verify code email combo        
        String response = userVerificationFacade.verify(email, verificationCode);
        
        //Correct email and verification code when no message
        if (response.isEmpty()) {
            //Update user session
            String activeUserRole = userBelongsToGroupFacade.find(email);
            
            Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            sessionMap.put( "activeUser", userFacade.find(email) );
            sessionMap.put( "activeUser.role", userFacade.find(activeUserRole) );
            
            //Redirect to home page TEMPORARILY
            //TODO: Create verification success graphic and page and redirect there instead
            return "index?faces-redirect=true";
            
        } else {
            //Inform user of error
            FacesMessage message = new FacesMessage("Invalid verification code");
            FacesContext context = FacesContext.getCurrentInstance();
            
            UIComponent verifyButton = context.getViewRoot().findComponent("verify-submit");
            context.addMessage(verifyButton.getClientId(context), message);
            
            return "";
        }
    }
    
    /*GETTERS AND SETTERS*/  

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }    
}
