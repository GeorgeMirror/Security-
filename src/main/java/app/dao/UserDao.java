package app.dao;

import app.model.Role;
import app.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {

    void createUser(User user);
    List<User> getAllUsers();
    User getUserById(long id);
    User getUserByUserName(String username);
    void updateUser(User user);
    void deleteUser(long id);
    Set<Role> setRoles(String...role);

}
