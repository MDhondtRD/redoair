package com.realdolmen.air.controllers;

import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Created by JDOAX80 on 9/10/2015.
 */
@Named(value = "login")
public class Login {

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login";
    }
}
