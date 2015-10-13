package com.realdolmen.air.controllers;

import com.realdolmen.redoair.ejb.AirportRepository;
import com.realdolmen.redoair.ejb.FlightRepository;
import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.Airport;
import com.realdolmen.redoair.entities.Flight;
import com.realdolmen.redoair.entities.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@ManagedBean
@SessionScoped
public class AddFlightsController implements Serializable {

    /**
     * Repositories
     */

    @Inject
    private AirportRepository aRepo;

    @Inject
    private UserRepository uRepo;

    @Inject
    private FlightRepository repo;

    @Inject
    private Principal principal;


    /**
     * ATTRIBUTES
     */

    private String code;

    private String departureCountry;
    private String departureCity;
    private Airport departureAirport;

    private String destinationCountry;
    private String destinationCity;
    private Airport destinationAirport;

    private Date departure;

    private int availableSeats;

    private double price;


    /**
     * GETTERS
     */

    public String getCode() {
        return code;
    }

    public String getDepartureCountry() {
        return departureCountry;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public Date getDeparture() {
        return departure;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public double getPrice() {
        return price;
    }


    /**
     * SETTERS
     */

    public void setCode(String code) {
        this.code = code;
    }

    public void setDepartureCountry(String departureCountry) {
        this.departureCountry = departureCountry;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    /**
     * METHODS
     */

    public Set<String> getAllMyCurrentCodes() {
        return new TreeSet<String>(repo.getAllFlightCodesFromPartner(uRepo.getUserByEmail(principal.getName()).getUsername()));
    }

    public Set<String> getAllCountries() {
        return new TreeSet<String>(aRepo.getAllCountries());
    }

    public Set<String> getAllDepartureCities() {
        return new TreeSet<String>(aRepo.getAllCitiesByCountry(departureCountry));
    }

    public Set<String> getAllDestinationCities() {
        return new TreeSet<String>(aRepo.getAllCitiesByCountry(destinationCountry));
    }

    public List<Airport> getAllDepartureAirports() {
        return aRepo.getAllAirportsByCity(departureCity);
    }

    public List<Airport> getAllDestinationAirports() {
        return aRepo.getAllAirportsByCity(destinationCity);
    }


    /**
     * PERSIST & REDIRECT
     */

    public String addNewFlight() {
        repo.createFlight(new Flight("JA0018", departureAirport, destinationAirport, departure.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), availableSeats, price));
        return "addFlight"; //TODO: redirect naar ??
    }

}
