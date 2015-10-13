package com.realdolmen.air.controllers;

import com.realdolmen.redoair.ejb.TripRepository;
import com.realdolmen.redoair.entities.Trip;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ChooseTripController implements Serializable {

    /**
     * REPOSITORIES
     */

    @Inject
    private TripRepository tripRepository;

    /**
     * ATTRIBUTES
     */

    private List<Trip> trips;

    private List<Trip> filteredTrips;

    @ManagedProperty("#{param.country}")
    private String destination;

    private Trip selectedTrip;

    @PostConstruct
    public void init() {
        trips = tripRepository.getAllTrips();
    }


    /**
     * GETTERS & SETTERS
     */

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public List<Trip> getFilteredTrips() {
        return filteredTrips;
    }

    public void setFilteredTrips(List<Trip> filteredTrips) {
        this.filteredTrips = filteredTrips;
    }

    public Trip getSelectedTrip() {
        return selectedTrip;
    }

    public void setSelectedTrip(Trip selectedTrip) {
        this.selectedTrip = selectedTrip;
    }

    /**
     * METHODS
     */

    public void getTripsForChosenDestination(String destination) {
        tripRepository.getAllFutureTripsByCountry(destination);
        //tripRepository.getAllFutureTripsByDestination(destination);//Voor wanneer de mappoints op de map staan
    }
}
