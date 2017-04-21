/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.enterprise.context.RequestScoped;

import javax.inject.Named;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author Omar
 */
@ManagedBean
@Named(value = "currentDateAndTimeHelper")
@RequestScoped
public class CurrentDateAndTimeHelper {
    private String currentDate;
    private String currentDateTime;
    private String currentTime;
    private String currentYear;
    
    /**
     * Creates a new instance of CurrentDateAndTimeHelper
     */
    public CurrentDateAndTimeHelper() {
    }
    
    public String getCurrentDate() {
        return LocalDate.now().toString();
    }
    
    public String getCurrentDateTime() {
        return LocalDateTime.now().toString();
    }
    
    public String getCurrentTime() {
        return LocalTime.now().toString();
    }
    
    public String getCurrentYear() {
        return Integer.toString((LocalDate.now().getYear()));
    }
}
