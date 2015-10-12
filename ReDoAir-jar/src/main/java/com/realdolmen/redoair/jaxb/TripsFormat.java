package com.realdolmen.redoair.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDOAX80 on 12/10/2015.
 */
@XmlRootElement(name = "trips")
public class TripsFormat {

    private List<TripFormat> trips;

    public List<TripFormat> getTrips() {
        return trips;
    }

    @XmlElement(name = "trip")
    public void setTrips(List<TripFormat> trips) {
        this.trips = trips;
    }

    public void add(TripFormat trip) {
        if(this.trips == null) {
            this.trips = new ArrayList<>();
        }
        this.trips.add(trip);
    }
}