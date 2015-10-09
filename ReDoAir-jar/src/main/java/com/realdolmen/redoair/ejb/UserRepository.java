package com.realdolmen.redoair.ejb;


import com.realdolmen.redoair.entities.User;
import com.realdolmen.redoair.entities.UserType;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Base64;

@Stateless
@LocalBean
public class UserRepository implements UserRepositoryInterface, Serializable {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAllUsers() {
        List results = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        return results;
    }

    @Override
    public List<User> getAllUsersOfType(UserType type) {
        List results = em.createQuery("SELECT u FROM User u WHERE u.type = :type", User.class).setParameter("type", type).getResultList();
        return results;
    }

    @Override
    public User getUserById(Integer id) {
        return em.find(User.class, id);
    }

    @Override
    public User getUserByUsername(String username) {
        List results = em.createQuery("SELECT u FROM User u WHERE u.username = :un", User.class).setParameter("un", username).getResultList();
        if (!results.isEmpty()) {
            return (User) results.get(0);
        } else {
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        List results = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class).setParameter("email", email).getResultList();
        if (!results.isEmpty()) {
            return (User) results.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void createUser(User user) {
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        em.persist(user);
    }

    @Override
    public void deleteUser(User user) {
        em.remove(user);
    }

    @Override
    public boolean validateUser(User userToCheck) {
        String emailOrUsername = userToCheck.getEmail();
        User user;
        user = getUserByEmail(emailOrUsername);
        if (user == null) {//The email-address/username entered is invalid or the user does not exist in the database
            return false;
        } else {
            return comparePasswords(userToCheck, user.getPassword());
        }
    }

    public boolean comparePasswords(User userToCheck, String hashedPassword) {
        String hashedPasswordFromUserToCheck = hashPassword(userToCheck.getPassword());
        return hashedPassword.equals(hashedPasswordFromUserToCheck);
    }

    public String hashPassword(String unhashedPassword) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available for password hashing", e);
        }
        md.update(unhashedPassword.getBytes());
        return Base64.getEncoder().encodeToString(md.digest());
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
