package com.realdolmen.air.controllers;

import com.realdolmen.redoair.ejb.TripRepository;
import com.realdolmen.redoair.entities.Trip;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.event.data.FilterEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ConversationScoped
public class ChooseTripController implements Serializable {

    /**
     * REPOSITORIES
     */

    @Inject
    private TripRepository tripRepository;

    @Inject
    private ChooseDestinationController  chooseDestinationController;


    /**
     * ATTRIBUTES
     */

    private List<Trip> trips;

    private List<Trip> filteredTrips;

    private String destination;

    private Trip selectedTrip;

    @PostConstruct
    public void init() {
        trips = tripRepository.getAllFutureTrips();
        destination = chooseDestinationController.getCountry();
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

    public void onRowSelect(SelectEvent event) {
        selectedTrip = (Trip) event.getObject();
        System.out.println(selectedTrip.getOutFlight().getDestinationAirport().getCity() +" COUNTRY: " + selectedTrip.getOutFlight().getDestinationAirport().getCountry());
        //return "tripOverview";
    }

    /**
     * METHODS
     */

    public void getTripsForChosenDestination(String destination) {
        tripRepository.getAllFutureTripsByCountry(destination);
        //tripRepository.getAllFutureTripsByDestination(destination);//Voor wanneer de mappoints op de map staan
    }

}
