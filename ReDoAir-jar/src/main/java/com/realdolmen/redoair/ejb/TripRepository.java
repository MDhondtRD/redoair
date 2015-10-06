package com.realdolmen.redoair.ejb;

import com.realdolmen.redoair.entities.Trip;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@LocalBean
public class TripRepository implements TripRepositoryInterface{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Trip> getAllTrips() {
        return em.createQuery("SELECT t FROM Trip t", Trip.class)
                .getResultList();
    }

    @Override
    public Trip getTripById(Integer id) {
        return em.find(Trip.class, id);
    }

    @Override
    public List<Trip> getAllFutureTrips() {
        return em.createQuery("SELECT t FROM Trip t " +
                "WHERE t.departureDate > current_date", Trip.class)
                .getResultList();
    }

    @Override
    public List<Trip> getAllTripsByDestination(String destination) {
        return em.createQuery("SELECT t FROM Trip t " +
                "WHERE t.outFlight.destinationCity = :dest", Trip.class)
                .setParameter("dest", destination)
                .getResultList();
    }

    @Override
    public List<Trip> getAllFutureTripsByDestination(String destination) {
        return em.createQuery("SELECT t FROM Trip t " +
                "WHERE t.outFlight.destinationCity = :dest " +
                "AND t.departureDate > current_date", Trip.class)
                .setParameter("dest", destination)
                .getResultList();
    }

    @Override
    public List<Trip> getAllTripsWithoutReturnFlight() {
        return em.createQuery("SELECT t FROM Trip t " +
                "WHERE t.returnFlight IS NULL", Trip.class)
                .getResultList();
    }

    @Override
    public List<Trip> getAllTripsWithoutReturnFlightByDestination(String destination) {
        return em.createQuery("SELECT t FROM Trip t " +
                "WHERE t.returnFlight IS NULL " +
                "AND t.outFlight.destinationCity = :dest", Trip.class)
                .setParameter("dest", destination)
                .getResultList();
    }

    @Override
    public List<Trip> getAllFutureTripsWithoutReturnFlight() {
        return em.createQuery("SELECT t FROM Trip t " +
                "WHERE t.departureDate > current_date " +
                "AND t.returnFlight IS NULL", Trip.class)
                .getResultList();
    }

    @Override
    public List<Trip> getAllFutureTripsWithoutReturnFlightByDestination(String destination) {
        return em.createQuery("SELECT t FROM Trip t " +
                "WHERE t.departureDate > current_date " +
                "AND t.returnFlight IS NULL " +
                "AND t.outFlight.destinationCity = :dest", Trip.class)
                .setParameter("dest", destination)
                .getResultList();
    }

    @Override
    public List<Trip> getAllFlightOnlyTrips() {
        return em.createQuery("SELECT t FROM Trip t " +
                "WHERE t.departureDate = t.returnDate " +
                "AND t.tripPricePerDayIncludesHotelAndLocalTransportAndGuidedToursAndMuchMore = 0.0", Trip.class)
                .getResultList();
    }

    @Override
    public List<Trip> getAllFlightOnlyTripsByDestination(String destination) {
        return em.createQuery("SELECT t FROM Trip t " +
                "WHERE t.departureDate = t.returnDate " +
                "AND t.tripPricePerDayIncludesHotelAndLocalTransportAndGuidedToursAndMuchMore = 0.0" +
                "AND t.outFlight.destinationCity = :dest", Trip.class)
                .setParameter("dest", destination)
                .getResultList();
    }

    @Override
    public List<Trip> getAllFutureFlightOnlyTrips() {
        return em.createQuery("SELECT t FROM Trip t " +
                "WHERE t.departureDate = t.returnDate " +
                "AND t.tripPricePerDayIncludesHotelAndLocalTransportAndGuidedToursAndMuchMore = 0.0" +
                "AND t.departureDate > current_date ", Trip.class)
                .getResultList();
    }

    @Override
    public List<Trip> getAllFutureFlightOnlyTripsByDestination(String destination) {
        return em.createQuery("SELECT t FROM Trip t " +
                "WHERE t.departureDate = t.returnDate " +
                "AND t.tripPricePerDayIncludesHotelAndLocalTransportAndGuidedToursAndMuchMore = 0.0" +
                "AND t.departureDate > current_date " +
                "AND t.outFlight.destinationCity = :dest", Trip.class)
                .setParameter("dest", destination)
                .getResultList();
    }

    @Override
    public List<Trip> getAllFlightOnlyTripsWithoutReturnFlight() {
        return em.createQuery("SELECT t FROM Trip t " +
                "WHERE t.departureDate = t.returnDate " +
                "AND t.tripPricePerDayIncludesHotelAndLocalTransportAndGuidedToursAndMuchMore = 0.0" +
                "AND t.returnFlight IS NULL", Trip.class)
                .getResultList();
    }

    @Override
    public List<Trip> getAllFlightOnlyTripsWithoutReturnFlightByDestination(String destination) {
        return em.createQuery("SELECT t FROM Trip t " +
                "WHERE t.departureDate = t.returnDate " +
                "AND t.tripPricePerDayIncludesHotelAndLocalTransportAndGuidedToursAndMuchMore = 0.0" +
                "AND t.returnFlight IS NULL " +
                "AND t.outFlight.destinationCity = :dest", Trip.class)
                .setParameter("dest", destination)
                .getResultList();
    }

    @Override
    public List<Trip> getAllFutureFlightOnlyTripsWithoutReturnFlight() {
        return em.createQuery("SELECT t FROM Trip t " +
                "WHERE t.departureDate = t.returnDate " +
                "AND t.tripPricePerDayIncludesHotelAndLocalTransportAndGuidedToursAndMuchMore = 0.0" +
                "AND t.departureDate > current_date " +
                "AND t.returnFlight IS NULL", Trip.class)
                .getResultList();
    }

    @Override
    public List<Trip> getAllFutureFlightOnlyTripsWithoutReturnFlightByDestination(String destination) {
        return em.createQuery("SELECT t FROM Trip t " +
                "WHERE t.departureDate = t.returnDate " +
                "AND t.tripPricePerDayIncludesHotelAndLocalTransportAndGuidedToursAndMuchMore = 0.0" +
                "AND t.departureDate > current_date " +
                "AND t.returnFlight IS NULL " +
                "AND t.outFlight.destinationCity = :dest", Trip.class)
                .setParameter("dest", destination)
                .getResultList();
    }

    public EntityManager getEntityManager(){
        return em;
    }

    public void setEntityManager(EntityManager em){
        this.em = em;
    }
}
