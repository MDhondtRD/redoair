package com.realdolmen.air.controllers;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by JDOAX80 on 14/10/2015.
 */
@Named
@ConversationScoped
public class BookingController implements Serializable {

    public String endBooking()  {
        return "home";
    }
}
