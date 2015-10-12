package com.realdolmen.redoair.jaxb;

import com.realdolmen.redoair.entities.Airport;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by JDOAX80 on 12/10/2015.
 */
@XmlRootElement(name = "flight")
public class FlightFormat {

    private String code;

    private String departureAirport;

    private String destinationAirport;

    private Date departure;

    private int availableSeats;

    private double price;

    private double lengthOfFlight;


    public String getCode() {
        return code;
    }

    @XmlElement(name = "code")
    public void setCode(String code) {
        this.code = code;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    @XmlElement(name = "departureAirport")
    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    @XmlElement(name = "destinationAirport")
    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public Date getDeparture() {
        return departure;
    }

    @XmlElement(name = "departureDate")
    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    @XmlElement(name = "availableSeats")
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getPrice() {
        return price;
    }

    @XmlElement(name = "price")
    public void setPrice(double price) {
        this.price = price;
    }

    public double getLengthOfFlight() {
        return lengthOfFlight;
    }

    @XmlElement(name = "lengthOfFlight")
    public void setLengthOfFlight(double lengthOfFlight) {
        this.lengthOfFlight = lengthOfFlight;
    }

}
