package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.ejb.FlightRepository;
import com.realdolmen.redoair.ejb.TripRepository;
import com.realdolmen.redoair.entities.Airport;
import com.realdolmen.redoair.entities.Flight;
import com.realdolmen.redoair.entities.Trip;
import org.junit.After;
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
    private int numberOfLocations = 9;
    private Airport[] locations = new Airport[numberOfLocations];
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

    @After
    public void after() {
        for (Trip t : repo.getAllTrips())
            repo.removeTrip(t);
        repo.getEntityManager().flush();
        for (Flight f : fRepo.getAllFlights())
            fRepo.removeFlight(f);
        fRepo.getEntityManager().flush();
        populated = false;
        numberOfFutureTrips = 0;
        numberOfFutureNoReturnFlightTrips = 0;
    }

    public void initialiseRepositories(){
        repo = new TripRepository();
        repo.setEntityManager(entityManager());
        fRepo = new FlightRepository();
        fRepo.setEntityManager(entityManager());
        Airport a1 = new Airport("London", "abc", "UK", 0.0, 0.0);
        Airport a2 = new Airport("Paris", "abc", "UK", 0.0, 0.0);
        Airport a3 = new Airport("New York", "abc", "UK", 0.0, 0.0);
        Airport a4 = new Airport("Brussels", "abc", "Belgium", 0.0, 0.0);
        Airport a5 = new Airport("Madrid", "abc", "UK", 0.0, 0.0);
        Airport a6 = new Airport("Berlin", "abc", "UK", 0.0, 0.0);
        Airport a7 = new Airport("Rome", "abc", "UK", 0.0, 0.0);
        Airport a8 = new Airport("Lisbon", "abc", "UK", 0.0, 0.0);
        Airport a9 = new Airport("Athens", "abc", "UK", 0.0, 0.0);
        entityManager().persist(a1);
        entityManager().persist(a2);
        entityManager().persist(a3);
        entityManager().persist(a4);
        entityManager().persist(a5);
        entityManager().persist(a6);
        entityManager().persist(a7);
        entityManager().persist(a8);
        entityManager().persist(a9);
        entityManager().flush();
        locations[0] = a1;
        locations[1] = a2;
        locations[2] = a3;
        locations[3] = a4;
        locations[4] = a5;
        locations[5] = a6;
        locations[6] = a7;
        locations[7] = a8;
        locations[8] = a9;
    }

    public void populateDatabase(){
        // Generate <numberOfCommonTrips> trips (and twice as much flights)
        for (int i = 0; i < numberOfCommonTrips*2; i+=2){
            LocalDateTime ldt1 = LocalDateTime.of(LocalDate.of(2015, 9 + (i/2 / 30), 1 + ((i/2) % 30)), LocalTime.now());
            Flight f1 = new Flight("MD" + (1000 + i), locations[(i/2/factor1)%numberOfLocations], locations[(i/2/factor1+1)%numberOfLocations], ldt1, 15, 399.95);
            LocalDateTime ldt2 = LocalDateTime.of(LocalDate.of(2015, 9 + (((i/2)+1) / 30), 1 + (((i/2)+1) % 30)), LocalTime.now());
            Flight f2 = new Flight("MD" + (1000 + i+1), locations[(i/2/factor1+1)%numberOfLocations], locations[(i/2/factor1)%numberOfLocations], ldt2, 15, 399.95);
            Trip t = new Trip(ldt1.toLocalDate(), ldt2.toLocalDate(), f1, f2, 49.95, "RD Travel");
            fRepo.createFlight(f1); fRepo.createFlight(f2); repo.createTrip(t);
            if (ldt1.isAfter(LocalDateTime.now()))
                numberOfFutureTrips++;
        }

        // Generate <numberOfFlightOnlyTrips> trips (and twice as much flights)
        for (int i = 0; i < numberOfFlightOnlyTrips*2; i+=2){
            LocalDateTime ldt1 = LocalDateTime.of(LocalDate.of(2015, 9 + (i/2 / 30), 1 + ((i/2) % 30)), LocalTime.now());
            Flight f1 = new Flight("MD" + (2000 + i), locations[(i/2/factor2)%numberOfLocations], locations[(i/2/factor2+1)%numberOfLocations], ldt1, 15, 399.95);
            LocalDateTime ldt2 = LocalDateTime.of(LocalDate.of(2015, 9 + (((i/2)+1) / 30), 1 + (((i/2)+1) % 30)), LocalTime.now());
            Flight f2 = new Flight("MD" + (2000 + i+1), locations[(i/2/factor2+1)%numberOfLocations], locations[(i/2/factor2)%numberOfLocations], ldt2, 15, 399.95);
            Trip t = new Trip(ldt1.toLocalDate(), ldt1.toLocalDate(), f1, f2, 0.0, "RD Travel");
            fRepo.createFlight(f1); fRepo.createFlight(f2); repo.createTrip(t);
            if (ldt1.isAfter(LocalDateTime.now()))
                numberOfFutureTrips++;
        }

        // Generate <numberOfCommonTripsWithoutReturnFlight> trips (and as much flights)
        for (int i = 0; i < numberOfCommonTripsWithoutReturnFlight; i++){
            LocalDateTime ldt1 = LocalDateTime.of(LocalDate.of(2015, 9 + (i / 30), 1 + (i % 30)), LocalTime.now());
            Flight f = new Flight("MD" + (3000 + i), locations[(i/factor3)%numberOfLocations], locations[(i/factor3+1)%numberOfLocations], ldt1, 15, 399.95);
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
            Flight f = new Flight("MD" + (4000 + i), locations[(i/factor4)%numberOfLocations], locations[(i/factor4+1)%numberOfLocations], ldt1, 15, 399.95);
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
        int startIndex = repo.getEntityManager().createQuery("SELECT MIN(t.id) FROM Trip t", Integer.class).getSingleResult();
        for (int i = startIndex; i <= numberOfTrips; i++)
            assertNotNull(repo.getTripById(i));
        assertNull(repo.getTripById(numberOfTrips + 1));
    }

    @Test
    public void canFindAllTripsByDestination(){
        for (int i = 0; i < numberOfLocations; i++)
            assertEquals(numberOfTripsByLocation, repo.getAllTripsByDestination(locations[i].getCity()).size());
    }

    @Test
    public void canFindAllFutureTripsByDestination(){
        for (int i = 0; i < numberOfLocations; i++)
            assertEquals(
                    findTripsByDestination(repo.getAllFutureTrips(), locations[i].getCity()),
                    repo.getAllFutureTripsByDestination(locations[i].getCity()).size());
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
                    findTripsByDestination(repo.getAllTripsWithoutReturnFlight(), locations[i].getCity()),
                    repo.getAllTripsWithoutReturnFlightByDestination(locations[i].getCity()).size());
    }

    @Test
    public void canFindAllFutureTripsWithoutReturnFlight(){
        assertEquals(numberOfFutureNoReturnFlightTrips, repo.getAllFutureTripsWithoutReturnFlight().size());
    }

    @Test
    public void canFindAllFutureTripsWithoutReturnFlightByDestination(){
        for (int i = 0; i < numberOfLocations; i++)
            assertEquals(
                    findTripsByDestination(repo.getAllFutureTripsWithoutReturnFlight(), locations[i].getCity()),
                    repo.getAllFutureTripsWithoutReturnFlightByDestination(locations[i].getCity()).size());
    }

    @Test
    public void canFindAllFlightOnlyTrips(){
        assertEquals(numberOfFlightOnlyTrips + numberOfFlightOnlyTripsWithoutReturnFlight, repo.getAllFlightOnlyTrips().size());
    }

    @Test
    public void canFindAllFlightOnlyTripsByDestination(){
        for (int i = 0; i < numberOfLocations; i++)
            assertEquals(
                    findTripsByDestination(repo.getAllFlightOnlyTrips(), locations[i].getCity()),
                    repo.getAllFlightOnlyTripsByDestination(locations[i].getCity()).size());
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
                    findTripsByDestination(repo.getAllFutureFlightOnlyTrips(), locations[i].getCity()),
                    repo.getAllFutureFlightOnlyTripsByDestination(locations[i].getCity()).size());
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
                    findTripsByDestination(repo.getAllFlightOnlyTripsWithoutReturnFlight(), locations[i].getCity()),
                    repo.getAllFlightOnlyTripsWithoutReturnFlightByDestination(locations[i].getCity()).size());
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
                    findTripsByDestination(repo.getAllFutureFlightOnlyTripsWithoutReturnFlight(), locations[i].getCity()),
                    repo.getAllFutureFlightOnlyTripsWithoutReturnFlightByDestination(locations[i].getCity()).size());
    }


    private int findTripsByDestination(Collection<Trip> trips, String destination){
        int c = 0;
        for (Trip t : trips)
            if (t.getOutFlight().getDestinationCity().equals(destination))
                c++;
        return c;
    }

}
