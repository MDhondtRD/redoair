package com.realdolmen.redoair.persistence;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.realdolmen.redoair.entities.Airport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Scanner;

public class PopulateDatabase {


    public void setup(EntityManager em) {
        System.out.println("Populating");
        try {
            String jstring = new Scanner(getClass().getClassLoader().getResourceAsStream("airports.json")).useDelimiter("\\A").next();
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray jArray = parser.parse(jstring).getAsJsonArray();


            for (JsonElement obj : jArray) {
                em.persist(gson.fromJson(obj, Airport.class));
            }

            em.flush();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
