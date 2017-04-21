/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Entity;

import javax.persistence.Lob;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Past;

import javax.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.LocalDateTime;

import Daaso.Hibernate.LocalDatePersistenceConverter;
import Daaso.Hibernate.LocalDateTimePersistenceConverter;
import javax.validation.constraints.Size;

/**
 *
 * @author Omar
 */
@Entity
@Table(name="DAASO_USER")
public class User implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="USER_ID", unique=true, nullable=false, precision=9, scale=0)
    private int id;
    
    @Size(min=1)
    @Column (name="EMAIL", length=320, unique=true, nullable=false)
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
                     + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9]"
              + "(?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]"
              + "(?:[a-z0-9-]*[a-z0-9])?", 
              message = "{invalid.email}")
    private String email;
    
    @Lob
    @Column (name="PASSWORD_SALT", length=20)
    private byte[] password_salt;
    
    @Lob
    @Column (name="PASSWORD", length=1024)
    private byte[] password;   
    
    @Column (name="GPLUS_ID", length=255, unique=true)
    private String gplusId;
    
    @Column (name="FACEBOOK_ID", length=255, unique=true)
    private String facebookId;
    
    @Column (name="LINKEDIN_ID", length=255, unique=true)
    private String linkedinId;
    
    @Column (name="FIRST_NAME", length = 35, nullable=false)
    private String firstName;
    
    @Column (name="LAST_NAME", length = 35, nullable=false)
    private String lastName;
    
    @Column (name="DATE_OF_BIRTH")
    @Past
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate dateOfBirth;
    
    @Column (name="LAST_LOGIN")
    @Past
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime lastLogin = LocalDateTime.now();
    
    @Column (name="DATE_REGISTERED")
    @Past
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate dateRegistered = LocalDate.now();
    
    @Column (name="PHONE", length=50)
    private String phone;
    
    @Column (name="ADDRESS", length=260)
    private String address;
    
    @Column (name="IS_VERIFIED")
    private boolean verified;
    
    public User () {}

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }  

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
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

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGplusId() {
        return gplusId;
    }

    public void setGplusId(String gplusId) {
        this.gplusId = gplusId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getLinkedinId() {
        return linkedinId;
    }

    public void setLinkedinId(String linkedinId) {
        this.linkedinId = linkedinId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public byte[] getPassword_salt() {
        return password_salt;
    }

    public void setPassword_salt(byte[] password_salt) {
        this.password_salt = password_salt;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public byte[] getPasswordSalt() {
        return password_salt;
    }

    public void setPasswordSalt(byte[] password_salt) {
        this.password_salt = password_salt;
    }
}
