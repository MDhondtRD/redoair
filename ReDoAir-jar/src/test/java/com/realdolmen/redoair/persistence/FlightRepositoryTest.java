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

    @Before
    public void before() {
        repo = new FlightRepository();
        repo.setEntityManager(entityManager());
    }

    @Test(expected = PersistenceException.class)
    public void flightsShouldHaveANonNullDepartureCity(){
        Flight f = new Flight("AA0000", null, "New York", LocalDateTime.of(LocalDate.of(1985,11,12), LocalTime.now()), 15, 399.95, "BE");
        repo.createFlight(f); repo.getEntityManager().flush();
    }

    @Test(expected = PersistenceException.class)
    public void flightsShouldHaveANonNullDestinationCity(){
        Flight f = new Flight("AA0000", "London", null, LocalDateTime.of(LocalDate.of(1985,11,12),LocalTime.now()), 15, 399.95, "BE");
        repo.createFlight(f); repo.getEntityManager().flush();
    }

    @Test(expected = PersistenceException.class)
    public void flightsShouldHaveANonNullDepartureDateTime(){
        Flight f = new Flight("AA0000", "London", "New York", null, 15, 399.95, "BE");
        repo.createFlight(f); repo.getEntityManager().flush();
    }

    @Test
    public void flightsCanBeRetrieved(){
        assertEquals(1, repo.getAllFlights().size());
        Flight f = new Flight("AA0000", "London", "New York", LocalDateTime.of(LocalDate.of(1985,11,12),LocalTime.now()), 15, 399.999, "BE");
        repo.createFlight(f); repo.getEntityManager().flush();
        assertEquals(2, repo.getAllFlights().size());
    }

    @Test
    public void flightsCanBeRetrievedById(){
        Flight f = new Flight("AA0000", "London", "New York", LocalDateTime.of(LocalDate.of(1985,11,12),LocalTime.now()), 15, 399.999, "BE");
        repo.createFlight(f); repo.getEntityManager().flush();
        assertEquals("MD1991", repo.getFlightById(1).getCode());
        assertEquals(f.getCode(), repo.getFlightById(f.getId()).getCode());
    }

    @Test
    public void flightsCanBeRetrievedByCode(){
        assertEquals(1, repo.getAllFlightsByCode("MD1991").size());
        repo.createFlight(new Flight("MD1991", "London", "New York", LocalDateTime.of(LocalDate.of(1985,11,12),LocalTime.now()), 15, 15.15, "BE"));
        repo.getEntityManager().flush();
        assertEquals(2, repo.getAllFlightsByCode("MD1991").size());
    }

    @Test
    public void flightsCanBeRetrievedByDestinationCity(){
        Flight f1 = new Flight("AA0000", "London", "New York", LocalDateTime.of(LocalDate.of(1985,11,12),LocalTime.now()), 15, 399.999, "BE");
        Flight f2 = new Flight("AA0001", "New York", "London", LocalDateTime.of(LocalDate.of(1985,11,12),LocalTime.now()), 15, 399.999, "BE");
        repo.createFlight(f1); repo.createFlight(f2); repo.getEntityManager().flush();
        assertEquals(2, repo.getAllFlightsByDestinationCity("New York").size());
        assertEquals(1, repo.getAllFlightsByDestinationCity("London").size());
        assertEquals(0, repo.getAllFlightsByDestinationCity("Brussels").size());
    }

    @Test
    public void flightsCanBeRetrievedByDepartureCity(){
        Flight f1 = new Flight("AA0000", "London", "New York", LocalDateTime.of(LocalDate.of(1985,11,12),LocalTime.now()), 15, 399.999, "BE");
        Flight f2 = new Flight("AA0001", "New York", "London", LocalDateTime.of(LocalDate.of(1985,11,12),LocalTime.now()), 15, 399.999, "BE");
        repo.createFlight(f1); repo.createFlight(f2); repo.getEntityManager().flush();
        assertEquals(1, repo.getAllFlightsByDepartureCity("New York").size());
        assertEquals(1, repo.getAllFlightsByDepartureCity("London").size());
        assertEquals(1, repo.getAllFlightsByDepartureCity("Brussels").size());
    }

    @Test
    public void findAllFutureFlights(){
        Flight f1 = new Flight("AA0001", "London", "New York", LocalDate.of(2015, 10, 01).atTime(14,30), 15, 399.999, "BE"); // 01-10-2015 14:30:00
        Flight f2 = new Flight("AA0002", "London", "New York", LocalDate.of(2015, 10, 02).atTime(14,30), 15, 399.999, "BE"); // 02-10-2015 14:30:00
        Flight f3 = new Flight("AA0003", "London", "New York", LocalDate.of(2015, 10, 03).atTime(14,30), 15, 399.999, "BE"); // 03-10-2015 14:30:00
        Flight f4 = new Flight("AA0004", "London", "New York", LocalDate.of(2015, 12, 01).atTime(14,30), 15, 399.999, "BE"); // 01-12-2015 14:30:00
        Flight f5 = new Flight("AA0005", "London", "New York", LocalDate.of(2015, 12, 02).atTime(14,30), 15, 399.999, "BE"); // 02-12-2015 14:30:00
        repo.createFlight(f1); repo.createFlight(f2); repo.createFlight(f3); repo.createFlight(f4); repo.createFlight(f5);
        repo.getEntityManager().flush();
        assertEquals(6, repo.getAllFlights().size());
        assertEquals(2, repo.getAllFutureFlights().size());
    }

    @Test
    public void findOnlyFutureFlightsByDestinationCity(){
        Flight f1 = new Flight("AA0001", "London", "New York", LocalDate.of(2015, 10, 01).atTime(14,30), 15, 399.999, "BE"); // 01-10-2015 14:30:00
        Flight f2 = new Flight("AA0002", "London", "New York", LocalDate.of(2015, 10, 02).atTime(14,30), 15, 399.999, "BE"); // 02-10-2015 14:30:00
        Flight f3 = new Flight("AA0003", "London", "New York", LocalDate.of(2015, 10, 03).atTime(14,30), 15, 399.999, "BE"); // 03-10-2015 14:30:00
        Flight f4 = new Flight("AA0004", "London", "New York", LocalDate.of(2015, 12, 01).atTime(14,30), 15, 399.999, "BE"); // 01-12-2015 14:30:00
        Flight f5 = new Flight("AA0005", "London", "New York", LocalDate.of(2015, 12, 02).atTime(14,30), 15, 399.999, "BE"); // 02-12-2015 14:30:00
        repo.createFlight(f1); repo.createFlight(f2); repo.createFlight(f3); repo.createFlight(f4); repo.createFlight(f5);
        repo.getEntityManager().flush();
        assertEquals(6, repo.getAllFlightsByDestinationCity("New York").size());
        assertEquals(2, repo.getAllFutureFlightsByDestinationCity("New York").size());
        assertEquals(0, repo.getAllFlightsByDestinationCity("London").size());
        assertEquals(0, repo.getAllFutureFlightsByDestinationCity("London").size());
        assertEquals(0, repo.getAllFlightsByDestinationCity("Brussels").size());
        assertEquals(0, repo.getAllFutureFlightsByDestinationCity("Brussels").size());
    }

    @Test
    public void findOnlyFutureFlightsByDepartureCity(){
        Flight f1 = new Flight("AA0001", "London", "New York", LocalDate.of(2015, 10, 01).atTime(14,30), 15, 399.999, "BE"); // 01-10-2015 14:30:00
        Flight f2 = new Flight("AA0002", "London", "New York", LocalDate.of(2015, 10, 02).atTime(14,30), 15, 399.999, "BE"); // 02-10-2015 14:30:00
        Flight f3 = new Flight("AA0003", "London", "New York", LocalDate.of(2015, 10, 03).atTime(14,30), 15, 399.999, "BE"); // 03-10-2015 14:30:00
        Flight f4 = new Flight("AA0004", "London", "New York", LocalDate.of(2015, 12, 01).atTime(14,30), 15, 399.999, "BE"); // 01-12-2015 14:30:00
        Flight f5 = new Flight("AA0005", "London", "New York", LocalDate.of(2015, 12, 02).atTime(14,30), 15, 399.999, "BE"); // 02-12-2015 14:30:00
        repo.createFlight(f1); repo.createFlight(f2); repo.createFlight(f3); repo.createFlight(f4); repo.createFlight(f5);
        repo.getEntityManager().flush();
        assertEquals(6, repo.getAllFlights().size());
        assertEquals(0, repo.getAllFlightsByDepartureCity("New York").size());
        assertEquals(0, repo.getAllFutureFlightsByDepartureCity("New York").size());
        assertEquals(5, repo.getAllFlightsByDepartureCity("London").size());
        assertEquals(2, repo.getAllFutureFlightsByDepartureCity("London").size());
        assertEquals(1, repo.getAllFlightsByDepartureCity("Brussels").size());
        assertEquals(0, repo.getAllFutureFlightsByDepartureCity("Brussels").size());
    }

    @Test
    public void findAllDestinationCities(){
        Flight f1 = new Flight("AA0001", "London", "New York", LocalDate.of(2015, 10, 01).atTime(14,30), 15, 399.999, "BE"); // 01-10-2015 14:30:00
        Flight f2 = new Flight("AA0002", "London", "New York", LocalDate.of(2015, 10, 02).atTime(14,30), 15, 399.999, "BE"); // 02-10-2015 14:30:00
        Flight f3 = new Flight("AA0003", "London", "New York", LocalDate.of(2015, 10, 03).atTime(14,30), 15, 399.999, "BE"); // 03-10-2015 14:30:00
        Flight f4 = new Flight("AA0004", "London", "New York", LocalDate.of(2015, 12, 01).atTime(14,30), 15, 399.999, "BE"); // 01-12-2015 14:30:00
        Flight f5 = new Flight("AA0005", "London", "New York", LocalDate.of(2015, 12, 02).atTime(14,30), 15, 399.999, "BE"); // 02-12-2015 14:30:00
        repo.createFlight(f1); repo.createFlight(f2); repo.createFlight(f3); repo.createFlight(f4); repo.createFlight(f5);
        repo.getEntityManager().flush();
        assertEquals(1, repo.getAllDestinationCities().size());
        assertTrue(repo.getAllDestinationCities().contains("New York"));
        assertFalse(repo.getAllDestinationCities().contains("London"));
        assertFalse(repo.getAllDestinationCities().contains("Brussels"));
    }

    @Test
    public void findAllDepartureCities(){
        Flight f1 = new Flight("AA0001", "London", "New York", LocalDate.of(2015, 10, 01).atTime(14,30), 15, 399.999, "BE"); // 01-10-2015 14:30:00
        Flight f2 = new Flight("AA0002", "London", "New York", LocalDate.of(2015, 10, 02).atTime(14,30), 15, 399.999, "BE"); // 02-10-2015 14:30:00
        Flight f3 = new Flight("AA0003", "London", "New York", LocalDate.of(2015, 10, 03).atTime(14,30), 15, 399.999, "BE"); // 03-10-2015 14:30:00
        Flight f4 = new Flight("AA0004", "London", "New York", LocalDate.of(2015, 12, 01).atTime(14,30), 15, 399.999, "BE"); // 01-12-2015 14:30:00
        Flight f5 = new Flight("AA0005", "London", "New York", LocalDate.of(2015, 12, 02).atTime(14,30), 15, 399.999, "BE"); // 02-12-2015 14:30:00
        repo.createFlight(f1); repo.createFlight(f2); repo.createFlight(f3); repo.createFlight(f4); repo.createFlight(f5);
        repo.getEntityManager().flush();
        assertEquals(2, repo.getAllDepartureCities().size());
        assertFalse(repo.getAllDepartureCities().contains("New York"));
        assertTrue(repo.getAllDepartureCities().contains("London"));
        assertTrue(repo.getAllDepartureCities().contains("Brussels"));
    }

    @Test
    public void findAllFutureDestinationCities(){
        Flight f1 = new Flight("AA0001", "London", "New York", LocalDate.of(2015, 10, 01).atTime(14,30), 15, 399.999, "BE"); // 01-10-2015 14:30:00
        Flight f2 = new Flight("AA0002", "London", "Detroit", LocalDate.of(2015, 10, 02).atTime(14,30), 15, 399.999, "BE"); // 02-10-2015 14:30:00
        Flight f3 = new Flight("AA0003", "London", "Chicago", LocalDate.of(2015, 10, 03).atTime(14,30), 15, 399.999, "BE"); // 03-10-2015 14:30:00
        Flight f4 = new Flight("AA0004", "London", "Boston", LocalDate.of(2015, 12, 01).atTime(14,30), 15, 399.999, "BE"); // 01-12-2015 14:30:00
        Flight f5 = new Flight("AA0005", "London", "Philadelphia", LocalDate.of(2015, 12, 02).atTime(14,30), 15, 399.999, "BE"); // 02-12-2015 14:30:00
        repo.createFlight(f1); repo.createFlight(f2); repo.createFlight(f3); repo.createFlight(f4); repo.createFlight(f5);
        repo.getEntityManager().flush();
        assertEquals(6, repo.getAllFlights().size());
        assertEquals(2, repo.getAllFutureFlights().size());
        assertEquals(5, repo.getAllDestinationCities().size());
        assertTrue(repo.getAllDestinationCitiesServedByFutureFlights().contains("Boston"));
        assertTrue(repo.getAllDestinationCitiesServedByFutureFlights().contains("Philadelphia"));
        assertFalse(repo.getAllDestinationCitiesServedByFutureFlights().contains("New York"));
        assertFalse(repo.getAllDestinationCitiesServedByFutureFlights().contains("Detroit"));
        assertFalse(repo.getAllDestinationCitiesServedByFutureFlights().contains("Chicago"));
        assertFalse(repo.getAllDestinationCitiesServedByFutureFlights().contains("Brussels"));
        assertFalse(repo.getAllDestinationCitiesServedByFutureFlights().contains("London"));
    }

    @Test
    public void findAllFutureDepartureCities(){
        Flight f1 = new Flight("AA0001", "New York", "London", LocalDate.of(2015, 10, 01).atTime(14,30), 15, 399.999, "BE"); // 01-10-2015 14:30:00
        Flight f2 = new Flight("AA0002", "Detroit", "London", LocalDate.of(2015, 10, 02).atTime(14,30), 15, 399.999, "BE"); // 02-10-2015 14:30:00
        Flight f3 = new Flight("AA0003", "Chicago", "London", LocalDate.of(2015, 10, 03).atTime(14,30), 15, 399.999, "BE"); // 03-10-2015 14:30:00
        Flight f4 = new Flight("AA0004", "Boston", "London", LocalDate.of(2015, 12, 01).atTime(14,30), 15, 399.999, "BE"); // 01-12-2015 14:30:00
        Flight f5 = new Flight("AA0005", "Philadelphia", "London", LocalDate.of(2015, 12, 02).atTime(14,30), 15, 399.999, "BE"); // 02-12-2015 14:30:00
        repo.createFlight(f1); repo.createFlight(f2); repo.createFlight(f3); repo.createFlight(f4); repo.createFlight(f5);
        repo.getEntityManager().flush();
        assertEquals(6, repo.getAllFlights().size());
        assertEquals(2, repo.getAllFutureFlights().size());
        assertEquals(6, repo.getAllDepartureCities().size());
        assertTrue(repo.getAllDepartureCitiesServedByFutureFlights().contains("Boston"));
        assertTrue(repo.getAllDepartureCitiesServedByFutureFlights().contains("Philadelphia"));
        assertFalse(repo.getAllDepartureCitiesServedByFutureFlights().contains("New York"));
        assertFalse(repo.getAllDepartureCitiesServedByFutureFlights().contains("Detroit"));
        assertFalse(repo.getAllDepartureCitiesServedByFutureFlights().contains("Chicago"));
        assertFalse(repo.getAllDepartureCitiesServedByFutureFlights().contains("Brussels"));
        assertFalse(repo.getAllDepartureCitiesServedByFutureFlights().contains("London"));
    }

    @Test
    public void flightsCanBePersisted(){
        Flight f = new Flight("MD1991", "London", "New York", LocalDateTime.of(LocalDate.of(1985,11,12),LocalTime.now()), 15, 15.15, "BE");
        repo.createFlight(f); repo.getEntityManager().flush();
        assertNotNull(f.getId());
        assertEquals(2, repo.getAllFlights().size());
    }

    @Test
    public void flightsCanBeRemoved(){
        Flight f = new Flight("MD1991", "London", "New York", LocalDateTime.of(LocalDate.of(1985,11,12),LocalTime.now()), 15, 15.15, "BE");
        repo.createFlight(f); repo.getEntityManager().flush();
        assertEquals(2, repo.getAllFlights().size());
        repo.deleteFlight(f); repo.getEntityManager().flush();
        assertEquals(1, repo.getAllFlights().size());
        repo.deleteFlight(repo.getFlightById(1)); repo.getEntityManager().flush();
        assertEquals(0, repo.getAllFlights().size());
    }

}
