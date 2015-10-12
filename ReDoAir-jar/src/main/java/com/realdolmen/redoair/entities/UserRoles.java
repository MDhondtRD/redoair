package com.realdolmen.redoair.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by JDOAX80 on 12/10/2015.
 */
@Entity
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String userrole;

    protected UserRoles() {
        //required no-argument constructor
    }

    public UserRoles(String email, String userrole) {
        this.email = email;
        this.userrole = userrole;
    }

    public Integer getId() {
        return id;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
