package com.realdolmen.air.controllers;

import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.Address;
import com.realdolmen.redoair.entities.User;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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

    @ManagedProperty("#{param.name}")
    private String name;

    public String createUser() {
        setName(user.getName());
        user.setAddress(address);
        userRepository.createUser(user);
        return "/WEB-INF/successfulRegistration";
    }

    public String cancel() {
        return "home";
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
