package com.realdolmen.redoair.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private double price;

    private double lengthOfFlight;

    private double endUserPrice;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "discounts")
    private List<Discount> discounts = new ArrayList<>();

    @Transient
    private String airlineCompany;


    /**
     * CONSTRUCTORS
     */

    public Flight() {
        // required no-argument constructor
        // used by Hibernate
    }

    public Flight(String code, String departureCity, String destinationCity, LocalDateTime departure, int availableSeats, double price) {
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

    public void setDeparture(LocalDateTime departure) {
        if(departure != null) {
            this.departure = Date.from(departure.atZone(ZoneId.systemDefault()).toInstant());
        }
        else {
            this.departure = null; //Will throw a persistence exception if trying to persist a flight with a null as departure value.
        }
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        if (availableSeats < 1)
            throw new IllegalArgumentException("A flight must have at least 1 available seat");
        this.availableSeats = availableSeats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0.0)
            throw new IllegalArgumentException("A flight should have a price >= 0");
        this.price = BigDecimal.valueOf(price).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
        this.endUserPrice = this.price * 1.05;
    }

    public double getLengthOfFlight() {
        return lengthOfFlight;
    }

    public void setLengthOfFlight(double lengthOfFlight) {
        this.lengthOfFlight = lengthOfFlight;
    }

    public double getEndUserPrice() {
        return endUserPrice;
    }

    public void setEndUserPrice(double endUserPrice) {
        this.endUserPrice = endUserPrice;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public String getAirlineCompany() {
        return airlineCompany;
    }

    public void setAirlineCompany(String airlineCompany) {
        this.airlineCompany = airlineCompany;
    }

    /**
     * METHODS
     */

}
