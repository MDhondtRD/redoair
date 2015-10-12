package com.realdolmen.redoair.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by JDOAX80 on 11/10/2015.
 */
public class TripUnmarshaller {

    public void unmarshal() {
        try {
            File file = new File("C:\\file.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(TripFormat.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            TripFormat trip = (TripFormat) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
