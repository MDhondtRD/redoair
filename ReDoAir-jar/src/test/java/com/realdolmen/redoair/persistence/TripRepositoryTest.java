package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.ejb.FlightRepository;
import com.realdolmen.redoair.ejb.TripRepository;
import com.realdolmen.redoair.entities.Flight;
import com.realdolmen.redoair.entities.Trip;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by MDNAX30 on 6/10/2015.
 */
public class TripRepositoryTest extends DataPersistenceTest{

    private TripRepository repo;
    private FlightRepository fRepo;
    private int numberOfFlights = 120;
    private int numberOfFutureFlights = 0;
    private String[] locations = {"London", "Paris", "New York", "Brussels", "Madrid", "Berlin", "Rome", "Lisbon", "Athens"};
    private int numberOfLocations = 9;
    private int numberOfCommonTrips = 10 * numberOfLocations;
    private int numberOfFlightOnlyTrips = 10 * numberOfLocations;

    @Before
    public void before() {
        // Initialise repositories
        repo = new TripRepository();
        repo.setEntityManager(entityManager());
        fRepo = new FlightRepository();
        fRepo.setEntityManager(entityManager());

        // Populate database
        for (int i = 0; i < numberOfFlights; i++) {
            LocalDateTime ldt = LocalDateTime.of(LocalDate.of(2015, 9 + (i / 30), 1 + (i % 30)), LocalTime.now());
            fRepo.createFlight(new Flight("MD" + (1000 + i), locations[(int) (Math.random() * (numberOfLocations - 1))], locations[(int) (Math.random() * (numberOfLocations - 1))], ldt, 15, 399.95));
            if (ldt.isAfter(LocalDateTime.now()))
                numberOfFutureFlights++;
        }
        fRepo.getEntityManager().flush();

//        for (int i = 0; i < numberOfTrips; i++) {
//            Trip t = new Trip(LocalDate.of(2015, 10, 6), LocalDate.of(2015, 10, 10), f1, f2, 0.00, "RD Travel");
//        }
    }

    @Test
    public void sdlkfjsk(){

    }
}
