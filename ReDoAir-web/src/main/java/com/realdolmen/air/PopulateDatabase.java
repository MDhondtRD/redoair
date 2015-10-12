package com.realdolmen.air;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.realdolmen.redoair.entities.Airport;
import com.realdolmen.redoair.entities.Flight;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.transaction.Transactional;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

@WebListener
public class PopulateDatabase implements ServletContextListener {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("ServletContextListener destroyed");
        em.clear();
    }

    @Override
    @Transactional
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

            Flight f1 = new Flight("JA0001", em.find(Airport.class, 1), em.find(Airport.class, 2), LocalDateTime.now(), 20, 32.01);
            Flight f2 = new Flight("JA0002", em.find(Airport.class, 3), em.find(Airport.class, 4), LocalDateTime.now(), 20, 32.01);
            Flight f3 = new Flight("JA0003", em.find(Airport.class, 5), em.find(Airport.class, 6), LocalDateTime.now(), 20, 32.01);
            Flight f4 = new Flight("JA0004", em.find(Airport.class, 7), em.find(Airport.class, 8), LocalDateTime.now(), 20, 32.01);
            Flight f5 = new Flight("JA0005", em.find(Airport.class, 9), em.find(Airport.class, 10), LocalDateTime.now(), 20, 32.01);
            em.persist(f1); em.persist(f2); em.persist(f3); em.persist(f4); em.persist(f5);
            em.flush();



        } catch (Exception e) {
            e.printStackTrace();
        }




    }

}
