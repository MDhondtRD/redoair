package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.entities.*;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class BookingPersistenceTest extends DataPersistenceTest {

//    @Test
//    public void bookingsCanBeCreated(){
//        new Booking(new User("MDhondt", "123", UserType.CUSTOMER, "m.d@s.com"), 1);
//        new Booking(new User("MDhondt", "123", UserType.CUSTOMER, "m.d@s.com"), 2, new ArrayList<Trip>());
//        Flight f = new Flight("MD1991", "Brussels", "London", LocalDateTime.now(), 10, 24.95, "ENG");
//        Trip t = new Trip(LocalDate.now(), LocalDate.now(), f, null, 14.99, "RD Travel");
//        new Booking(new User("MDhondt", "123", UserType.CUSTOMER, "m.d@s.com"), 3, Arrays.asList(t));
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void bookingsCanNotBeMadeWithAnEmployeeAccount(){
//        new Booking(new User("MDhondt","123", UserType.EMPLOYEE,"m.d@s.com"), 1);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void bookingsCanNotBeMadeWithAPartnerAccount(){
//        new Booking(new User("MDhondt","123", UserType.PARTNER,"m.d@s.com"), 2);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void bookingsCanNotBeMadeWithANullUser(){
//        new Booking(null, 1);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void aUserCanNotBookZeroSeats(){
//        new Booking(new User("MDhondt","123", UserType.EMPLOYEE,"m.d@s.com"), 0);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void aUserCanNotBookLessThanZeroSeats(){
//        new Booking(new User("MDhondt","123", UserType.EMPLOYEE,"m.d@s.com"), -1);
//    }
//
//    @Test
//    public void bookingsCanBeCommited(){
//        User u = new User("MD", "123", UserType.CUSTOMER, "m.d@s.com");
//        Booking booking1 = new Booking(u, 1);
//        Booking booking2 = new Booking(u, 2, new ArrayList<Trip>());
//        Flight f = new Flight("MD1991", "Brussels", "London", LocalDateTime.now(), 10, 24.95, "ENG");
//        Trip t = new Trip(LocalDate.now(), LocalDate.now(), f, null, 14.99, "RD Travel");
//        Booking booking3 = new Booking(u, 3, Arrays.asList(t));
//        entityManager().persist(f); entityManager().persist(t); entityManager().persist(u);
//        entityManager().persist(booking1);
//        entityManager().persist(booking2);
//        entityManager().persist(booking3);
//        entityManager().flush();
//        assertNotNull(booking1.getId());
//        assertNotNull(booking2.getId());
//        assertNotNull(booking3.getId());
//    }
//
//    @Test
//    public void bookingsCanBeRetrieved(){
//        User u = new User("MD2", "123", UserType.CUSTOMER, "m2.d@s.com");
//        Booking b = new Booking(u, 1);
//        entityManager().persist(u); entityManager().persist(b); entityManager().flush();
//        assertEquals(u, entityManager().find(User.class, u.getId()));
//    }


}
