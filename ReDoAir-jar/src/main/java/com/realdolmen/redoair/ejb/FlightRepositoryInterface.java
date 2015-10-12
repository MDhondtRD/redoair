package com.realdolmen.redoair.ejb;

import com.realdolmen.redoair.entities.Flight;

import javax.ejb.Remote;
import java.util.List;
import java.util.Set;

@Remote
public interface FlightRepositoryInterface {

    public List<Flight> getAllFlights();
    public List<Flight> getAllFutureFlights();
    public List<Flight> getAllFlightsByDestinationCity(String city);
    public List<Flight> getAllFlightsByDepartureCity(String city);
    public List<Flight> getAllFutureFlightsByDestinationCity(String city);
    public List<Flight> getAllFutureFlightsByDepartureCity(String city);
    public Set<String> getAllDestinationCities();
    public Set<String> getAllDepartureCities();
    public Set<String> getAllDestinationCitiesServedByFutureFlights();
    public Set<String> getAllDepartureCitiesServedByFutureFlights();
    public Flight getFlightById(Integer id);
    public List<Flight> getAllFlightsByCode(String code);
    public void createFlight(Flight flight);
    public void removeFlight(Flight flight);
    public Set<String> getAllFlightCodesFromPartner(String username);
    public void overrideEndUserPrice(Flight flight, double newPrice);

}
