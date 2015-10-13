package com.realdolmen.redoair.jaxb;

import com.realdolmen.redoair.ejb.TripRepository;
import com.realdolmen.redoair.entities.Flight;
import com.realdolmen.redoair.entities.Trip;

import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Created by JDOAX80 on 11/10/2015.
 */
@Stateless
@LocalBean
public class TripMarshaller {

    public void exportAllTripsInDatabaseToXML(OutputStream outputStream, TripRepository tripRepository) {
        try {
            List<Trip> trips = tripRepository.getAllTrips();
            System.out.println(trips.size());
            TripsFormat formattedTrips = new TripsFormat();
            for(Trip trip : trips) {
                FlightFormat outFlight = formatFlight(trip.getOutFlight());
                FlightFormat returnFlight = formatFlight(trip.getReturnFlight());
                TripFormat formattedTrip = formatTrip(trip, outFlight, returnFlight);
                formattedTrips.add(formattedTrip);
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(TripsFormat.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(formattedTrips, outputStream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public FlightFormat formatFlight(Flight flight) {
        FlightFormat formattedFlight = new FlightFormat();
        if(flight != null) {
            formattedFlight.setCode(flight.getCode());
            formattedFlight.setDeparture(flight.getDeparture());
            formattedFlight.setLengthOfFlight(flight.getLengthOfFlight());
            formattedFlight.setDestinationAirport(flight.getDestinationAirport().getName());
            formattedFlight.setDepartureAirport(flight.getDepartureAirport().getName());
            formattedFlight.setPrice(flight.getPrice());
            formattedFlight.setAvailableSeats(flight.getAvailableSeats());
        }
        else {
            formattedFlight = null;
        }
        return formattedFlight;
    }

    public TripFormat formatTrip(Trip trip, FlightFormat outFlight, FlightFormat returnFlight) {
        TripFormat formattedTrip = new TripFormat();
        formattedTrip.setDepartureDate(trip.getDepartureDate());
        formattedTrip.setReturnDate(trip.getReturnDate());
        formattedTrip.setTravelAgency(trip.getTravelAgency());
        formattedTrip.setTripDayPrice(trip.getTripDayPrice());
        formattedTrip.setOutFlight(outFlight);
        formattedTrip.setReturnFlight(returnFlight);
        return formattedTrip;
    }

}
