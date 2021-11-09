package is.recruit.mycroft.spring.subjects.service;

import is.recruit.mycroft.spring.subjects.model.entity.FavoriteSeat;
import is.recruit.mycroft.spring.subjects.model.entity.Role;
import is.recruit.mycroft.spring.subjects.model.entity.User;
import is.recruit.mycroft.spring.subjects.repository.FavoriteSeatRepository;
import is.recruit.mycroft.spring.subjects.repository.RoleRepository;
import is.recruit.mycroft.spring.subjects.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final FavoriteSeatRepository favoriteSeatRepository;


    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRoleName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

//    @Override
//    public Collection<Role> getUserRoles(String username) {
//        return userRepository.findByUsername(username).getRoles();
//    }

    @Override
    public void addFavoriteSeatToUser(String username, String code) {
        User user = userRepository.findByUsername(username);
        FavoriteSeat favoriteSeat = favoriteSeatRepository.findByCode(code);
        user.setFavoriteSeat(favoriteSeat);
        userRepository.save(user);
    }
}
