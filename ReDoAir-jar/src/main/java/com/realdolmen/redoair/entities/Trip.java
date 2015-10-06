package com.realdolmen.redoair.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
public class Trip {

    /**
     * ID
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;



    /**
     * ATTRIBUTES
     */

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date departureDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @ManyToOne
    @Column(nullable = false)
    private Flight outFlight;

    @ManyToOne
    private Flight returnFlight;

    @Column(nullable = false)
    private double tripPricePerDayIncludesHotelAndLocalTransportAndGuidedToursAndMuchMore;

    private String travelAgency;



    /**
     * CONSTRUCTORS
     */

    public Trip() {
        // required no-argument constructor
    }

    public Trip(LocalDateTime departureDate, LocalDateTime returnDate, Flight outFlight, Flight returnFlight, double price, String travelAgency) {
        setDepartureDate(departureDate);
        setReturnDate(returnDate);
        setOutFlight(outFlight);
        setReturnFlight(returnFlight);
        setTripPricePerDayIncludesHotelAndLocalTransportAndGuidedToursAndMuchMore(price);
        setTravelAgency(travelAgency);
    }



    /**
     * GETTERS & SETTERS
     */

    public Integer getId() {
        return id;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        if(departureDate != null)
            this.departureDate = Date.from(departureDate.atZone(ZoneId.systemDefault()).toInstant());
        else
            this.departureDate = null; //Will throw a persistence exception if trying to persist a flight with a null as departure value.
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        if(returnDate != null)
            this.returnDate = Date.from(returnDate.atZone(ZoneId.systemDefault()).toInstant());
        else
            this.returnDate = null; //Will throw a persistence exception if trying to persist a flight with a null as departure value.
    }

    public Flight getOutFlight() {
        return outFlight;
    }

    public void setOutFlight(Flight outFlight) {
        if (outFlight.getDeparture().toInstant().truncatedTo(ChronoUnit.DAYS)
                .isAfter(departureDate.toInstant().truncatedTo(ChronoUnit.DAYS)))
            throw new IllegalArgumentException("Outgoing flight should not be after start of the trip.");
        this.outFlight = outFlight;
    }

    public Flight getReturnFlight() {
        return returnFlight;
    }

    public void setReturnFlight(Flight returnFlight) {
        if (returnFlight.getDeparture().toInstant().truncatedTo(ChronoUnit.DAYS)
                .isBefore(returnDate.toInstant().truncatedTo(ChronoUnit.DAYS)))
            throw new IllegalArgumentException("Return flight should not be before end of the trip.");
        this.returnFlight = returnFlight;
    }

    public double getTripPricePerDayIncludesHotelAndLocalTransportAndGuidedToursAndMuchMore() {
        return tripPricePerDayIncludesHotelAndLocalTransportAndGuidedToursAndMuchMore;
    }

    public void setTripPricePerDayIncludesHotelAndLocalTransportAndGuidedToursAndMuchMore(double price) {
        this.tripPricePerDayIncludesHotelAndLocalTransportAndGuidedToursAndMuchMore = price;
    }

    public String getTravelAgency() {
        return travelAgency;
    }

    public void setTravelAgency(String travelAgency) {
        this.travelAgency = travelAgency;
    }



    /**
     * METHODS
     */


}
