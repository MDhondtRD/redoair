package com.realdolmen.redoair.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Trip implements Serializable {

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

    @ManyToOne(optional = false)
    private Flight outFlight;

    @ManyToOne
    private Flight returnFlight;

    @Column(nullable = false)
    private double tripDayPrice;

    private String travelAgency;


    /**
     * CONSTRUCTORS
     */

    public Trip() {
        // required no-argument constructor
    }

    public Trip(LocalDate departureDate, LocalDate returnDate, Flight outFlight, Flight returnFlight, double price, String travelAgency) {
        setDepartureDate(departureDate);
        setReturnDate(returnDate);
        setOutFlight(outFlight);
        setReturnFlight(returnFlight);
        setTripDayPrice(price);
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

    public void setDepartureDate(LocalDate departureDate) {
        if (departureDate != null)
            this.departureDate = Date.from(departureDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        else
            this.departureDate = null; //Will throw a persistence exception if trying to persist a flight with a null as departure value.
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        if (returnDate != null) {
            if (Date.from(returnDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).toInstant().truncatedTo(ChronoUnit.DAYS)
                    .isBefore(departureDate.toInstant().truncatedTo(ChronoUnit.DAYS)))
                throw new IllegalArgumentException("Return date should not be before departure date.");
            this.returnDate = Date.from(returnDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else
            this.returnDate = null; //Will throw a persistence exception if trying to persist a flight with a null as departure value.
    }

    public Flight getOutFlight() {
        return outFlight;
    }

    public void setOutFlight(Flight outFlight) {
        if (outFlight != null) {
//            System.out.println("-----");
//            System.out.println(outFlight.getDeparture().toInstant());
//            System.out.println(departureDate.toInstant());
//            System.out.println(outFlight.getDeparture().toInstant().truncatedTo(ChronoUnit.DAYS));
//            System.out.println(departureDate.toInstant().atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS));
            if (outFlight.getDeparture().toInstant().atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS)
                    .isAfter(departureDate.toInstant().atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS)))
                throw new IllegalArgumentException("Outgoing flight should not be after start of the trip.");
            this.outFlight = outFlight;
        } else
            this.outFlight = null; //Will throw a persistence exception if trying to persist a flight with a null as departure value.
    }

    public Flight getReturnFlight() {
        return returnFlight;
    }

    public void setReturnFlight(Flight returnFlight) {
        if (returnFlight != null) {
            if (returnFlight.getDeparture().toInstant().truncatedTo(ChronoUnit.DAYS)
                    .isBefore(returnDate.toInstant().truncatedTo(ChronoUnit.DAYS)))
                throw new IllegalArgumentException("Return flight should not be before end of the trip.");
            // TODO: departure van return flight moet destination van out flight zijn.
            this.returnFlight = returnFlight;
        } else {
            this.returnFlight = null;
        }
    }

    public double getTripDayPrice() {
        return tripDayPrice;
    }

    public void setTripDayPrice(double price) {
        if (price < 0.0)
            throw new IllegalArgumentException("A flight should have a price >= 0");
        this.tripDayPrice = BigDecimal.valueOf(price).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
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

    public double getTripPrice() {
        double price = 0.0;
        price += getOutFlight().getPrice();
        if (getReturnFlight() != null)
            price += getReturnFlight().getPrice();
        price += getTripDayPrice() * getDurationOfTripInDays();
        return BigDecimal.valueOf(price).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public int getDurationOfTripInDays() {
        // http://stackoverflow.com/questions/20165564/calculating-days-between-two-dates-with-in-java
        Calendar dayOne = Calendar.getInstance();
        dayOne.setTime(getDepartureDate());
        Calendar dayTwo = Calendar.getInstance();
        dayTwo.setTime(getReturnDate());

        if (dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR)) {
            return Math.abs(dayOne.get(Calendar.DAY_OF_YEAR) - dayTwo.get(Calendar.DAY_OF_YEAR));
        } else {
            if (dayTwo.get(Calendar.YEAR) > dayOne.get(Calendar.YEAR)) {
                //swap them
                Calendar temp = dayOne;
                dayOne = dayTwo;
                dayTwo = temp;
            }
            int extraDays = 0;

            while (dayOne.get(Calendar.YEAR) > dayTwo.get(Calendar.YEAR)) {
                dayOne.add(Calendar.YEAR, -1);
                // getActualMaximum() important for leap years
                extraDays += dayOne.getActualMaximum(Calendar.DAY_OF_YEAR);
            }

            return extraDays - dayTwo.get(Calendar.DAY_OF_YEAR) + dayOne.get(Calendar.DAY_OF_YEAR);
        }
    }
}
