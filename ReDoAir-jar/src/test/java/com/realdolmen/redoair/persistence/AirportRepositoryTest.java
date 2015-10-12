package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.ejb.AirportRepository;
import com.realdolmen.redoair.entities.Airport;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by JDOAX80 on 12/10/2015.
 */
public class AirportRepositoryTest extends DataPersistenceTest {

    private AirportRepository airportRepository;

    @Before
    public void init() {
        airportRepository = new AirportRepository();
        airportRepository.setEntityManager(entityManager());
    }


    private int sizeOfAirportList = 5;

    @Test
    public void allAirportsCanBeRetrieved() {
        List<Airport> airportList = airportRepository.getAllAirports();
        assertEquals(sizeOfAirportList, airportList.size());
    }

    @Test
    public void allAirportsCanBeRetrievedByCountry() {
        List<Airport> airports = airportRepository.getAllAirportsByCountry("Belgium");
        assertEquals(18, airports.size());
    }

    @Test
    public void canRetrieveAirportById() {
        Airport airport = airportRepository.getAirportById(173);
        assertEquals(43.033056, airport.getLatitude(), 0.0005);
    }

    @Test
    public void canRetrieveAirportByCity() {
        List<Airport> airports = airportRepository.getAirportByCity("London");
        assertEquals(6, airports.size());
    }

    @Test
    public void canRetrieveAirportByName() {
        Airport airport = airportRepository.getAirportByName("Gatwick");
        assertNotNull(airport.getId());
    }
}
