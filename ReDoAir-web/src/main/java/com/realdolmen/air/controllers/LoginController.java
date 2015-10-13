package com.realdolmen.air.controllers;

import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.User;

import javax.annotation.PostConstruct;
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
    private boolean loggedIn = false;
    private boolean loggedOut = true;

    @PostConstruct
    public void checkIfLoggedInOrLoggedOut() {
        if(!principal.getName().equals("anonymous")) {
            loggedIn = true;
            loggedOut = false;
        }
        else {
            loggedIn = false;
            loggedOut = true;
        }
    }

    public String login() {
        String returnPath = null;
        try {
            HttpServletRequest request = (HttpServletRequest) getContext().getRequest();
            request.login(user.getEmail(), user.getPassword());
            returnPath = "home.xhtml";
        } catch (ServletException e) {
            //Login failed
            System.out.println("FAAAAIL");
            returnPath = "../faces/global/loginInNavBar.xhtml";
        }
        finally {
            user.setPassword(null);
            return returnPath;
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "loginInNavBar.xhtml";
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

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isLoggedOut() {
        return loggedOut;
    }

    public void setLoggedOut(boolean loggedOut) {
        this.loggedOut = loggedOut;
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
