package com.realdolmen.redoair.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Flight implements Serializable {

    /**
     * ID
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    /**
     * ATTRIBUTES
     */

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String departureCity;

    @Column(nullable = false)
    private String destinationCity;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date departure;

    @Column(nullable = false)
    private int availableSeats;

    @Column(nullable = false)
    private float price;


    /**
     * CONSTRUCTORS
     */

    public Flight() {
        // required no-argument constructor
    }

    public Flight(String code, String departureCity, String destinationCity, Date departure, int availableSeats, float price) {
        this.setCode(code);
        this.setDepartureCity(departureCity);
        this.setDestinationCity(destinationCity);
        this.setDeparture(departure);
        this.setAvailableSeats(availableSeats);
        this.setPrice(price);
    }


    /**
     * GETTERS & SETTERS
     */

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    /**
     * A flight code must have 2 capital letters followed by 4 digits
     */
    public void setCode(String code) {
        if (code.length() != 6)
            throw new IllegalArgumentException("A flight code must be 6 characters.");
        if (!(Character.isUpperCase(code.charAt(0)) && Character.isUpperCase(code.charAt(1))))
            throw new IllegalArgumentException("A flight code must start with 2 uppercase letters.");
        if (!(Character.isDigit(code.charAt(2)) && Character.isDigit(code.charAt(3)) && Character.isDigit(code.charAt(4)) && Character.isDigit(code.charAt(5))))
            throw new IllegalArgumentException("A flight code must start with 2 uppercase letters followed by 4 digits");
        this.code = code;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        if (availableSeats < 1)
            throw new IllegalArgumentException("A flight must have at least 1 available seat");
        this.availableSeats = availableSeats;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        if (price < 0.0)
            throw new IllegalArgumentException("A flight should have a price >= 0");
        this.price = BigDecimal.valueOf(price).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
    }


    /**
     * METHODS
     */

}
