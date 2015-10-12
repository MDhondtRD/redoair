package com.realdolmen.redoair.jaxb;

import com.realdolmen.redoair.entities.Flight;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by JDOAX80 on 11/10/2015.
 */
@XmlRootElement(name = "trip")
public class TripFormat {


    private Date departureDate;
    private Date returnDate;
    private FlightFormat outFlight;
    private FlightFormat returnFlight;
    private double tripDayPrice;
    private String travelAgency;



    public Date getDepartureDate() {
        return departureDate;
    }

    @XmlElement(name = "departureDate")
    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    @XmlElement(name = "returnDate")
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public FlightFormat getOutFlight() {
        return outFlight;
    }

    @XmlElement(name = "outFlight")
    public void setOutFlight(FlightFormat outFlight) {
        this.outFlight = outFlight;
    }

    public FlightFormat getReturnFlight() {
        return returnFlight;
    }

    @XmlElement(name = "returnFlight")
    public void setReturnFlight(FlightFormat returnFlight) {
        this.returnFlight = returnFlight;
    }

    public double getTripDayPrice() {
        return tripDayPrice;
    }

    @XmlElement(name = "tripDayPrice")
    public void setTripDayPrice(double tripDayPrice) {
        this.tripDayPrice = tripDayPrice;
    }

    public String getTravelAgency() {
        return travelAgency;
    }

    @XmlElement(name = "travelAgency")
    public void setTravelAgency(String travelAgency) {
        this.travelAgency = travelAgency;
    }
}
