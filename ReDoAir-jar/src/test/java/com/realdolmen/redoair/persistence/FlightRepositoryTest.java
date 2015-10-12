package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.ejb.FlightRepository;
import com.realdolmen.redoair.entities.Airport;
import com.realdolmen.redoair.entities.Flight;
import org.junit.After;
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
    private int numberOfLocations = 9;
    private Airport[] locations = new Airport[numberOfLocations];
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

    @After
    public void after() {
        for (Flight f : repo.getAllFlights())
            repo.removeFlight(f);
        repo.getEntityManager().flush();
        populated = false;
    }

    public void initialiseRepositories(){
        repo = new FlightRepository();
        repo.setEntityManager(entityManager());
        Airport a1 = new Airport("London", "abc", "UK", 0.0, 0.0);
        Airport a2 = new Airport("Paris", "abc", "UK", 0.0, 0.0);
        Airport a3 = new Airport("New York", "abc", "UK", 0.0, 0.0);
        Airport a4 = new Airport("Brussels", "abc", "UK", 0.0, 0.0);
        Airport a5 = new Airport("Madrid", "abc", "UK", 0.0, 0.0);
        Airport a6 = new Airport("Berlin", "abc", "UK", 0.0, 0.0);
        Airport a7 = new Airport("Rome", "abc", "UK", 0.0, 0.0);
        Airport a8 = new Airport("Lisbon", "abc", "UK", 0.0, 0.0);
        Airport a9 = new Airport("Athens", "abc", "UK", 0.0, 0.0);
        entityManager().persist(a1);
        entityManager().persist(a2);
        entityManager().persist(a3);
        entityManager().persist(a4);
        entityManager().persist(a5);
        entityManager().persist(a6);
        entityManager().persist(a7);
        entityManager().persist(a8);
        entityManager().persist(a9);
        entityManager().flush();
        locations[0] = a1;
        locations[1] = a2;
        locations[2] = a3;
        locations[3] = a4;
        locations[4] = a5;
        locations[5] = a6;
        locations[6] = a7;
        locations[7] = a8;
        locations[8] = a9;
    }

    public void populateDatabase(){
        for (int i = 1; i <= numberOfFlights; i++){
            LocalDateTime ldt = LocalDateTime.of(LocalDate.of(2015, 3 + (i / 30), 1 + (i % 30)), LocalTime.now());
            Flight f = new Flight(
                    "MD" + (5000 + i),
                    locations[i%numberOfLocations],
                    locations[(i+1)%numberOfLocations],
                    ldt,
                    50,
                    124.95);
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
        int startIndex = repo.getEntityManager().createQuery("SELECT MIN(f.id) FROM Flight f", Integer.class).getSingleResult();
        for (int i = startIndex; i <= numberOfFlights; i++)
            assertNotNull(repo.getFlightById(i));
        assertNull(repo.getFlightById(numberOfFlights + 1));
    }

    @Test
    public void flightsCanBeRetrievedByCode(){
        for (int i = 1; i <= numberOfFlights; i++)
            assertEquals(1, repo.getAllFlightsByCode("MD" + (5000 + i)).size());
        assertEquals(0, repo.getAllFlightsByCode("MD" + (5000 + numberOfFlights + 1)).size());
    }

    @Test
    public void flightsCanBeRetrievedByDestinationCity(){
        for (int i = 0; i < numberOfLocations; i++)
            assertEquals(factor, repo.getAllFlightsByDestinationCity(locations[i].getCity()).size());
    }

    @Test
    public void flightsCanBeRetrievedByDepartureCity(){
        for (int i = 0; i < numberOfLocations; i++)
            assertEquals(factor, repo.getAllFlightsByDepartureCity(locations[i].getCity()).size());
    }

    @Test
    public void findAllFutureFlights(){
        assertEquals(numberOfFutureFlights, repo.getAllFutureFlights().size());
    }

    @Test
    public void findOnlyFutureFlightsByDestinationCity(){
        for (int i = 0; i < numberOfLocations; i++) {
            assertNotNull(repo.getAllFutureFlightsByDestinationCity(locations[i].getCity()));
            assertTrue(repo.getAllFutureFlightsByDestinationCity(locations[i].getCity()).size() <= numberOfFutureFlights);
            assertTrue(repo.getAllFutureFlightsByDestinationCity(locations[i].getCity()).size() <= factor);
        }
    }

    @Test
    public void findOnlyFutureFlightsByDepartureCity(){
        for (int i = 0; i < numberOfLocations; i++) {
            assertNotNull(repo.getAllFutureFlightsByDestinationCity(locations[i].getCity()));
            assertTrue(repo.getAllFutureFlightsByDestinationCity(locations[i].getCity()).size() <= numberOfFutureFlights);
            assertTrue(repo.getAllFutureFlightsByDestinationCity(locations[i].getCity()).size() <= factor);
        }
    }

    @Test
    public void findAllDestinationCities(){
        assertEquals(numberOfLocations, repo.getAllDestinationCities().size());
        for (int i = 0; i < numberOfLocations; i++)
            assertTrue(repo.getAllDestinationCities().contains(locations[i].getCity()));
    }

    @Test
    public void findAllDepartureCities(){
        assertEquals(numberOfLocations, repo.getAllDepartureCities().size());
        for (int i = 0; i < numberOfLocations; i++)
            assertTrue(repo.getAllDepartureCities().contains(locations[i].getCity()));
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
