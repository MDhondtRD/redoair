package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.ejb.FlightRepository;
import com.realdolmen.redoair.entities.Airport;
import com.realdolmen.redoair.entities.Flight;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class FlightPersistenceTest extends DataPersistenceTest {

    private Airport from;
    private Airport to;

    @Before
    public void init() {
        from = new Airport("London", "Gatwick", "United Kingdom", 0.0, 0.0);
        to = new Airport("New York", "JFK", "United States", 0.0, 0.0);
        entityManager().persist(from);
        entityManager().persist(to);
        entityManager().flush();
    }

    @Test
    public void flightsCanBeCreated() {
        Flight f = new Flight("AA0000", from, to, LocalDateTime.of(LocalDate.of(1985, 11, 12), LocalTime.now()), 15, 399.95);
        entityManager().persist(f);
        assertNotNull(f.getId());
    }

    @Test(expected = NullPointerException.class)
    public void flightsCodeValidationShouldFailOnNullCode() {
        Flight f = new Flight(null, from, to, LocalDateTime.of(LocalDate.of(1985, 11, 12), LocalTime.now()), 15, 399.95);
    }

    @Test(expected = IllegalArgumentException.class)
    public void flightsNeedAValidCode() {
        Flight f2 = new Flight("aa123", from, to, LocalDateTime.of(LocalDate.of(1985, 11, 12), LocalTime.now()), 15, 399.95);
    }


    @Test(expected = IllegalArgumentException.class)
    public void flightsShouldHaveAtLeastOneAvailableSeatWhenCreated() {
        Flight f = new Flight("AA0000", from, to, LocalDateTime.of(LocalDate.of(1985, 11, 12), LocalTime.now()), 0, 399.95);
    }

    @Test(expected = IllegalArgumentException.class)
    public void flightsShouldHaveAPositiveAmountOfAvailableSeatsWhenCreated() {
        Flight f = new Flight("AA0000", from, to, LocalDateTime.of(LocalDate.of(1985, 11, 12), LocalTime.now()), -5, 399.95);
    }

    @Test(expected = IllegalArgumentException.class)
    public void flightsCanNotHaveANegativePrice() {
        Flight f = new Flight("AA0000", from, to, LocalDateTime.of(LocalDate.of(1985, 11, 12), LocalTime.now()), 15, -23.65);
    }

    @Test
    public void flightPricesShouldNotHaveMoreThanTwoDecimals() {
        Flight f = new Flight("AA0000", from, to, LocalDateTime.of(LocalDate.of(1985, 11, 12), LocalTime.now()), 15, 399.999);
        assertEquals(400.00, f.getPrice(), 0.0000000001);
    }

    @Test(expected = PersistenceException.class)
    public void flightsShouldHaveANonNullDepartureCity() {
        Flight f = new Flight("AA0000", null, to, LocalDateTime.of(LocalDate.of(1985, 11, 12), LocalTime.now()), 15, 399.95);
        entityManager().persist(f);
    }

    @Test(expected = PersistenceException.class)
    public void flightsShouldHaveANonNullDestinationCity() {
        Flight f = new Flight("AA0000", from, null, LocalDateTime.of(LocalDate.of(1985, 11, 12), LocalTime.now()), 15, 399.95);
        entityManager().persist(f);
    }

    @Test(expected = PersistenceException.class)
    public void flightsShouldHaveANonNullDepartureDateTime() {
        Flight f = new Flight("AA0000", from, to, null, 15, 399.95);
        entityManager().persist(f);
    }

}