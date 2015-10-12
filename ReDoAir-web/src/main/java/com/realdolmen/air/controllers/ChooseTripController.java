package com.realdolmen.air.controllers;

import com.realdolmen.redoair.ejb.TripRepository;
import com.realdolmen.redoair.entities.Trip;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDOAX80 on 12/10/2015.
 */
@Named
@RequestScoped
public class ChooseTripController implements Serializable{
    @Inject
    private TripRepository tripRepository;

    private List<Trip> trips = new ArrayList<>();

    @ManagedProperty("#{param.country}")
    private String destination;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    public void getTripsForChosenDestination(String destination) {
        tripRepository.getAllFutureTripsByCountry(destination);
        //tripRepository.getAllFutureTripsByDestination(destination);//Voor wanneer de mappoints op de map staan
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}
