package com.realdolmen.redoair.ejb;

import com.realdolmen.redoair.entities.Airport;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface AirportRepositoryInterface {

    public List<Airport> getAllAirports();
    public List<Airport> getAllAirportsByCountry(String country);
    public Airport getAirportById(Integer id);

}
