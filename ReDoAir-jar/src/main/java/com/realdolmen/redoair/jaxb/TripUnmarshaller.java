package com.realdolmen.redoair.jaxb;

import com.realdolmen.redoair.ejb.AirportRepository;
import com.realdolmen.redoair.ejb.TripRepository;
import com.realdolmen.redoair.entities.Airport;
import com.realdolmen.redoair.entities.Flight;
import com.realdolmen.redoair.entities.Trip;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by JDOAX80 on 11/10/2015.
 */
@Stateless
@LocalBean
public class TripUnmarshaller {

    @EJB
    private TripRepository tripRepository;
    @EJB
    private AirportRepository airportRepository;

    public void unmarshal(File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TripsFormat.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            TripsFormat tripsFormat = (TripsFormat) jaxbUnmarshaller.unmarshal(file);
            for (TripFormat tripFormat : tripsFormat.getTrips()) {
                Airport departureAirportForOutFlight = airportRepository.getAirportByName(tripFormat.getOutFlight().getDepartureAirport());
                Airport destinationAirportForOutFlight = airportRepository.getAirportByName(tripFormat.getOutFlight().getDestinationAirport());
                Flight outFlight = new Flight(tripFormat.getOutFlight().getCode(), departureAirportForOutFlight, destinationAirportForOutFlight, tripFormat.getOutFlight().getDeparture(), tripFormat.getOutFlight().getAvailableSeats(), tripFormat.getOutFlight().getPrice(), tripFormat.getOutFlight().getLengthOfFlight());
                Airport departureAirportForReturnFlight = null;
                Airport destinationAirportForReturnFlight = null;
                if (tripFormat.getReturnFlight() != null) {
                    departureAirportForReturnFlight = airportRepository.getAirportByName(tripFormat.getReturnFlight().getDepartureAirport());
                    destinationAirportForReturnFlight = airportRepository.getAirportByName(tripFormat.getReturnFlight().getDestinationAirport());
                }
                Flight returnFlight = null;
                if(tripFormat.getReturnFlight() != null) {
                    returnFlight = new Flight(tripFormat.getReturnFlight().getCode(), departureAirportForReturnFlight, destinationAirportForReturnFlight, tripFormat.getReturnFlight().getDeparture(), tripFormat.getReturnFlight().getAvailableSeats(), tripFormat.getReturnFlight().getPrice(), tripFormat.getReturnFlight().getLengthOfFlight());
                }
                Trip trip = new Trip(tripFormat.getDepartureDate(), tripFormat.getReturnDate(), outFlight, returnFlight, tripFormat.getTripDayPrice(), tripFormat.getTravelAgency());
                tripRepository.createTrip(trip);
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
