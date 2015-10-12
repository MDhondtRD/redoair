package com.realdolmen.redoair.ejb;

import com.realdolmen.redoair.entities.Booking;
import com.realdolmen.redoair.entities.User;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface BookingsRepositoryInterface {

    public List<Booking> getAllBookings();
    public Booking getBookingById(Integer id);
    public List<Booking> getAllBookingsOfUser(User user);
    public void createBooking(Booking booking);
    public void removeBooking(Booking booking);
    public boolean isValidCreditCardNumber(String creditCardNumber);

}
