package com.realdolmen.redoair.ejb;

import com.realdolmen.redoair.entities.Airport;

import javax.ejb.Remote;
import java.util.List;
import java.util.Set;

@Remote
public interface AirportRepositoryInterface {

    List<Airport> getAllAirports();

    List<Airport> getAllAirportsByCountry(String country);

    Airport getAirportById(Integer id);

    List<Airport> getAllAirportsByCity(String city);

    Airport getAirportByName(String airportName);

    Set<String> getAllCitiesByCountry(String country);

    Set<String> getAllCountries();
}
