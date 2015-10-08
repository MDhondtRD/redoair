package com.realdolmen.redoair.persistence;


import com.realdolmen.redoair.ejb.BookingsRepository;
import com.realdolmen.redoair.ejb.FlightRepository;
import com.realdolmen.redoair.ejb.TripRepository;
import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.Booking;
import com.realdolmen.redoair.entities.Flight;
import com.realdolmen.redoair.entities.Trip;
import com.realdolmen.redoair.entities.User;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BookingRepositoryTest extends DataPersistenceTest {

    private UserRepository uRepo;
    private FlightRepository fRepo;
    private TripRepository tRepo;
    private BookingsRepository repo;
    private static boolean populated = false;
    private int numberOfUsers = 50;
    private int numberOfFlights = 200;
    private int numberOfTrips = 50;
    private int numberOfBookings = 25;

    @Before
    public void before() {
        initialiseRepositories();
        if (!populated) {
            populateDatabase();
            populated = true;
        }
    }

    public void initialiseRepositories() {
        repo = new BookingsRepository();
        repo.setEntityManager(entityManager());
        uRepo = new UserRepository();
        uRepo.setEntityManager(entityManager());
        fRepo = new FlightRepository();
        fRepo.setEntityManager(entityManager());
        tRepo = new TripRepository();
        tRepo.setEntityManager(entityManager());
    }

    public void populateDatabase() {
        for (int i = 1; i <= numberOfUsers; i++)
            uRepo.createUser(new User("Maarten Dhondt " + i, "123", "maarten.dhondt" + i + "@skynet.be"));
        uRepo.getEntityManager().flush();
        for (User u : uRepo.getAllUsers())
            System.out.println(u);
        for (int i = 1; i <= numberOfFlights; i++)
            fRepo.createFlight(new Flight("MD" + (1000 + i), "Brussels", "London", LocalDateTime.now(), 25, 9.99, "ENG"));
        fRepo.getEntityManager().flush();
        for (int i = 1; i <= numberOfTrips/2; i++)
            tRepo.createTrip(new Trip(LocalDate.now(), LocalDate.now().plusDays(5), fRepo.getAllFlights().get((int) (Math.random() * numberOfFlights)), null, 14.95, "RD Travel"));
        tRepo.getEntityManager().flush();
        for (int i = numberOfTrips/2; i <= numberOfTrips; i++)
            tRepo.createTrip(new Trip(LocalDate.now(), LocalDate.now(), fRepo.getAllFlights().get((int) (Math.random() * numberOfFlights)), fRepo.getAllFlights().get((int) (Math.random() * numberOfFlights)), 13.95, "RD Travel"));
        tRepo.getEntityManager().flush();
        for (int i = 1; i <= numberOfBookings; i++) {
            ArrayList<Trip> trips = new ArrayList<Trip>();
            for (int j = 0; j <= ((int)(Math.random()*5)); j++)
                trips.add(tRepo.getAllTrips().get((int) (Math.random() * numberOfTrips)));
            repo.createBooking(new Booking(uRepo.getUserByUsername("Maarten Dhondt " + ((int) (Math.random() * numberOfUsers))), 2, trips));
        }
        repo.getEntityManager().flush();

    }

    @Test
    public void lsdfj(){

    }
}
