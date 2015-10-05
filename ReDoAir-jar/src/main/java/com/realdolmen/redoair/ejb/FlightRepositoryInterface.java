package com.realdolmen.redoair.ejb;

import com.realdolmen.redoair.entities.Flight;

import javax.ejb.Remote;
import java.util.List;
import java.util.Set;

@Remote
public interface FlightRepositoryInterface {

    public List<Flight> getAllFlights();
    public List<Flight> getAllFlightsByDestinationCity();
    public List<Flight> getAllFlightsByDepartureCity();
    public List<Flight> getAllFutureFlightsByDestinationCity();
    public List<Flight> getAllFutureFlightsByDepartureCity();
    public Set<String> getAllDestinationCities();
    public Set<String> getAllDepartureCities();
    public Set<String> getAllDestinationCitiesServedByFutureFlights();
    public Set<String> getAllDepartureCitiesServedByFurtureFlights();
    public Flight getFlightById();
    public List<Flight> getFlightsByCode();
    public void createFlight(Flight flight);
    public void deleteFlight(Flight flight);

}
