package com.realdolmen.redoair.persistence;


import com.realdolmen.redoair.ejb.BookingsRepository;
import com.realdolmen.redoair.ejb.FlightRepository;
import com.realdolmen.redoair.ejb.TripRepository;
import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.Booking;
import com.realdolmen.redoair.entities.Flight;
import com.realdolmen.redoair.entities.Trip;
import com.realdolmen.redoair.entities.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class BookingRepositoryTest extends DataPersistenceTest {

//    private UserRepository uRepo;
//    private FlightRepository fRepo;
//    private TripRepository tRepo;
//    private BookingsRepository repo;
//    private static boolean populated = false;
//    private int numberOfUsers = 50;
//    private int numberOfFlights = 200;
//    private int numberOfTrips = 50;
//    private int numberOfBookings = 25;
//
//    @Before
//    public void before() {
//        initialiseRepositories();
//        if (!populated) {
//            populateDatabase();
//            populated = true;
//        }
//    }
//
//    @After
//    public void after() {
//        for (Booking b : repo.getAllBookings())
//            repo.removeBooking(b);
//        repo.getEntityManager().flush();
//        for (Trip t : tRepo.getAllTrips())
//            tRepo.removeTrip(t);
//        tRepo.getEntityManager().flush();
//        for (Flight f : fRepo.getAllFlights())
//            fRepo.removeFlight(f);
//        fRepo.getEntityManager().flush();
//        for (User u : uRepo.getAllUsers())
//            uRepo.removeUser(u);
//        uRepo.getEntityManager().flush();
//        populated = false;
//    }
//
//    public void initialiseRepositories() {
//        repo = new BookingsRepository();
//        repo.setEntityManager(entityManager());
//        uRepo = new UserRepository();
//        uRepo.setEntityManager(entityManager());
//        fRepo = new FlightRepository();
//        fRepo.setEntityManager(entityManager());
//        tRepo = new TripRepository();
//        tRepo.setEntityManager(entityManager());
//    }
//
//    public void populateDatabase() {
//        for (int i = 1; i <= numberOfUsers; i++)
//            uRepo.createUser(new User("Maarten Dhondt " + i, "123", "maarten.dhondt" + i + "@skynet.be"));
//        uRepo.getEntityManager().flush();
//        for (int i = 1; i <= numberOfFlights; i++)
//            fRepo.createFlight(new Flight("MD" + (6000 + i), "Brussels", "London", LocalDateTime.now(), 25, 9.99, "ENG"));
//        fRepo.getEntityManager().flush();
//        for (int i = 1; i <= numberOfTrips / 2; i++)
//            tRepo.createTrip(new Trip(LocalDate.now(), LocalDate.now().plusDays(5), fRepo.getAllFlights().get((int) (Math.random() * numberOfFlights)), null, 14.95, "RD Travel"));
//        tRepo.getEntityManager().flush();
//        for (int i = numberOfTrips / 2; i <= numberOfTrips; i++)
//            tRepo.createTrip(new Trip(LocalDate.now(), LocalDate.now(), fRepo.getAllFlights().get((int) (Math.random() * numberOfFlights)), fRepo.getAllFlights().get((int) (Math.random() * numberOfFlights)), 13.95, "RD Travel"));
//        tRepo.getEntityManager().flush();
//        for (int i = 1; i <= numberOfBookings; i++) {
//            HashSet<Trip> temp = new HashSet<Trip>();
//            int numberOfTripsInBooking = ((int) (Math.random() * 5));
//            for (int j = 0; j <= numberOfTripsInBooking; j++)
//                temp.add(tRepo.getAllTrips().get((int) (Math.random() * numberOfTrips)));
//            ArrayList<Trip> trips = new ArrayList<Trip>(temp);
//            User u = uRepo.getUserByUsername("Maarten Dhondt " + ((int) (Math.random() * numberOfUsers)+1));
//            repo.createBooking(new Booking(u, 2, trips));
//        }
//        repo.getEntityManager().flush();
//    }
//
//    @Test
//    public void allBookingsCanBeRetrieved() {
//        assertEquals(numberOfBookings, repo.getAllBookings().size());
//    }
//
//    @Test
//    public void bookingsCanBeFoundByTheirId() {
//        int startIndex = repo.getEntityManager().createQuery("SELECT MIN(b.id) FROM Booking b", Integer.class).getSingleResult();
//        for (int i = startIndex; i <= numberOfBookings; i++)
//            assertNotNull(repo.getBookingById(i));
//        assertNull(repo.getBookingById(startIndex+numberOfBookings));
//    }
//
//    @Test
//    public void allBookingsOfAUserCanBeRetrieved() {
//        for (int i = 1; i <= numberOfUsers; i++) {
//            int number = 0;
//            User user = uRepo.getUserByUsername("Maarten Dhondt " + i);
//            for (Booking booking : repo.getAllBookings())
//                if (booking.getUser() == user)
//                    number++;
//            assertEquals(number, repo.getAllBookingsOfUser(user).size());
//        }
//    }
//
//    @Test
//    public void bookingsCanBeCreatedAndDeleted() {
//        Booking booking = new Booking(uRepo.getUserByUsername("Maarten Dhondt " + numberOfUsers), 5, Arrays.asList(tRepo.getAllTrips().get(0)));
//        assertEquals(numberOfBookings, repo.getAllBookings().size());
//        repo.createBooking(booking);
//        repo.getEntityManager().flush();
//        assertEquals(numberOfBookings + 1, repo.getAllBookings().size());
//        repo.removeBooking(booking);
//        repo.getAllBookings().size();
//        assertEquals(numberOfBookings, repo.getAllBookings().size());
//    }
}
