package com.realdolmen.redoair.ejb;

import com.realdolmen.redoair.entities.Booking;
import com.realdolmen.redoair.entities.Trip;
import com.realdolmen.redoair.entities.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@LocalBean
public class BookingsRepository implements BookingsRepositoryInterface {

    @PersistenceContext
    private EntityManager em;

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
        em.persist(booking);
    }

    @Override
    public void removeBooking(Booking booking) {
        em.remove(booking);
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
