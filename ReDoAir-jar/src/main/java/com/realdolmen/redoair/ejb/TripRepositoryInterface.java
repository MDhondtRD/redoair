package com.realdolmen.redoair.ejb;

import com.realdolmen.redoair.entities.Trip;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MDNAX30 on 6/10/2015.
 */
public interface TripRepositoryInterface {

    public List<Trip> getAllTrips();
    public Trip getTripById(Integer id);
    public List<Trip> getAllFutureTrips();
    public List<Trip> getAllTripsByDestination(String destination);
    public List<Trip> getAllFutureTripsByDestination(String destination);
    public List<Trip> getAllTripsWithoutReturnFlight();
    public List<Trip> getAllTripsWithoutReturnFlightByDestination(String destination);
    public List<Trip> getAllFutureTripsWithoutReturnFlight();
    public List<Trip> getAllFutureTripsWithoutReturnFlightByDestination(String destination);
    public List<Trip> getAllFlightOnlyTrips();
    public List<Trip> getAllFlightOnlyTripsByDestination(String destination);
    public List<Trip> getAllFutureFlightOnlyTrips();
    public List<Trip> getAllFutureFlightOnlyTripsByDestination(String destination);
    public List<Trip> getAllFlightOnlyTripsWithoutReturnFlight();
    public List<Trip> getAllFlightOnlyTripsWithoutReturnFlightByDestination(String destination);
    public List<Trip> getAllFutureFlightOnlyTripsWithoutReturnFlight();
    public List<Trip> getAllFutureFlightOnlyTripsWithoutReturnFlightByDestination(String destination);
    public List<Trip> getAllFutureTripsByCountry(String country);
    public void createTrip(Trip trip);
    public void removeTrip(Trip trip);

}
