package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.Flight;
import org.junit.Test;

public class FlightPersistenceTest extends DataPersistenceTest {

    @Test
    public void persistenceTest(){
        Flight f = new Flight(321);
        entityManager().persist(f);
        assertNotNull(f.getId());
    }
}
