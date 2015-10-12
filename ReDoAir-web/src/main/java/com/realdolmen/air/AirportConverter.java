package com.realdolmen.air;

import com.realdolmen.redoair.ejb.AirportRepository;
import com.realdolmen.redoair.entities.Airport;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@ManagedBean
@RequestScoped
//@FacesConverter("airportConverter")
public class AirportConverter implements Converter {

    @EJB
    private AirportRepository repo;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            return repo.getAirportByName(value);
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(value + " is not a valid Airport Name"), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof Airport) {
            return String.valueOf(((Airport) value).getId());
        } else {
            throw new ConverterException(new FacesMessage(value + " is not a valid Airport"));
        }
    }
}
