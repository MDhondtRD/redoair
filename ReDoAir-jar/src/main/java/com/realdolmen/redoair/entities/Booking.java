package com.realdolmen.redoair.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Booking implements Serializable{

    /**
     * ID
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * ATTRIBUTES
     */

    @ManyToMany
    private List<Trip> trips;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private double pricePaid;

    @Column(nullable = false)
    private int seatsBooked;


    /**
     * CONSTRUCTORS
     */

    public Booking() {
        // required no-argument constructor
    }

    public Booking(User user, int seatsBooked) {
        setUser(user);
        setSeatsBooked(seatsBooked);
        setPricePaid(0.00);
        setTrips(new ArrayList<Trip>());
    }

    public Booking(User user, int seatsBooked, List<Trip> trips) {
        this.user = user;
        setUser(user);
        setSeatsBooked(seatsBooked);
        setPricePaid(0.00);
        setTrips(trips);
    }


    /**
     * GETTERS & SETTERS
     */

    public Integer getId() {
        return id;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        if (!doAllFlightsHaveEnoughAvailableSeats(trips))
            throw new IllegalArgumentException("There are not enough seats available.");
        this.trips = trips;
        setPricePaid();
    }

    public void addTrip(Trip trip) {
        List<Trip> temp = new ArrayList<Trip>(); for (Trip t : getTrips()) temp.add(t); temp.add(trip);
        if (!doAllFlightsHaveEnoughAvailableSeats(getTrips()) || !doAllFlightsHaveEnoughAvailableSeats(temp))
            throw new IllegalArgumentException("There are not enough seats available.");
        if (trip == null)
            throw new IllegalArgumentException("A trip should not be null.");
        this.trips.add(trip);
        setPricePaid();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null)
            throw new IllegalArgumentException("A user should not be null.");
        if (user.getType() != UserType.CUSTOMER)
            throw new IllegalArgumentException("Only customers can book trips.");
        this.user = user;
    }

    public double getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(double pricePaid) {
        if (pricePaid < 0.0)
            throw new IllegalArgumentException("A booking should have a price >= 0");
        this.pricePaid = pricePaid;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(int seatsBooked) {
        if (seatsBooked < 1)
            throw new IllegalArgumentException("At least 1 seat need to be booked.");
        this.seatsBooked = seatsBooked;
    }


    /**
     * METHODS
     */

    public void setPricePaid() {
        double price = 0.0;
        for (Trip t : getTrips())
            price += t.getTripPrice();
        setPricePaid(price * getSeatsBooked());
    }

    private boolean doAllFlightsHaveEnoughAvailableSeats(List<Trip> trips) {
        boolean enoughSeats = true;
        for (Trip t : trips) {
            enoughSeats = enoughSeats && (t.getOutFlight().getAvailableSeats() >= getSeatsBooked());
            if (t.getReturnFlight() != null)
                enoughSeats = enoughSeats && (t.getReturnFlight().getAvailableSeats() >= getSeatsBooked());
        }
        return enoughSeats;
    }
}
