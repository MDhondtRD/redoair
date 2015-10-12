package com.realdolmen.redoair.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
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

    @ManyToOne(optional = false)
    private Airport departureAirport;

    @ManyToOne(optional = false)
    private Airport destinationAirport;

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


    /**
     * CONSTRUCTORS
     */

    public Flight() {
        // required no-argument constructor
        // used by Hibernate
    }

    public Flight(String code, Airport departureAirport, Airport destinationAirport, LocalDateTime departure, int availableSeats, double price) {
        setCode(code);
        setDepartureAirport(departureAirport);
        setDestinationAirport(destinationAirport);
        setDeparture(departure);
        setAvailableSeats(availableSeats);
        setPrice(price);
    }

    public Flight(String code, Airport departureAirport, Airport destinationAirport, LocalDateTime departure, int availableSeats, double price, List<Discount> discounts) {
        setCode(code);
        setDepartureAirport(departureAirport);
        setDestinationAirport(destinationAirport);
        setDeparture(departure);
        setAvailableSeats(availableSeats);
        setPrice(price);
        setDiscounts(discounts);
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
        return departureAirport.getCity();
    }

    public String getDepartureCountry() {
        return departureAirport.getCountry();
    }

    public String getDestinationCity() {
        return destinationAirport.getCity();
    }

    public String getDestinationCountry() {
        return destinationAirport.getCountry();
    }

    public Airport getDepartureAirport(){
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport){
        this.departureAirport = departureAirport;
    }

    public Airport getDestinationAirport(){
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport){
        if (destinationAirport == departureAirport)
            throw new IllegalArgumentException("Destination airport should not be the same as the departure airport.");
        this.destinationAirport = destinationAirport;
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
        this.price = BigDecimal.valueOf(price).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        setEndUserPrice(this.price*1.05);
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
        if (endUserPrice < 0.0)
            throw new IllegalArgumentException("The user price should not be negative.");
        this.endUserPrice = BigDecimal.valueOf(endUserPrice).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    /**
     * METHODS
     */

}
