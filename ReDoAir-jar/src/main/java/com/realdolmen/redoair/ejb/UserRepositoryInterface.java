package com.realdolmen.redoair.ejb;

import com.realdolmen.redoair.entities.User;
import com.realdolmen.redoair.entities.UserType;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface UserRepositoryInterface {

    public List<User> getAllUsers();
    public List<User> getAllUsersOfType(UserType type);
    public User getUserById(Integer id);
    public User getUserByUsername(String username);
    public User getUserByEmail(String email);
    public void createUser(User user);
    public void deleteUser(User user);

}
