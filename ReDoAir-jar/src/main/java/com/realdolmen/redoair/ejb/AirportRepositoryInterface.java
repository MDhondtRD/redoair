package com.realdolmen.redoair.ejb;

import com.realdolmen.redoair.entities.Airport;

import javax.ejb.Remote;
import java.util.List;
import java.util.Set;

@Remote
public interface AirportRepositoryInterface {

    public List<Airport> getAllAirports();
    public List<Airport> getAllAirportsByCountry(String country);
    public Airport getAirportById(Integer id);
    public List<Airport> getAllAirportsByCity(String city);
    public Set<String> getAllCountries();
    public Set<String> getAllCitiesByCountry(String country);
    public Airport getAirportByName(String name);

}
