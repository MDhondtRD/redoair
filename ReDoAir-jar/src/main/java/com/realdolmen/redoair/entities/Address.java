package com.realdolmen.redoair.entities;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    /**
     * ATTRIBUTES
     */

    private String street;

    private String city;

    private String zipcode;

    private String country;


    /**
     * CONSTRUCTORS
     */

    public Address() {
        // required no-argument constructor
    }

    public Address(String street, String city, String zipcode, String country) {
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        this.country = country;
    }



    /**
     * GETTERS & SETTERS
     */

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    /**
     * METHODS
     */


}
