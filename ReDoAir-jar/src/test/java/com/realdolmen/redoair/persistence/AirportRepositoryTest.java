package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.ejb.AirportRepository;
import com.realdolmen.redoair.entities.Airport;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

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


    private int sizeOfAirportList = 8107;

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
        List<Airport> airports = airportRepository.getAllAirportsByCity("London");
        int i = 0;
        for(Airport airport : airports) {
            System.out.println(airport.getName());
            i++;
        }
        System.out.println("COUNT: " + i);
        assertEquals(6, airports.size());
    }

    @Test
    public void canRetrieveAirportByName() {
        Airport airport = airportRepository.getAirportByName("Gatwick");
        assertNotNull(airport.getId());
    }

    @Test
    public void canRetrieveAllCitiesByCountry() {
        Set<String> citiesByCountry = airportRepository.getAllCitiesByCountry("Belgium");
        assertEquals(28, citiesByCountry.size());//Filters all entries that have the same city
    }

    @Test
    public void canRetrieveAllCountries() {
        Set<String> countries = airportRepository.getAllCountries();
        int i = 0;
        for(String country : countries) {
            System.out.println(country);
            i++;
        }
        System.out.println("COUNT: " + i);
        assertEquals(50, countries.size());
    }
}
