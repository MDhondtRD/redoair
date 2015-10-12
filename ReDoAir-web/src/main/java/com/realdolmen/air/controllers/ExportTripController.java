package com.realdolmen.air.controllers;

import com.realdolmen.redoair.jaxb.TripMarshaller;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by JDOAX80 on 12/10/2015.
 */
@Named
@RequestScoped
public class ExportTripController {

    @Inject
    private TripMarshaller tripMarshaller;

    public void exportAllTripsToXml() {
       ExternalContext externalContext =  FacesContext.getCurrentInstance().getExternalContext();



    }

}
