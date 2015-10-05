package com.realdolmen.air.controllers;

import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.Address;
import com.realdolmen.redoair.entities.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by JDOAX80 on 5/10/2015.
 */
@Named
@RequestScoped
public class SignUpController {

    @Inject
    private UserRepository userRepository;

    private User user = new User();
    private Address address = new Address();

    public String createUser() {
        user.setAddress(address);
        userRepository.createUser(user);
        return "login";//redirects to the loginPage
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
