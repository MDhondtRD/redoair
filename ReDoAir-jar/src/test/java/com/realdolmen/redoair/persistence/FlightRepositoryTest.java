package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.ejb.FlightRepository;
import com.realdolmen.redoair.entities.Flight;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class FlightRepositoryTest extends DataPersistenceTest {

    private FlightRepository repo;
    private static boolean populated = false;
    private String[] locations = {"London", "Paris", "New York", "Brussels", "Madrid", "Berlin", "Rome", "Lisbon", "Athens"};
    private int numberOfLocations = 9;
    private int factor = 20;
    private int numberOfFlights = factor * numberOfLocations;
    private int numberOfFutureFlights = 0;

    @Before
    public void before() {
        initialiseRepositories();
        if (!populated){
            populateDatabase();
            populated = true;
        }
    }

    public void initialiseRepositories(){
        repo = new FlightRepository();
        repo.setEntityManager(entityManager());
    }

    public void populateDatabase(){
        for (int i = 1; i <= numberOfFlights; i++){
            LocalDateTime ldt = LocalDateTime.of(LocalDate.of(2015, 3 + (i / 30), 1 + (i % 30)), LocalTime.now());
            Flight f = new Flight(
                    "MD" + (1000 + i),
                    locations[i%numberOfLocations],
                    locations[(i+1)%numberOfLocations],
                    ldt,
                    50,
                    124.95,
                    "BEL");
            repo.createFlight(f);
            if (ldt.isAfter(LocalDateTime.now()))
                numberOfFutureFlights++;
        }
    }

    @Test
    public void flightsCanBeRetrieved(){
        assertEquals(numberOfFlights, repo.getAllFlights().size());
    }

    @Test
    public void flightsCanBeRetrievedById(){
        for (int i = 1; i <= numberOfFlights; i++)
            assertNotNull(repo.getFlightById(i));
        assertNull(repo.getFlightById(numberOfFlights + 1));
    }

    @Test
    public void flightsCanBeRetrievedByCode(){
        for (int i = 1; i <= numberOfFlights; i++)
            assertEquals(1, repo.getAllFlightsByCode("MD" + (1000 + i)).size());
        assertEquals(0, repo.getAllFlightsByCode("MD" + (1000 + numberOfFlights + 1)).size());
    }

    @Test
    public void flightsCanBeRetrievedByDestinationCity(){
        for (int i = 0; i < numberOfLocations; i++)
            assertEquals(factor, repo.getAllFlightsByDestinationCity(locations[i]).size());
    }

    @Test
    public void flightsCanBeRetrievedByDepartureCity(){
        for (int i = 0; i < numberOfLocations; i++)
            assertEquals(factor, repo.getAllFlightsByDepartureCity(locations[i]).size());
    }

    @Test
    public void findAllFutureFlights(){
        assertEquals(numberOfFutureFlights, repo.getAllFutureFlights().size());
    }

    @Test
    public void findOnlyFutureFlightsByDestinationCity(){
        for (int i = 0; i < numberOfLocations; i++) {
            assertNotNull(repo.getAllFutureFlightsByDestinationCity(locations[i]));
            assertTrue(repo.getAllFutureFlightsByDestinationCity(locations[i]).size() <= numberOfFutureFlights);
            assertTrue(repo.getAllFutureFlightsByDestinationCity(locations[i]).size() <= factor);
        }
    }

    @Test
    public void findOnlyFutureFlightsByDepartureCity(){
        for (int i = 0; i < numberOfLocations; i++) {
            assertNotNull(repo.getAllFutureFlightsByDestinationCity(locations[i]));
            assertTrue(repo.getAllFutureFlightsByDestinationCity(locations[i]).size() <= numberOfFutureFlights);
            assertTrue(repo.getAllFutureFlightsByDestinationCity(locations[i]).size() <= factor);
        }
    }

    @Test
    public void findAllDestinationCities(){
        assertEquals(numberOfLocations, repo.getAllDestinationCities().size());
        for (int i = 0; i < numberOfLocations; i++)
            assertTrue(repo.getAllDestinationCities().contains(locations[i]));
    }

    @Test
    public void findAllDepartureCities(){
        assertEquals(numberOfLocations, repo.getAllDepartureCities().size());
        for (int i = 0; i < numberOfLocations; i++)
            assertTrue(repo.getAllDepartureCities().contains(locations[i]));
    }

    @Test
    public void findAllFutureDestinationCities(){
        assertNotNull(repo.getAllDestinationCitiesServedByFutureFlights());
        assertTrue(repo.getAllDestinationCitiesServedByFutureFlights().size() <= numberOfFutureFlights);
        assertTrue(repo.getAllDestinationCitiesServedByFutureFlights().size() <= repo.getAllDestinationCities().size());
    }

    @Test
    public void findAllFutureDepartureCities(){
        assertNotNull(repo.getAllDepartureCitiesServedByFutureFlights());
        assertTrue(repo.getAllDepartureCitiesServedByFutureFlights().size() <= numberOfFutureFlights);
        assertTrue(repo.getAllDepartureCitiesServedByFutureFlights().size() <= repo.getAllDepartureCities().size());
    }

    @Test
    public void findAllFlightCodesFromAirlinePartnerWithName(){
        assertNotNull(repo.getAllFlightCodesFromPartner("Maarten Dhondt airlines"));
        assertEquals(numberOfFlights, repo.getAllFlightCodesFromPartner("Maarten D flights").size());
    }

}
