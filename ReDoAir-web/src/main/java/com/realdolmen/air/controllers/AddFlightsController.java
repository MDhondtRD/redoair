package com.realdolmen.air.controllers;

import com.realdolmen.redoair.ejb.AirportRepository;
import com.realdolmen.redoair.ejb.FlightRepository;
import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.Airport;
import com.realdolmen.redoair.entities.Flight;
import com.realdolmen.redoair.entities.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Named
@RequestScoped
public class AddFlightsController {

    @Inject
    private AirportRepository aRepo;

    @Inject
    private UserRepository uRepo;

    @Inject
    private FlightRepository repo;

    @Inject
    private Principal principal;

    private String code;

    private String departureCountry;

    private String departureCity;

    private String destinationCountry;

    private String destinationCity;

    private Airport departureAirport;

    private Airport destinationAirport;

    private Date departure;

    private int availableSeats;

    private double price;

    public int getAvailableSeats() {
        return availableSeats;
    }

    public String getDepartureCountry() {
        return departureCountry;
    }

    public void setDepartureCountry(String departureCountry) {
        this.departureCountry = departureCountry;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<String> getAllMyCurrentCodes(){
        return new TreeSet<String>(repo.getAllFlightCodesFromPartner(uRepo.getUserByEmail(principal.getName()).getUsername()));
    }

    public Set<String> getDepartureCities(){
        return repo.getAllDepartureCities();
    }

    public Set<String> getDestinationCities(){
        return repo.getAllDestinationCities();
    }

    public String addNewFlight(){
        System.out.println("  ------------ Adding flight ------------");
       repo.createFlight(new Flight(code, departureAirport, destinationAirport, departure.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), availableSeats, price));
        return "addFlight"; //TODO: redirect naar ??
    }

    public Set<String> getAllDepartureCities(){
        return aRepo.getAllCitiesByCountry(departureCountry);
    }

    public Set<String> getAllDestinationCities(){
        return aRepo.getAllCitiesByCountry(destinationCountry);
    }

    public List<Airport> getAllDepartureAirports(){
        return aRepo.getAllAirportsByCity(departureCity);
    }

    public List<Airport> getAllDestinationAirports(){
        return aRepo.getAllAirportsByCity(destinationCity);
    }

    public Set<String> getAllCountries(){
        return aRepo.getAllCountries();
    }

}
