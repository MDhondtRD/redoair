package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.ejb.FlightRepository;
import com.realdolmen.redoair.entities.Flight;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.PersistenceException;
import java.util.Date;

public class FlightPersistenceTest extends DataPersistenceTest {

    private FlightRepository repo;

    @Before
    public void before(){
        repo = new FlightRepository();
        repo.setEntityManager(entityManager());
    }

    @Test
    public void flightsCanBeCreated(){
        Flight f1 = new Flight();
        Flight f2 = new Flight("AA0000", "London", "New York", new Date(), 15, 399.95);
    }

    @Test(expected = NullPointerException.class)
    public void flightsCodeValidationShouldFailOnNullCode(){
        Flight f = new Flight(null, "London", "New York", new Date(), 15, 399.95);
    }

    @Test(expected = IllegalArgumentException.class)
    public void flightsNeedAValidCode(){
        Flight f2 = new Flight("aa123", "London", "New York", new Date(), 15, 399.95);
    }

    @Test(expected = PersistenceException.class)
    public void flightsShouldHaveANonNullDepartureCity(){
        Flight f = new Flight("AA0000", null, "New York", new Date(), 15, 399.95);
        repo.createFlight(f);
    }

    @Test(expected = PersistenceException.class)
    public void flightsShouldHaveANonNullDestinationCity(){
        Flight f = new Flight("AA0000", "London", null, new Date(), 15, 399.95);
        repo.createFlight(f);
    }

    @Test(expected = PersistenceException.class)
    public void flightsShouldHaveANonNullDepartureDateTime(){
        Flight f = new Flight("AA0000", "London", "New York", null, 15, 399.95);
        repo.createFlight(f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void flightsShouldHaveAtLeastOneAvailableSeatWhenCreated(){
        Flight f = new Flight("AA0000", "London", "New York", new Date(), 0, 399.95);
    }

    @Test(expected = IllegalArgumentException.class)
    public void flightsShouldHaveAPositiveAmountOfAvailableSeatsWhenCreated(){
        Flight f = new Flight("AA0000", "London", "New York", new Date(), -5, 399.95);
    }

    @Test(expected = IllegalArgumentException.class)
    public void flightsCanNotHaveANegativePrice(){
        Flight f = new Flight("AA0000", "London", "New York", new Date(), 15, -23.65);
    }

    @Test
    public void flightPricesShouldNotHaveMoreThanTwoDecimals(){
        Flight f = new Flight("AA0000", "London", "New York", new Date(), 15, 399.999);
        assertEquals(400.00, f.getPrice(),0.005);
    }

    @Test
    public void flightsCanBeRetrieved(){
        assertEquals(1, repo.getAllFlights().size());
        Flight f = new Flight("AA0000", "London", "New York", new Date(), 15, 399.999);
        repo.createFlight(f);
        assertEquals(2, repo.getAllFlights().size());
    }

    @Test
    public void flightsCanBeRetrievedById(){

    }
}
