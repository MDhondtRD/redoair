package com.realdolmen.redoair.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by JDOAX80 on 7/10/2015.
 */
@Entity
public class Airport implements Serializable {

    /**
     * ID
     */

    @Id
    private Integer id;


    /**
     * ATTRIBUTES
     */

    private String city;

    private String name;

    private String country;

    @Column(nullable=false)
    private double longitude;

    @Column(nullable=false)
    private double latitude;


    /**
     * CONSTRUCTORS
     */

    protected Airport() {
        // required no-argument constructor
        // used by Hibernate
    }

    public Airport(Integer id, String city, String name, String country, double longitude, double latitude) {
        this.id = id;
        this.city = city;
        this.name = name;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    /**
     * GETTERS
     */

    public Integer getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}