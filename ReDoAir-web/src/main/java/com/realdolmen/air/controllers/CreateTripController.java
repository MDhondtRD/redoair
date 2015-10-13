package com.realdolmen.air.controllers;

import com.realdolmen.redoair.jaxb.TripMarshaller;
import com.realdolmen.redoair.jaxb.TripUnmarshaller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * Created by JDOAX80 on 12/10/2015.
 */
@Named
@RequestScoped
public class CreateTripController {
    private Part file;
    private String fileContent;
    @Inject
    TripUnmarshaller tripUnmarshaller;
    @Inject
    TripMarshaller tripMarshaller;

    public void upload() {
        try {
            fileContent = new Scanner(file.getInputStream())
                    .useDelimiter("\\A").next();
        } catch (IOException e) {
            // Error handling
        }
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public void readFile() {
        File file = new File("C:\\Users\\jdoax80\\Desktop\\tripsFormat.xml");
        tripUnmarshaller.unmarshal(file);
    }

    public void createXml() {

        //tripMarshaller.exportAllTripsInDatabaseToXML();
    }
}
