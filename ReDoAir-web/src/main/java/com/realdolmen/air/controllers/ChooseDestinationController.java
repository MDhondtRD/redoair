package com.realdolmen.air.controllers;

import com.realdolmen.redoair.ejb.AirportRepository;
import com.realdolmen.redoair.ejb.TripRepository;
import com.realdolmen.redoair.entities.Trip;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by JDOAX80 on 7/10/2015.
 */
@Named
@ConversationScoped
public class ChooseDestinationController implements Serializable {

    private String country;
    private String destination;
    private List<Trip> tripsForCountry = new ArrayList<>();
    private List<Trip> tripsForDestination = new ArrayList<>();
    private Set<String> destinations;

    @Inject
    private TripRepository tripRepository;

    @Inject
    private AirportRepository airportRepository;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Trip> getTripsForCountry() {
        return tripsForCountry;
    }

    public void setTripsForCountry(List<Trip> tripsForCountry) {
        this.tripsForCountry = tripsForCountry;
    }

    public List<Trip> getTripsForDestination() {
        return tripsForDestination;
    }

    public void setTripsForDestination(List<Trip> tripsForDestination) {
        this.tripsForDestination = tripsForDestination;
    }


    public Set<String> getDestinations() {
        return destinations;
    }

    public void setDestinations(Set<String> destinations) {
        this.destinations = destinations;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

//    public String startBooking() {
//        System.out.println("Start booking");
//        retrieveTripsForCountry();
//        return "chooseTrip";
//    }

    public String goToChooseTrip() {
        System.out.println("Navigating to chooseTripView");
        retrieveTripsForCountry();
        return "chooseTrip";
    }

    public void retrieveTripsForCountry() {
        tripsForCountry = tripRepository.getAllFutureTripsByCountry(country);
    }

    public void retrieveTripsForDestination() {
        tripsForDestination = tripRepository.getAllFutureTripsByDestination(destination);
    }

    public void retrieveDestinationsForCountry() {
        destinations  = airportRepository.getAllCitiesByCountry(country);

    }


}
