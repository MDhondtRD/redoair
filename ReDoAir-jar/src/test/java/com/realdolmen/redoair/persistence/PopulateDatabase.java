package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.entities.Airport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PopulateDatabase extends DataPersistenceTest{


    public void setup() {
        System.out.println("Populating");
        System.out.println(entityManager().find(Airport.class, 3797).getName());
    }

}
