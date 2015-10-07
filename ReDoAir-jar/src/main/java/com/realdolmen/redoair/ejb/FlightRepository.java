package com.realdolmen.redoair.ejb;

import com.realdolmen.redoair.entities.Flight;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Stateless
@LocalBean
public class FlightRepository implements FlightRepositoryInterface {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Flight> getAllFlights() {
        return em.createQuery("SELECT f FROM Flight f", Flight.class).getResultList();
    }

    @Override
    public List<Flight> getAllFutureFlights() {
        return em.createQuery("SELECT f FROM Flight f WHERE f.departure > current_timestamp").getResultList();
    }

    @Override
    public List<Flight> getAllFlightsByDestinationCity(String city) {
        return em.createQuery("SELECT f FROM Flight f WHERE f.destinationCity = :city", Flight.class)
                .setParameter("city", city).getResultList();
    }

    @Override
    public List<Flight> getAllFlightsByDepartureCity(String city) {
        return em.createQuery("SELECT f FROM Flight f WHERE f.departureCity = :city", Flight.class)
                .setParameter("city", city).getResultList();
    }

    @Override
    public List<Flight> getAllFutureFlightsByDestinationCity(String city) {
        return em.createQuery("SELECT f FROM Flight f WHERE f.destinationCity = :city AND f.departure > CURRENT_TIMESTAMP", Flight.class)
                .setParameter("city", city).getResultList();
    }

    @Override
    public List<Flight> getAllFutureFlightsByDepartureCity(String city) {
        return em.createQuery("SELECT f FROM Flight f WHERE f.departureCity = :city AND f.departure > CURRENT_TIMESTAMP", Flight.class)
                .setParameter("city", city).getResultList();
    }

    @Override
    public Set<String> getAllDestinationCities() {
        List<String> list = em.createQuery("SELECT f.destinationCity FROM Flight f", String.class).getResultList();
        return new HashSet<String>(list);
    }

    @Override
    public Set<String> getAllDepartureCities() {
        List<String> list = em.createQuery("SELECT f.departureCity FROM Flight f", String.class).getResultList();
        return new HashSet<String>(list);
    }

    @Override
    public Set<String> getAllDestinationCitiesServedByFutureFlights() {
        List<String> list = em.createQuery("SELECT f.destinationCity FROM Flight f WHERE f.departure > CURRENT_TIMESTAMP", String.class).getResultList();
        return new HashSet<String>(list);
    }

    @Override
    public Set<String> getAllDepartureCitiesServedByFutureFlights() {
        List<String> list = em.createQuery("SELECT f.departureCity FROM Flight f WHERE f.departure > CURRENT_TIMESTAMP", String.class).getResultList();
        return new HashSet<String>(list);
    }

    @Override
    public Flight getFlightById(Integer id) {
        return em.find(Flight.class, id);
    }

    @Override
    public List<Flight> getAllFlightsByCode(String code) {
        return em.createQuery("SELECT f FROM Flight f WHERE f.code = :code", Flight.class).setParameter("code", code).getResultList();
    }


    @Override
    public void createFlight(Flight flight) {
        em.persist(flight);
    }

    @Override
    public void deleteFlight(Flight flight) {
        em.remove(flight);
    }

    @Override
    public Set<String> getAllCodesFromPartner(String username){
        List<String> list = em.createQuery("SELECT f.code FROM Flight f", String.class).getResultList();
        Set<String> codes =  new HashSet<String>(list);
        String airlineCode = getAirlineCodeFromUsername(username);
        Set<String> partnerCodes = new HashSet<String>();
        for (String code : codes)
            if (code.contains(airlineCode))
                partnerCodes.add(code);
        return partnerCodes;
    }

    public EntityManager getEntityManager(){
        return em;
    }

    public void setEntityManager(EntityManager em){
        this.em = em;
    }


    private String getAirlineCodeFromUsername(String username){
        String airlineCode = "";
        for (int i = 0; i < username.length(); i++)
            if(Character.isUpperCase(username.charAt(i)))
                airlineCode += username.charAt(i);
        return airlineCode;
    }
}