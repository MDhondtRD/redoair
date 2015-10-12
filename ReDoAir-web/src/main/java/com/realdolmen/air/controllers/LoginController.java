package com.realdolmen.air.controllers;

import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.User;
import de.rtner.misc.BinTools;
import de.rtner.security.auth.spi.PBKDF2Engine;
import de.rtner.security.auth.spi.PBKDF2Parameters;
import de.rtner.security.auth.spi.SimplePBKDF2;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.security.SecureRandom;

/**
 * Created by JDOAX80 on 5/10/2015.
 */
@Named
@RequestScoped
public class LoginController implements Serializable {

    @Inject
    private UserRepository userRepository;

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

}
