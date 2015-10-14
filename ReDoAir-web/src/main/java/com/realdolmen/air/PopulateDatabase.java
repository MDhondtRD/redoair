package com.realdolmen.air;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.realdolmen.redoair.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.transaction.Transactional;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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
            System.out.println("Creating and persisting airports from from airports.json");
            String jstring = new Scanner(getClass().getClassLoader().getResourceAsStream("data/airports.json")).useDelimiter("\\A").next();
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray jArray = parser.parse(jstring).getAsJsonArray();

            for (JsonElement obj : jArray)
                em.persist(gson.fromJson(obj, Airport.class));

            em.flush();
            System.out.println("3363 airports persisted");

            System.out.println("Creating and persisting users");
            System.out.println("Creating and persisting partners");
            em.persist(new User("Abelag Aviation", "Abelag", "Aviation", "1EwbrW7te4JQjPjk/JbuzD5H3T5g6CRNrjz7qtnEB1Y=", new Address("Main street 1", "", "Brussels", "1000", "Belgium"), UserType.PARTNER, "info@abelagaviation.com", null));
            em.persist(new User("Brussels Airlines", "Brussels", "Airlines", "1EwbrW7te4JQjPjk/JbuzD5H3T5g6CRNrjz7qtnEB1Y=", new Address("Main street 1", "", "Brussels", "1000", "Belgium"), UserType.PARTNER, "info@brusselsairlines.com", null));
            em.persist(new User("JetairFly", "Jetair", "Fly", "1EwbrW7te4JQjPjk/JbuzD5H3T5g6CRNrjz7qtnEB1Y=", new Address("Main street 1", "", "Brussels", "1000", "Belgium"), UserType.PARTNER, "info@jetairfly.com", null));
            em.persist(new User("Thomas Cook airlines", "Thomas", "Cook", "1EwbrW7te4JQjPjk/JbuzD5H3T5g6CRNrjz7qtnEB1Y=", new Address("Main street 1", "", "Brussels", "1000", "Belgium"), UserType.PARTNER, "info@thomascookairlines.com", null));
            em.persist(new User("Tnt Airlines", "TNT", "Airlines", "1EwbrW7te4JQjPjk/JbuzD5H3T5g6CRNrjz7qtnEB1Y=", new Address("Main street 1", "", "Brussels", "1000", "Belgium"), UserType.PARTNER, "info@tntairlines.com", null));
            em.persist(new User("Vizion Air", "Vizion", "Air", "1EwbrW7te4JQjPjk/JbuzD5H3T5g6CRNrjz7qtnEB1Y=", new Address("Main street 1", "", "Brussels", "1000", "Belgium"), UserType.PARTNER, "info@vizionair.com", null));
            em.persist(new User("Vlm Airlines", "VLM", "Airlines", "1EwbrW7te4JQjPjk/JbuzD5H3T5g6CRNrjz7qtnEB1Y=", new Address("Main street 1", "", "Brussels", "1000", "Belgium"), UserType.PARTNER, "info@vlmairlines.com", null));
            em.persist(new UserRoles("info@abelagaviation.com", "partner"));
            em.persist(new UserRoles("info@brusselsairlines.com", "partner"));
            em.persist(new UserRoles("info@jetairfly.com", "partner"));
            em.persist(new UserRoles("info@thomascookairlines.com", "partner"));
            em.persist(new UserRoles("info@tntairlines.com", "partner"));
            em.persist(new UserRoles("info@vizionair.com", "partner"));
            em.persist(new UserRoles("info@vlmairlines.com", "partner"));
            em.flush();
            System.out.println("7 partners persisted");

            System.out.println("Creating and persisting employees");
            em.persist(new User("De Rudy", "De", "Rudy", "1EwbrW7te4JQjPjk/JbuzD5H3T5g6CRNrjz7qtnEB1Y=", new Address("Main street 1", "", "Brussels", "1000", "Belgium"), UserType.EMPLOYEE, "de@rudy.com", null));
            em.persist(new User("Onze Jef", "Onze", "Jef", "1EwbrW7te4JQjPjk/JbuzD5H3T5g6CRNrjz7qtnEB1Y=", new Address("Main street 1", "", "Brussels", "1000", "Belgium"), UserType.EMPLOYEE, "onze@jef.com", null));
            em.persist(new User("Jantje", "zJQZX8N2g73S9FmIPAiHTaqu8I/gl+5Te9UREeYsqYY=", "jan@flan.com"));
            em.persist(new UserRoles("de@rudy.com", "employee"));
            em.persist(new UserRoles("onze@jef.com", "employee"));
            em.persist(new UserRoles("jan@flan.com", "partner"));
            em.flush();
            System.out.println("3 employees persisted");

            System.out.println("Creating and persisting customers");
            em.persist(new User("MaartenDhondt", "Maarten", "Dhondt", "1EwbrW7te4JQjPjk/JbuzD5H3T5g6CRNrjz7qtnEB1Y=", new Address("Main street 1", "", "Brussels", "1000", "Belgium"), UserType.CUSTOMER, "maarten@dhondt.com", null));
            em.persist(new User("JornDevolder", "Jorn", "Devolder", "1EwbrW7te4JQjPjk/JbuzD5H3T5g6CRNrjz7qtnEB1Y=", new Address("Main street 1", "", "Brussels", "1000", "Belgium"), UserType.CUSTOMER, "jorn@devolder.com", null));
            em.flush();
            System.out.println("2 customers persisted");
            System.out.println("11 users persisted");

            System.out.println("Creating and persisting flights");
            List<Flight> flights = new ArrayList<Flight>();
            LocalDateTime ldt = LocalDateTime.now().minusMonths(1);
            for (int i = 1; i <= 750; i++) {
                int a1 = 0, a2 = 0;
                while (a1 == 0 || a2 == 0 || a1 == a2) {
                    a1 = (int) (1 + Math.random() * 3350);
                    a2 = (int) (1 + Math.random() * 3350);
                }
                Flight f = new Flight("AA" + (1000 + i), em.find(Airport.class, a1), em.find(Airport.class, a2), ldt.plusDays(i / 7), (int) (1 + Math.random() * 50), Math.random() * 1000.00);
                em.persist(f);
                flights.add(f);
            }
            em.flush();
            System.out.println("750 flights persisted");

            System.out.println("Creating and persisting trips");
            for (int i = 1; i <= 10; i++) {
                List<Flight> outFlights = new ArrayList<Flight>();
                List<Flight> returnFlights = new ArrayList<Flight>();
                Flight outFlight = null;
                Flight returnFlight = null;
                while (outFlights.size() == 0 || returnFlights.size() == 0) {
                    int a = (int) (1 + Math.random() * 3350);
                    outFlights = em.createQuery("SELECT f FROM Flight f WHERE f.destinationAirport.id = :random", Flight.class).setParameter("random", a).getResultList();
                    if (outFlights.size() > 0) {
                        outFlight = outFlights.get(new Random().nextInt(outFlights.size()));
                        returnFlights = em.createQuery("SELECT f FROM Flight f WHERE f.departureAirport.id = :random AND f.departure > :outDate", Flight.class)
                                .setParameter("random", a).setParameter("outDate", outFlight.getDeparture()).getResultList();
                    }
                }
                returnFlight = returnFlights.get(new Random().nextInt(returnFlights.size()));
                em.persist(new Trip(outFlight.getDeparture().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), returnFlight.getDeparture().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), outFlight, returnFlight, Math.random() * 250, "RD Travel"));
            }
            em.flush();
            System.out.println("25 trips persisted");


//            Flight f1 = new Flight("JA0001", em.find(Airport.class, 1), em.find(Airport.class, 2), LocalDateTime.now(), 20, 32.01);
//            Flight f2 = new Flight("JA0002", em.find(Airport.class, 3), em.find(Airport.class, 4), LocalDateTime.now().plusDays(2), 20, 32.01);
//            Flight f3 = new Flight("JA0003", em.find(Airport.class, 5), em.find(Airport.class, 6), LocalDateTime.now().plusDays(4), 20, 32.01);
//            Flight f4 = new Flight("JA0004", em.find(Airport.class, 2), em.find(Airport.class, 7), LocalDateTime.now().plusDays(6), 20, 32.01);
//            Flight f5 = new Flight("JA0005", em.find(Airport.class, 4), em.find(Airport.class, 8), LocalDateTime.now().plusDays(8), 20, 32.01);
//            em.persist(f1); em.persist(f2); em.persist(f3); em.persist(f4); em.persist(f5);
//            em.flush();

//            User u1 = new User("JetAir", "Maarten", "Dhondt","1EwbrW7te4JQjPjk/JbuzD5H3T5g6CRNrjz7qtnEB1Y=", new Address("Croix ou Pile 1","","Saint-Sauveur","7912","Belgium"), UserType.PARTNER, "info@jetair.com", new Date());
//            User u2 = new User("Jan", "Jan", "Peeters", "1EwbrW7te4JQjPjk/JbuzD5H3T5g6CRNrjz7qtnEB1Y=", new Address("Croix ou Pile 1", "", "Saint-Sauveur", "7912", "Belgium"), UserType.EMPLOYEE, "emp@redoair.com", new Date());
//            UserRoles ur1 = new UserRoles("info@jetair.com", "partner");
//            UserRoles ur2 = new UserRoles("emp@redoair.com", "employee");
//            em.persist(u1); em.persist(u2);
//            em.persist(ur1); em.persist(ur2);
//            em.flush();

//            Trip t1 = new Trip(LocalDate.now(), LocalDate.now().plusDays(5), f1, f4, 55.55, "RD Travel");
//            Trip t2 = new Trip(LocalDate.now().plusDays(2), LocalDate.now().plusDays(5), f2, f5, 44.44, "RD Travel");
//            em.persist(t1); em.persist(t2);
//            em.flush();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
