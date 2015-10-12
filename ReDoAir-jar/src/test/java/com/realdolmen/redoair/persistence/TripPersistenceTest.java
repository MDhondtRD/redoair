package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.entities.Airport;
import com.realdolmen.redoair.entities.Flight;
import com.realdolmen.redoair.entities.Trip;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TripPersistenceTest extends DataPersistenceTest {

    private Airport from;
    private Airport to;

    @Before
    public void init() {
        from = entityManager().find(Airport.class, 492); // London
        to = entityManager().find(Airport.class, 3797); // New York
    }

    @Test
    public void tripCanBePersisted() {
        Flight f = new Flight("MD1991", from, to, LocalDateTime.of(2015, 9, 1, 14, 30), 50, 123.45);
        entityManager().persist(f);
        Trip t = new Trip(LocalDate.of(2015, 10, 1), LocalDate.of(2015, 10, 31), f, null, 0.00, "RD Travel");
        entityManager().persist(t);
        assertNotNull(t.getId());
    }

    @Test(expected = PersistenceException.class)
    public void tripCanNotHaveANullOutflight() {
        Trip t = new Trip(LocalDate.of(2015, 10, 1), LocalDate.of(2015, 10, 31), null, null, 0.00, "RD Travel");
        entityManager().persist(t);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tripEndDateMustNotBeBeforeStartDate() {
        Flight f = new Flight("MD1991", from, to, LocalDateTime.of(2015, 9, 1, 14, 30), 50, 123.45);
        entityManager().persist(f);
        Trip t = new Trip(LocalDate.of(2015, 10, 2), LocalDate.of(2015, 10, 1), f, null, 0.00, "RD Travel");
    }

    @Test(expected = IllegalArgumentException.class)
    public void tripCanNotHaveAnOutFlightAfterTripHasAlreadyStarted() {
        Flight f = new Flight("MD1991", from, to, LocalDateTime.of(2015, 10, 2, 14, 30), 50, 123.45);
        entityManager().persist(f);
        Trip t = new Trip(LocalDate.of(2015, 10, 1), LocalDate.of(2015, 10, 3), f, null, 0.00, "RD Travel");
    }

    @Test(expected = IllegalArgumentException.class)
    public void tripCanNotHaveAnOutFlightAfterTripHasAlreadyEnded() {
        Flight f = new Flight("MD1991", from, to, LocalDateTime.of(2015, 10, 3, 14, 30), 50, 123.45);
        entityManager().persist(f);
        Trip t = new Trip(LocalDate.of(2015, 10, 1), LocalDate.of(2015, 10, 2), f, null, 0.00, "RD Travel");
    }

    @Test(expected = IllegalArgumentException.class)
    public void tripCanNotHaveAReturnFlightWhenTripHasNotYetBegan() {
        Flight f1 = new Flight("MD1991", from, to, LocalDateTime.of(2015, 10, 4, 14, 30), 50, 123.45);
        Flight f2 = new Flight("MD1992", to, from, LocalDateTime.of(2015, 10, 5, 14, 30), 50, 123.45);
        entityManager().persist(f1);
        entityManager().persist(f2);
        Trip t = new Trip(LocalDate.of(2015, 10, 6), LocalDate.of(2015, 10, 10), f1, f2, 0.00, "RD Travel");
    }

    @Test(expected = IllegalArgumentException.class)
    public void tripCanNotHaveAReturnFlightWhenTripHasNotYetEnded() {
        Flight f1 = new Flight("MD1991", from, to, LocalDateTime.of(2015, 10, 5, 14, 30), 50, 123.45);
        Flight f2 = new Flight("MD1992", to, from, LocalDateTime.of(2015, 10, 8, 14, 30), 50, 123.45);
        entityManager().persist(f1);
        entityManager().persist(f2);
        Trip t = new Trip(LocalDate.of(2015, 10, 6), LocalDate.of(2015, 10, 10), f1, f2, 0.00, "RD Travel");
    }

    @Test(expected = IllegalArgumentException.class)
    public void tripCanNotHaveANegativePrice(){
        Trip t = new Trip(LocalDate.of(2015, 10, 1), LocalDate.of(2015, 10, 2), null, null, -12.34, "RD Travel");
    }

    @Test
    public void tripPricesShouldNotHaveMoreThanTwoDecimals() {
        Trip t = new Trip(LocalDate.of(2015, 10, 1), LocalDate.of(2015, 10, 2), null, null, 399.999, "RD Travel");
        assertEquals(400.00, t.getTripDayPrice(), 0.005);
    }

}