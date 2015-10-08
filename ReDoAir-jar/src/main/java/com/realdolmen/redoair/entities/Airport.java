package com.realdolmen.redoair.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by JDOAX80 on 7/10/2015.
 */
@Entity
public class Airport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * ATTRIBUTES
     */

    private String destinationCity;
    private String nameOfAirport;

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

    public Airport(String destinationCity, double longitude, double latitude) {
        this.destinationCity = destinationCity;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Integer getId() {
        return id;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getNameOfAirport() {
        return nameOfAirport;
    }

    public void setNameOfAirport(String nameOfAirport) {
        this.nameOfAirport = nameOfAirport;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
