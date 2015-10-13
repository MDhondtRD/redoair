package com.realdolmen.air.controllers;

import com.realdolmen.redoair.ejb.AirportRepository;
import com.realdolmen.redoair.ejb.TripRepository;
import com.realdolmen.redoair.jaxb.TripMarshaller;
import com.realdolmen.redoair.jaxb.TripUnmarshaller;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
    private static final String DEFAULT_FILE_NAME = "marshalledTrips.xml";
    @Inject
    TripUnmarshaller tripUnmarshaller;
    @Inject
    TripMarshaller tripMarshaller;
    @Inject
    TripRepository tripRepository;
    @Inject
    AirportRepository airportRepository;

    public void upload() {
        try {
            tripUnmarshaller.unmarshal(file.getInputStream(), airportRepository, tripRepository);
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

    public void createXml() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType("application/xml");
        externalContext.setResponseCharacterEncoding("UTF-8");
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + DEFAULT_FILE_NAME + "\"");
        try {
            OutputStream output = externalContext.getResponseOutputStream();
            tripMarshaller.exportAllTripsInDatabaseToXML(output, tripRepository);

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            FacesContext.getCurrentInstance().responseComplete();
        }
    }
}
