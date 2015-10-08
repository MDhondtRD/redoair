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

public class FlightPersistenceTest extends DataPersistenceTest {

    @Test
    public void flightsCanBeCreated(){
        Flight f = new Flight("AA0000", "London", "New York", LocalDateTime.of(LocalDate.of(1985, 11, 12), LocalTime.now()), 15, 399.95, "BE");;
        entityManager().persist(f);
        assertNotNull(f.getId());
    }

    @Test(expected = NullPointerException.class)
    public void flightsCodeValidationShouldFailOnNullCode(){
        Flight f = new Flight(null, "London", "New York", LocalDateTime.of(LocalDate.of(1985,11,12),LocalTime.now()), 15, 399.95, "BE");
    }

    @Test(expected = IllegalArgumentException.class)
    public void flightsNeedAValidCode(){
        Flight f2 = new Flight("aa123", "London", "New York", LocalDateTime.of(LocalDate.of(1985,11,12),LocalTime.now()), 15, 399.95, "BE");
    }


    @Test(expected = IllegalArgumentException.class)
    public void flightsShouldHaveAtLeastOneAvailableSeatWhenCreated(){
        Flight f = new Flight("AA0000", "London", "New York", LocalDateTime.of(LocalDate.of(1985,11,12),LocalTime.now()), 0, 399.95, "BE");
    }

    @Test(expected = IllegalArgumentException.class)
    public void flightsShouldHaveAPositiveAmountOfAvailableSeatsWhenCreated(){
        Flight f = new Flight("AA0000", "London", "New York", LocalDateTime.of(LocalDate.of(1985,11,12),LocalTime.now()), -5, 399.95, "BE");
    }

    @Test(expected = IllegalArgumentException.class)
    public void flightsCanNotHaveANegativePrice(){
        Flight f = new Flight("AA0000", "London", "New York", LocalDateTime.of(LocalDate.of(1985,11,12),LocalTime.now()), 15, -23.65, "BE");
    }

    @Test
    public void flightPricesShouldNotHaveMoreThanTwoDecimals(){
        Flight f = new Flight("AA0000", "London", "New York", LocalDateTime.of(LocalDate.of(1985,11,12),LocalTime.now()), 15, 399.999, "BE");
        assertEquals(400.00, f.getPrice(), 0.05);
    }

    @Test(expected = PersistenceException.class)
    public void flightsShouldHaveANonNullDepartureCity(){
        Flight f = new Flight("AA0000", null, "New York", LocalDateTime.of(LocalDate.of(1985,11,12), LocalTime.now()), 15, 399.95, "BE");
        entityManager().persist(f);
    }

    @Test(expected = PersistenceException.class)
    public void flightsShouldHaveANonNullDestinationCity(){
        Flight f = new Flight("AA0000", "London", null, LocalDateTime.of(LocalDate.of(1985,11,12),LocalTime.now()), 15, 399.95, "BE");
        entityManager().persist(f);
    }

    @Test(expected = PersistenceException.class)
    public void flightsShouldHaveANonNullDepartureDateTime(){
        Flight f = new Flight("AA0000", "London", "New York", null, 15, 399.95, "BE");
        entityManager().persist(f);
    }

}