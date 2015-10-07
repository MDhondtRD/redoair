package com.realdolmen.air.controllers;

import com.realdolmen.redoair.ejb.FlightRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.Set;

@Named
@RequestScoped
public class AddFlightsController {

    @Inject
    private FlightRepository repo;

    private String code;

    private String departureCity;

    private String destinationCity;

    private Date departure;

    private int availableSeats;

    private double price;

    public int getAvailableSeats() {
        return availableSeats;
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

    public Set<String> getAllMyCurrentCodes(String username){
        System.out.println("Running..................");
        return repo.getAllFlightCodesFromPartner(username);
    }

    public Set<String> getDepartureCities(){
        return repo.getAllDepartureCities();
    }

    public Set<String> getDestinationCities(){
        return repo.getAllDestinationCities();
    }

    public String addNewFlight(){
       // Flight f = new Flight(code, departureCity, destinationCity, departure, availableSeats, price);
       // repo.createFlight(f);
        return "addFlight"; //TODO: redirect naar ??
    }

}
