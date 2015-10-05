package com.realdolmen.redoair.ejb;

import com.realdolmen.redoair.entities.Flight;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Set;

@Stateless
@LocalBean
public class FlightRepository implements FlightRepositoryInterface {

    @Override
    public List<Flight> getAllFlights() {
        return null;
    }

    @Override
    public List<Flight> getAllFlightsByDestinationCity() {
        return null;
    }

    @Override
    public List<Flight> getAllFlightsByDepartureCity() {
        return null;
    }

    @Override
    public List<Flight> getAllFutureFlightsByDestinationCity() {
        return null;
    }

    @Override
    public List<Flight> getAllFutureFlightsByDepartureCity() {
        return null;
    }

    @Override
    public Set<String> getAllDestinationCities() {
        return null;
    }

    @Override
    public Set<String> getAllDepartureCities() {
        return null;
    }

    @Override
    public Set<String> getAllDestinationCitiesServedByFutureFlights() {
        return null;
    }

    @Override
    public Set<String> getAllDepartureCitiesServedByFurtureFlights() {
        return null;
    }

    @Override
    public Flight getFlightById() {
        return null;
    }

    @Override
    public List<Flight> getFlightsByCode() {
        return null;
    }

    @Override
    public void createFlight(Flight flight) {

    }

    @Override
    public void deleteFlight(Flight flight) {

    }
}
