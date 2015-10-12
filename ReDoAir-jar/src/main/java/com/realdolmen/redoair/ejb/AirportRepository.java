package com.realdolmen.redoair.ejb;

import com.realdolmen.redoair.entities.Airport;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
@LocalBean
public class AirportRepository implements AirportRepositoryInterface {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Airport> getAllAirports() {
        return em.createQuery("SELECT a FROM Airport a", Airport.class).getResultList();
    }

    @Override
    public List<Airport> getAllAirportsByCountry(String country) {
        return em.createQuery("SELECT a FROM Airport a WHERE a.country = :country", Airport.class)
                .setParameter("country", country).getResultList();
    }

    @Override
    public List<Airport> getAllAirportsByCity(String city) {
        return em.createQuery("SELECT a FROM Airport a WHERE a.city = :city", Airport.class)
                .setParameter("city", city).getResultList();
    }

    @Override
    public Set<String> getAllCountries() {
        return new HashSet<String>(em.createQuery("SELECT a.country FROM Airport a", String.class).getResultList());
    }

    @Override
    public Set<String> getAllCitiesByCountry(String country) {
        return new HashSet<String>(em.createQuery("SELECT a.city FROM Airport a WHERE a.country = :country")
                .setParameter("country", country).getResultList());
    }


    @Override
    public Airport getAirportById(Integer id) {
        return em.find(Airport.class, id);
    }

    @Override
    public List<Airport> getAirportByCity(String city) {
        List<Airport> results = em.createQuery("SELECT a FROM Airport a WHERE a.city = :city", Airport.class)
                .setParameter("city", city).getResultList();
        if(!results.isEmpty()) {
            return results;
        }
        else {
            return null;
        }
    }

    @Override
    public Airport getAirportByName(String name) {
        List results = em.createQuery("SELECT a FROM Airport a WHERE a.name = :name", Airport.class)
                .setParameter("name", name).getResultList();
        if(!results.isEmpty()) {
            return (Airport) results.get(0);
        }
        else {
            return null;
        }
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
