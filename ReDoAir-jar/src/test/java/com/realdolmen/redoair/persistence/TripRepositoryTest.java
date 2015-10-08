package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.ejb.FlightRepository;
import com.realdolmen.redoair.ejb.TripRepository;
import com.realdolmen.redoair.entities.Flight;
import com.realdolmen.redoair.entities.Trip;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

public class TripRepositoryTest extends DataPersistenceTest{

    private TripRepository repo;
    private FlightRepository fRepo;
    private static boolean populated = false;
    private static int numberOfFutureTrips = 0;
    private static int numberOfFutureNoReturnFlightTrips = 0;
    private String[] locations = {"London", "Paris", "New York", "Brussels", "Madrid", "Berlin", "Rome", "Lisbon", "Athens"};
    private int numberOfLocations = 9;
    private int factor1 = 10;
    private int factor2 = 8;
    private int factor3 = 6;
    private int factor4 = 4;
    private int numberOfCommonTrips = factor1 * numberOfLocations ;
    private int numberOfFlightOnlyTrips = factor2 * numberOfLocations;
    private int numberOfCommonTripsWithoutReturnFlight = factor3 * numberOfLocations;
    private int numberOfFlightOnlyTripsWithoutReturnFlight = factor4 * numberOfLocations;
    private int numberOfTrips = numberOfCommonTrips + numberOfFlightOnlyTrips + numberOfCommonTripsWithoutReturnFlight + numberOfFlightOnlyTripsWithoutReturnFlight;
    private int numberOfTripsByLocation = factor1 + factor2 + factor3 + factor4;

    @Before
    public void before() {
        initialiseRepositories();
        if (!populated){
            populateDatabase();
            populated = true;
        }
    }

    public void initialiseRepositories(){
        repo = new TripRepository();
        repo.setEntityManager(entityManager());
        fRepo = new FlightRepository();
        fRepo.setEntityManager(entityManager());
    }

    public void populateDatabase(){
        // Generate <numberOfCommonTrips> trips (and twice as much flights)
        for (int i = 0; i < numberOfCommonTrips*2; i+=2){
            LocalDateTime ldt1 = LocalDateTime.of(LocalDate.of(2015, 9 + (i/2 / 30), 1 + ((i/2) % 30)), LocalTime.now());
            Flight f1 = new Flight("MD" + (1000 + i), locations[(i/2/factor1)%numberOfLocations], locations[(i/2/factor1+1)%numberOfLocations], ldt1, 15, 399.95, "BEL");
            LocalDateTime ldt2 = LocalDateTime.of(LocalDate.of(2015, 9 + (((i/2)+1) / 30), 1 + (((i/2)+1) % 30)), LocalTime.now());
            Flight f2 = new Flight("MD" + (1000 + i+1), locations[(i/2/factor1+1)%numberOfLocations], locations[(i/2/factor1)%numberOfLocations], ldt2, 15, 399.95, "BEL");
            Trip t = new Trip(ldt1.toLocalDate(), ldt2.toLocalDate(), f1, f2, 49.95, "RD Travel");
            fRepo.createFlight(f1); fRepo.createFlight(f2); repo.createTrip(t);
            if (ldt1.isAfter(LocalDateTime.now()))
                numberOfFutureTrips++;
        }

        // Generate <numberOfFlightOnlyTrips> trips (and twice as much flights)
        for (int i = 0; i < numberOfFlightOnlyTrips*2; i+=2){
            LocalDateTime ldt1 = LocalDateTime.of(LocalDate.of(2015, 9 + (i/2 / 30), 1 + ((i/2) % 30)), LocalTime.now());
            Flight f1 = new Flight("MD" + (2000 + i), locations[(i/2/factor2)%numberOfLocations], locations[(i/2/factor2+1)%numberOfLocations], ldt1, 15, 399.95, "BEL");
            LocalDateTime ldt2 = LocalDateTime.of(LocalDate.of(2015, 9 + (((i/2)+1) / 30), 1 + (((i/2)+1) % 30)), LocalTime.now());
            Flight f2 = new Flight("MD" + (2000 + i+1), locations[(i/2/factor2+1)%numberOfLocations], locations[(i/2/factor2)%numberOfLocations], ldt2, 15, 399.95, "BEL");
            Trip t = new Trip(ldt1.toLocalDate(), ldt1.toLocalDate(), f1, f2, 0.0, "RD Travel");
            fRepo.createFlight(f1); fRepo.createFlight(f2); repo.createTrip(t);
            if (ldt1.isAfter(LocalDateTime.now()))
                numberOfFutureTrips++;
        }

        // Generate <numberOfCommonTripsWithoutReturnFlight> trips (and as much flights)
        for (int i = 0; i < numberOfCommonTripsWithoutReturnFlight; i++){
            LocalDateTime ldt1 = LocalDateTime.of(LocalDate.of(2015, 9 + (i / 30), 1 + (i % 30)), LocalTime.now());
            Flight f = new Flight("MD" + (3000 + i), locations[(i/factor3)%numberOfLocations], locations[(i/factor3+1)%numberOfLocations], ldt1, 15, 399.95, "BEL");
            LocalDateTime ldt2 = LocalDateTime.of(LocalDate.of(2015, 9 + ((i+5) / 30), 1 + ((i+5) % 30)), LocalTime.now());
            Trip t = new Trip(ldt1.toLocalDate(), ldt2.toLocalDate(), f, null, 49.95, "RD Travel");
            fRepo.createFlight(f); repo.createTrip(t);
            if (ldt1.isAfter(LocalDateTime.now())) {
                numberOfFutureTrips++;
                numberOfFutureNoReturnFlightTrips++;
            }
        }

        // Generate <numberOfFlightOnlyTripsWithoutReturnFlight> trips (and as much flights)
        for (int i = 0; i < numberOfFlightOnlyTripsWithoutReturnFlight; i++){
            LocalDateTime ldt1 = LocalDateTime.of(LocalDate.of(2015, 9 + (i / 30), 1 + (i % 30)), LocalTime.now());
            Flight f = new Flight("MD" + (4000 + i), locations[(i/factor4)%numberOfLocations], locations[(i/factor4+1)%numberOfLocations], ldt1, 15, 399.95, "BEL");
            Trip t = new Trip(ldt1.toLocalDate(), ldt1.toLocalDate(), f, null, 0.0, "RD Travel");
            fRepo.createFlight(f); repo.createTrip(t);
            if (ldt1.isAfter(LocalDateTime.now())){
                numberOfFutureTrips++;
                numberOfFutureNoReturnFlightTrips++;
            }
        }
        fRepo.getEntityManager().flush();
        repo.getEntityManager().flush();
    }





    @Test
    public void canFindAllTrips(){
        assertEquals(numberOfTrips, repo.getAllTrips().size());
    }

    @Test
    public void canFindAllFutureTrips(){
        assertEquals(numberOfFutureTrips, repo.getAllFutureTrips().size());
    }

    @Test
    public void canFindTripsByID(){
        for (int i = 1; i <= numberOfTrips; i++)
            assertNotNull(repo.getTripById(i));
        assertNull(repo.getTripById(numberOfTrips + 1));
    }

    @Test
    public void canFindAllTripsByDestination(){
        for (int i = 0; i < numberOfLocations; i++)
            assertEquals(numberOfTripsByLocation, repo.getAllTripsByDestination(locations[i]).size());
    }

    @Test
    public void canFindAllFutureTripsByDestination(){
        for (int i = 0; i < numberOfLocations; i++)
            assertEquals(
                    findTripsByDestination(repo.getAllFutureTrips(), locations[i]),
                    repo.getAllFutureTripsByDestination(locations[i]).size());
    }

    @Test
    public void canFindAllTripsWithoutReturnFlight(){
        assertEquals(numberOfCommonTripsWithoutReturnFlight + numberOfFlightOnlyTripsWithoutReturnFlight,
                repo.getAllTripsWithoutReturnFlight().size());
    }

    @Test
    public void canFindAllTripsWithoutReturnFlightByDestination(){
        for (int i = 0; i < numberOfLocations; i++)
            assertEquals(
                    findTripsByDestination(repo.getAllTripsWithoutReturnFlight(), locations[i]),
                    repo.getAllTripsWithoutReturnFlightByDestination(locations[i]).size());
    }

    @Test
    public void canFindAllFutureTripsWithoutReturnFlight(){
        assertEquals(numberOfFutureNoReturnFlightTrips, repo.getAllFutureTripsWithoutReturnFlight().size());
    }

    @Test
    public void canFindAllFutureTripsWithoutReturnFlightByDestination(){
        for (int i = 0; i < numberOfLocations; i++)
            assertEquals(
                    findTripsByDestination(repo.getAllFutureTripsWithoutReturnFlight(), locations[i]),
                    repo.getAllFutureTripsWithoutReturnFlightByDestination(locations[i]).size());
    }

    @Test
    public void canFindAllFlightOnlyTrips(){
        assertEquals(numberOfFlightOnlyTrips + numberOfFlightOnlyTripsWithoutReturnFlight, repo.getAllFlightOnlyTrips().size());
    }

    @Test
    public void canFindAllFlightOnlyTripsByDestination(){
        for (int i = 0; i < numberOfLocations; i++)
            assertEquals(
                    findTripsByDestination(repo.getAllFlightOnlyTrips(), locations[i]),
                    repo.getAllFlightOnlyTripsByDestination(locations[i]).size());
    }

    @Test
    public void canFindFutureFlightOnlyTrips(){
        assertNotNull(repo.getAllFutureFlightOnlyTrips());
        assertTrue(repo.getAllFutureFlightOnlyTrips().size() <= numberOfFutureTrips);
        assertTrue(repo.getAllFutureFlightOnlyTrips().size() <= numberOfFlightOnlyTrips + numberOfFlightOnlyTripsWithoutReturnFlight);
    }

    @Test
    public void canFindAllFutureFlightOnlyTripsByDestination(){
        for (int i = 0; i < numberOfLocations; i++)
            assertEquals(
                    findTripsByDestination(repo.getAllFutureFlightOnlyTrips(), locations[i]),
                    repo.getAllFutureFlightOnlyTripsByDestination(locations[i]).size());
    }

    @Test
    public void canFindAllFlightOnlyTripsWithoutReturnFlight(){
        assertNotNull(repo.getAllFlightOnlyTripsWithoutReturnFlight());
        assertTrue(repo.getAllFlightOnlyTripsWithoutReturnFlight().size() <= numberOfFlightOnlyTripsWithoutReturnFlight);
    }

    @Test
    public void canFindAllFlightOnlyTripsWithoutReturnFlightByDestination(){
        for (int i = 0; i < numberOfLocations; i++)
            assertEquals(
                    findTripsByDestination(repo.getAllFlightOnlyTripsWithoutReturnFlight(), locations[i]),
                    repo.getAllFlightOnlyTripsWithoutReturnFlightByDestination(locations[i]).size());
    }

    @Test
    public void canFindAllFutureFlightOnlyTripsWithoutReturnFlight(){
        assertNotNull(repo.getAllFutureFlightOnlyTripsWithoutReturnFlight());
        assertTrue(repo.getAllFutureFlightOnlyTripsWithoutReturnFlight().size() <= numberOfFutureTrips);
        assertTrue(repo.getAllFutureFlightOnlyTripsWithoutReturnFlight().size() <= numberOfFlightOnlyTripsWithoutReturnFlight);
    }

    @Test
    public void canFindAllFutureFlightOnlyTripsWithoutReturnFlightByDestination(){
        for (int i = 0; i < numberOfLocations; i++)
            assertEquals(
                    findTripsByDestination(repo.getAllFutureFlightOnlyTripsWithoutReturnFlight(), locations[i]),
                    repo.getAllFutureFlightOnlyTripsWithoutReturnFlightByDestination(locations[i]).size());
    }


    @Test
    public void canFindAllFutureTripsByCountryCode() {
        assertEquals(numberOfFutureTrips, repo.getAllFutureTripsByCountryCode("BEL").size());
    }


    private int findTripsByDestination(Collection<Trip> trips, String destination){
        int c = 0;
        for (Trip t : trips)
            if (t.getOutFlight().getDestinationCity().equals(destination))
                c++;
        return c;
    }

}
