package com.realdolmen.redoair.ejb;

import com.realdolmen.redoair.entities.Airport;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    public Airport getAirportById(Integer id) {
        return em.find(Airport.class, id);
    }

    public EntityManager getEntitiyManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
