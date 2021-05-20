package com.workshopLab.workshopLab.Service;


import com.workshopLab.workshopLab.model.Role;
import com.workshopLab.workshopLab.model.User;
import com.workshopLab.workshopLab.repository.RoleRepository;
import com.workshopLab.workshopLab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userEntityRepository;
    @Autowired
    private RoleRepository roleEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User userEntity) {
        Role userRole = roleEntityRepository.findByName("ROLE_USER");
        userEntity.setRole(userRole);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userEntityRepository.save(userEntity);
    }

    public User findByLogin(String email) {
        return userEntityRepository.findByEmail(email);
    }

    public User findByLoginAndPassword(String login, String password) {
        User userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }
}