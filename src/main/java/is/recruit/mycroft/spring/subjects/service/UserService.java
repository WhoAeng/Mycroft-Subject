package is.recruit.mycroft.spring.subjects.service;

import is.recruit.mycroft.spring.subjects.model.entity.Role;
import is.recruit.mycroft.spring.subjects.model.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User>getUsers();
    void addFavoriteSeatToUser(String username, String code);
}
