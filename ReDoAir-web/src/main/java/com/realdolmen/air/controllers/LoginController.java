package com.realdolmen.air.controllers;

import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.User;

import javax.enterprise.context.RequestScoped;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.security.Principal;

/**
 * Created by JDOAX80 on 5/10/2015.
 */
@Named
@RequestScoped
public class LoginController implements Serializable {

    @Inject
    private UserRepository userRepository;

    @Inject
    private Principal principal;

    private User user = new User();

    public void login() {
        try {
            HttpServletRequest request = (HttpServletRequest) getContext().getRequest();
            request.login(user.getEmail(), user.getPassword());
        } catch (ServletException e) {
            //Login failed
            System.out.println("FAAAAIL");
        }
        finally {
            user.setPassword(null);
        }
    }

    public ExternalContext getContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login";
    }

    public boolean userIsACustomer(HttpServletRequest request) {
        return request.isUserInRole("customer");
    }

    public boolean userIsAPartner(HttpServletRequest request) {
        return request.isUserInRole("partner");
    }

    public boolean userIsAnEmployee(HttpServletRequest request) {
        return request.isUserInRole("employee");
    }

}
