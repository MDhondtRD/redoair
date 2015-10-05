package com.realdolmen.air.controllers;

import com.realdolmen.redoair.ejb.FlightRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Set;

@Named
@RequestScoped
public class AddFlightsController {

    @Inject
    private FlightRepository repo;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<String> getAllMyCurrentCodes(String username){
        System.out.println("Running..................");
        return repo.getAllCodesFromPartner(username);
    }

}
