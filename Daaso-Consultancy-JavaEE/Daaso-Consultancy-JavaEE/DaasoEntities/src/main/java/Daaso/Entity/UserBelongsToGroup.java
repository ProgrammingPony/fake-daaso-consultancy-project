/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Entity;

import javax.persistence.Entity;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 *
 * @author Omar
 */
@Entity
@Table(name="DAASO_USER_BELONGS_TO_GROUP")
public class UserBelongsToGroup implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    @JoinColumns ({
        @JoinColumn(name = "email", referencedColumnName = "email", table = "DAASO_USER"),
        @JoinColumn(name = "groupId", referencedColumnName = "groupId", table = "DAASO_GROUP")
    })     
    private UserBelongsToGroupId id;
    
    public UserBelongsToGroup() {}

    public UserBelongsToGroupId getId() {
        return id;
    }

    public void setId(UserBelongsToGroupId id) {
        this.id = id;
    }
}
