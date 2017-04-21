/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Embeddable;

/**
 * Example: http://www.thejavageek.com/2014/05/01/jpa-embeddedid-example/
 * @author Omar
 */
@Embeddable
public class UserBelongsToGroupId implements java.io.Serializable {
    @Column (name="EMAIL")
    private String email;
    
    @Column (name="GROUP_ID")
    private String groupId;

    public UserBelongsToGroupId() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
