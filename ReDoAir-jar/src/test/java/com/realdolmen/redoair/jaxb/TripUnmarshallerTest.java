package com.realdolmen.redoair.jaxb;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by JDOAX80 on 12/10/2015.
 */
public class TripUnmarshallerTest extends Assert {

    private TripUnmarshaller tripUnmarshaller = new TripUnmarshaller();
    @Test
    public void xmlFormatCanBeUnmarshalledIntoListOfTrips() {
        File file = new File("C:\\Users\\jdoax80\\Desktop\\tripsFormat.xml");//File to unmarshal, containing 2 trips
        int amountOfTrips = tripUnmarshaller.unmarshal(file);//Probleem met injection van airportrepo(geen managed container voor uw test)
        assertEquals(2, amountOfTrips);
    }
}
