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
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
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

    @Inject
    LoggedInUserController loggedInUserController;

    private User user = new User();

    public String validateUserAndLogIn() {
        if(userRepository.validateUser(user)) {
            /*loggedInUserController.login(user.getEmail());*/
            return "success";//redirect to home page
        }
        else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Password",
                            "Please enter correct username and Password"));
            return "login";//redirect to login page + error message
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
