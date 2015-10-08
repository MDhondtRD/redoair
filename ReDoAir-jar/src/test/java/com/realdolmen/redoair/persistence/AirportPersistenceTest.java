package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.entities.Airport;
import org.junit.Test;

/**
 * Created by JDOAX80 on 7/10/2015.
 */
public class AirportPersistenceTest extends DataPersistenceTest {

    @Test
    public void airportCanBeCreated() {
        Airport airport = new Airport("London", 51.50722, -0.12750);
        entityManager().persist(airport);
        assertNotNull(airport.getId());
    }

}
