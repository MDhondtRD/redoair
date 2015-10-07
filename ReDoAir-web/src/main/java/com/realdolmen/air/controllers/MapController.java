package com.realdolmen.air.controllers;

import com.realdolmen.redoair.ejb.TripRepository;
import com.realdolmen.redoair.entities.Trip;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDOAX80 on 7/10/2015.
 */
@Named
@RequestScoped
public class MapController {
    private String countryCode;
    private List<Trip> trips = new ArrayList<>();

    @Inject
    private TripRepository tripRepository;

    public void retrieveDestinationsForCountry() {
        trips = tripRepository.getAllFutureTripsByCountryCode(countryCode);
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}
