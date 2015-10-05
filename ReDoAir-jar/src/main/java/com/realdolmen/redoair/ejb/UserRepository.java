package com.realdolmen.redoair.ejb;


import com.realdolmen.redoair.entities.User;
import com.realdolmen.redoair.entities.UserType;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@LocalBean
public class UserRepository implements UserRepositoryInterface {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public List<User> getAllUsersOfType(UserType type) {
        return em.createQuery("SELECT u FROM User u WHERE u.type = :type", User.class).setParameter("type", type).getResultList();
    }

    @Override
    public User getUserById(Integer id) {
        return em.find(User.class, id);
    }

    @Override
    public User getUserByUsername(String username) {
        return em.createQuery("SELECT u FROM User u WHERE u.username = :un", User.class).setParameter("un", username).getSingleResult();
    }

    @Override
    public User getUserByEmail(String email) {
        return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class).setParameter("email", email).getSingleResult();
    }

    @Override
    public void createUser(User user) {
        em.persist(user);
    }

    @Override
    public void deleteUser(User user) {
        em.remove(user);
    }

    public EntityManager getEntityManager(){
        return em;
    }

    public void setEntityManager(EntityManager em){
        this.em = em;
    }
}