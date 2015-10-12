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

    List<Airport> getAirportByCity(String city);

    Airport getAirportByName(String airportName);
}
