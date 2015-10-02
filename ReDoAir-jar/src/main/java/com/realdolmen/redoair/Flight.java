package com.realdolmen.redoair;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by MDNAX30 on 2/10/2015.
 */
@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private int flightNb;

    public Flight(int flightNb) {
        this.flightNb = flightNb;
    }

    public int getFlightNb() {
        return flightNb;
    }

    public void setFlightNb(int flightNb) {
        this.flightNb = flightNb;
    }

    public Flight() {
    }

    public long getId() {
        return id;
    }
}
