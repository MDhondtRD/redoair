package com.realdolmen.redoair.ejb;


import com.realdolmen.redoair.entities.User;
import com.realdolmen.redoair.entities.UserType;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Stateless
@LocalBean
public class UserRepository implements UserRepositoryInterface {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAllUsers() {
        List results = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        return results;
    }

    @Override
    public List<User> getAllUsersOfType(UserType type) {
        List results =  em.createQuery("SELECT u FROM User u WHERE u.type = :type", User.class).setParameter("type", type).getResultList();
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
    public void removeUser(User user) {
        em.remove(user);
    }

    @Override
    public boolean validateUser(User userToCheck) {
        String emailOrUsername = userToCheck.getEmail();
        User user;
        if (emailOrUsername.contains("@")) { //checks whether it's a username or email address
            user = getUserByEmail(emailOrUsername);
        } else {
            user = getUserByUsername(emailOrUsername);
        }
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
        String hashedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(unhashedPassword.getBytes("UTF-8"));
            byte[] digestedMessage = md.digest();
            hashedPassword = String.format("%064x", new java.math.BigInteger(1, digestedMessage));
            return hashedPassword;
        } catch (Exception e) {
            if (e.equals(NoSuchAlgorithmException.class)) {
                throw new NoSuchAlgorithmException("Please use a valid hashAlgorithm");
                //PROPPER ERROR MESSAGE COMES HERE
            } else if (e.equals(UnsupportedEncodingException.class)) {
                throw new UnsupportedEncodingException("Please use a proper encoding");
                //PROPPER ERROR MESSAGE COMES HERE
            }
        } finally {
            return hashedPassword;
        }
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
