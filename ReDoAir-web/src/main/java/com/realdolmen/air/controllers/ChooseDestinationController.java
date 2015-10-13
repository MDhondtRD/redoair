package com.realdolmen.air.controllers;

import com.realdolmen.redoair.ejb.TripRepository;
import com.realdolmen.redoair.entities.Trip;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDOAX80 on 7/10/2015.
 */
@Named
@ConversationScoped
public class ChooseDestinationController implements Serializable {

    private String country;
    private List<Trip> trips = new ArrayList<>();
    private String tripName = "";

    @Inject
    private TripRepository tripRepository;

    public void retrieveDestinationsForCountry() {
        System.out.println("Calling retrieveDestinationsForCountry");
        System.out.println(country);
        trips = tripRepository.getAllFutureTripsByCountry(country);
        for(Trip trip: trips) {
            System.out.println("TRIP id: " + trip.getId());
            tripName = trip.getTravelAgency();
        }
/*        if(trips.size() > 0) {
            return "chooseTrip";
        }
        else {
            return "chooseTrip";
        }*/
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String startBooking() {
        System.out.println("Start booking");
        trips = tripRepository.getAllFutureTripsByCountry(country);
        return "../faces/global/chooseTrip";
    }

    public String endBooking() {
        return "addFlight.xhtml";
    }
}
