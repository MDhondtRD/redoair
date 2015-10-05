package com.realdolmen.air;

import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by JDOAX80 on 5/10/2015.
 */
@Named
@RequestScoped
public class LoginController {

    @Inject
    private UserRepository userRepository;

    private User user = new User();

    public String validateUser() {
        if(userRepository.validateUser(user)) {
            return "success";//redirect to home page
        }
        else {
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
