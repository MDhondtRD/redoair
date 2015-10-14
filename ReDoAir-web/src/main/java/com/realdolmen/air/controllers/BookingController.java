package com.realdolmen.air.controllers;

import com.realdolmen.redoair.ejb.BookingsRepository;
import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.Booking;
import com.realdolmen.redoair.entities.Trip;
import com.realdolmen.redoair.entities.User;

import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDOAX80 on 14/10/2015.
 */
@Named
@ConversationScoped
public class BookingController implements Serializable {

    @Inject
    private ChooseTripController chooseTripController;

    @Inject
    private BookingsRepository bookingsRepository;

    @Inject
    private UserRepository userRepository;

    private int numberOfPersons;
    private String creditCardNumber;

    public String doBooking() {
        Trip trip = chooseTripController.getSelectedTrip();
        if(trip != null) {
            System.out.println(trip.getOutFlight().getDestinationCountry());
            return "tripOverview";
        }
        else {
            return "chooseTrip";
        }
    }

    public String validateCreditCard() {
        boolean isValidCreditCard = bookingsRepository.isValidCreditCardNumber(creditCardNumber);
        if(isValidCreditCard) {
            return endBooking();
        }
        else {
            throw new ValidatorException(new FacesMessage(
                    "Invalid credit card number!"));
        }
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String pay() {
        return "bookTrip";
    }

    public String cancel() {
        return "chooseTrip";
    }

    public String endBooking()  {
        User user = userRepository.getUserByEmail("jan@flan.com");
        Trip trip = chooseTripController.getSelectedTrip();
        List<Trip> trips = new ArrayList<>();
        trips.add(trip);
        Booking booking = new Booking(user, numberOfPersons, trips);
        bookingsRepository.createBooking(booking);
        return "home";
    }
}
