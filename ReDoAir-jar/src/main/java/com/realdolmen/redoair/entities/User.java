package com.realdolmen.redoair.entities;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(nullable = false)
    String username;

    String name;

    String surname;

    @Column(nullable = false)
    String password;

    @Embedded
    Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserType type;

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

    public User(String username, String name, String surname, String password, Address address, UserType type) {

        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.address = address;
        this.type = type;
    }

    public User(String username, String password, UserType type) {

        this.username = username;
        this.password = password;
        this.type = type;
    }

    public User() {

    }
}
