package com.realdolmen.air;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.realdolmen.redoair.entities.Airport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.transaction.Transactional;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

@Transactional
public class PopulateDatabase implements ServletContextListener {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("ServletContextListener destroyed");
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("ServletContextListener started");
        try {
            String jstring = new Scanner(getClass().getClassLoader().getResourceAsStream("data/airports.json")).useDelimiter("\\A").next();
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray jArray = parser.parse(jstring).getAsJsonArray();

            for (JsonElement obj : jArray)
                em.persist(gson.fromJson(obj, Airport.class));

            em.flush();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
