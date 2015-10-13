package com.realdolmen.redoair.persistence;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.realdolmen.redoair.ejb.AirportRepository;
import com.realdolmen.redoair.entities.Airport;
import com.realdolmen.redoair.entities.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Scanner;

public class PopulateDatabase {

    public void setup(EntityManager em) {
        System.out.println("Populating");
        try {
            String jstring = new Scanner(getClass().getClassLoader().getResourceAsStream("airports.json")).useDelimiter("\\A").next();
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray jArray = parser.parse(jstring).getAsJsonArray();
            int i = 0;
            for (JsonElement obj : jArray) {
                if(i < 1) {
                    em.persist(gson.fromJson(obj, Airport.class));
                }
                i++;
            }
            System.out.println(i);
            em.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
