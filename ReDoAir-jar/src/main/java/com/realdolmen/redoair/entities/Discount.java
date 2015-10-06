package com.realdolmen.redoair.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by JDOAX80 on 6/10/2015.
 */
@Embeddable
public class Discount implements Serializable {

    /**
     * ATTRIBUTES
     */
    private double discountValue;
    private int discountThresholdBasedOnNumberOfSoldSeats;

    /**
     * CONSTRUCTORS
     */

    protected Discount() {
        // required no-argument constructor
        // used by Hibernate
    }

    public Discount(double discountValue, int discountThresholdBasedOnNumberOfSoldSeats) {
        this.discountValue = discountValue;
        this.discountThresholdBasedOnNumberOfSoldSeats = discountThresholdBasedOnNumberOfSoldSeats;
    }

    /**
     * GETTERS & SETTERS
     */

    public double getDiscountValue() {
        return discountValue;
    }

    public int getDiscountThresholdBasedOnNumberOfSoldSeats() {
        return discountThresholdBasedOnNumberOfSoldSeats;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public void setDiscountThresholdBasedOnNumberOfSoldSeats(int discountThresholdBasedOnNumberOfSoldSeats) {
        this.discountThresholdBasedOnNumberOfSoldSeats = discountThresholdBasedOnNumberOfSoldSeats;
    }
}
