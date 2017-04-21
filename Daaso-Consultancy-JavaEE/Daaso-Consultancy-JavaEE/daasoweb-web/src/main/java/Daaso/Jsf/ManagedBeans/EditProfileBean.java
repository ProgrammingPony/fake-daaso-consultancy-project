/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Jsf.ManagedBeans;

import javax.enterprise.context.RequestScoped;

import javax.ejb.EJB;

import javax.faces.context.FacesContext;

import javax.inject.Named;

import java.util.Map;

import Daaso.Entity.User;

import Daaso.Bean.Remote.UserFacadeInterface;

/**
 * JSF to handle profile changes. For profile.xhtml
 * @author Omar
 */
@Named
@RequestScoped
public class EditProfileBean {

    @EJB(beanName="UserFacade")
    UserFacadeInterface userFacade;
    
    //Any non editables should be read from session variable
    private String firstName, lastName;
    private String phone;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    /**
     * Creates a new instance of EditProfileBean
     */
    public EditProfileBean() {
        //Retrieve User Data From Session
        User activeUser;
        
        Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        activeUser = (User) sessionMap.get("activeUser");
        
        this.firstName = activeUser.getFirstName();
        this.lastName = activeUser.getLastName();
        this.phone = activeUser.getPhone();
    }
    
    public String updateProfile() {
        //Retrieve User Data From Session
        User activeUser;
        
        Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        activeUser = (User) sessionMap.get("activeUser");
        
        activeUser.setFirstName(firstName);
        activeUser.setLastName(lastName);
        activeUser.setPhone(phone);
        
        userFacade.edit(activeUser);
        
        return "Profile Successfully Editted";
    }
    
}
