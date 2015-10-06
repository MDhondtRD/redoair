package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.entities.Flight;
import com.realdolmen.redoair.entities.Trip;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by MDNAX30 on 6/10/2015.
 */
public class TripPersistenceTest extends DataPersistenceTest {

    @Test
    public void tripCanBePersisted(){
        Flight f = new Flight("MD1991", "Brussels", "London", LocalDateTime.of(2015, 9, 1, 14, 30) ,50, 123.45);
        Trip t = new Trip(LocalDate.of(2015,10,1), LocalDate.of(2015,10,31), f, null, 0.00, "RD Travel");
        entityManager().persist(f);
        entityManager().flush();
        entityManager().persist(t);
    }

}
