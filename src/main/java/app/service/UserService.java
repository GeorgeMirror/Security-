package app.service;

import app.model.Role;
import app.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    void createUser(User user);
    List<User> getAllUsers();
    User getUserById(long id);
    void updateUser(User user);
    void deleteUser(long id);
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    Set<Role> setRoles(String...roles);
}
