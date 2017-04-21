/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Lob;

/**
 *
 * @author Omar
 */
@Entity
@Table(name="DAASO_USER_VERIFICATION")
public class UserVerification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="VERIFICATION_ID", unique=true, nullable=false, precision=9, scale=0)
    private int id;

    @Column (name="CODE", length=20)
    private String code;
    
    @JoinColumn(name = "email", referencedColumnName = "email", table = "DAASO_USER")
    @Column(name="EMAIL", unique=true)
    private String email;
    
    /*OVERRIDES*/
    @Override
    public String toString() {
        //TODO: Update fields
        return "Daaso.Entity.UserVerification[ id=" + id + " ]";
    }    
    
    /*GETTERS AND ACCESSORS*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
  
}
