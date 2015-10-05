package com.realdolmen.redoair.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class User implements Serializable{

    /**
     * ID
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;



    /**
     * ATTRIBUTES
     */

    @Column(nullable = false, unique = true)
    private String username;

    private String name;

    private String surname;

    @Column(nullable = false)
    private String password;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType type;

    @Column(nullable = false, unique = true)
    private String email;


    /**
     * CONSTRUCTORS
     */

    public User() {
        // required no-argument constructor
    }

    public User(String username, String password, UserType type, String email) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.email = email;
    }

    public User(String username, String name, String surname, String password, Address address, UserType type, String email) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.address = address;
        this.type = type;
        this.email = email;
    }


    /**
     * GETTERS & SETTERS
     */

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }


    /**
     * METHODS
     */


}
