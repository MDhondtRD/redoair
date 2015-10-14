package com.realdolmen.redoair.ejb;

import com.realdolmen.redoair.entities.Booking;
import com.realdolmen.redoair.entities.Flight;
import com.realdolmen.redoair.entities.Trip;
import com.realdolmen.redoair.entities.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
@LocalBean
public class BookingsRepository implements BookingsRepositoryInterface, Serializable {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private FlightRepository flightRepository;

    @Override
    public List<Booking> getAllBookings() {
        return em.createQuery("SELECT b FROM Booking b", Booking.class).getResultList();
    }

    @Override
    public Booking getBookingById(Integer id) {
        return em.find(Booking.class, id);
    }

    @Override
    public List<Booking> getAllBookingsOfUser(User user) {
        return em.createQuery("SELECT b FROM Booking b WHERE b.user = :usr").setParameter("usr", user).getResultList();
    }

    @Override
    public void createBooking(Booking booking) {
        List<Flight> flights = new ArrayList<>();
        Flight outFlight;
        Flight returnFlight;
        for(Trip trip : booking.getTrips()) {
            outFlight = flightRepository.getFlightById(trip.getOutFlight().getId());
            outFlight.setAvailableSeats(outFlight.getAvailableSeats()- booking.getSeatsBooked());
            returnFlight = flightRepository.getFlightById(trip.getReturnFlight().getId());
            returnFlight.setAvailableSeats(returnFlight.getAvailableSeats()- booking.getSeatsBooked());
            flights.add(outFlight);
            flights.add(returnFlight);
        }
        for(Flight flight: flights) {
            em.merge(flight);
        }
        em.persist(booking);
    }

    @Override
    public void removeBooking(Booking booking) {
        em.remove(booking);
    }

    /**
     * https://code.google.com/p/gnuc-credit-card-checker/source/browse/trunk/CCCheckerPro/src/com/gnuc/java/ccc/Luhn.java
     */
    @Override
    public boolean isValidCreditCardNumber(String creditCardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = creditCardNumber.length() - 1; i >= 0; i--)
        {
            int n = Integer.parseInt(creditCardNumber.substring(i, i + 1));
            if (alternate)
            {
                n *= 2;
                if (n > 9)
                {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
