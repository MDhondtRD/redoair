package com.realdolmen.redoair.jaxb;

import com.realdolmen.redoair.entities.Flight;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by JDOAX80 on 11/10/2015.
 */
@XmlRootElement
public class TripFormat {


    private Date departureDate;



    private Date returnDate;

    private Flight outFlight;

    private Flight returnFlight;


    private double tripDayPrice;

    private String travelAgency;


}
