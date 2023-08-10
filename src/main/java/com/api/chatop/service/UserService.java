package com.api.chatop.service;

import com.api.chatop.model.User;
import com.api.chatop.model.Role;
import com.api.chatop.repository.UserRepository;
import com.api.chatop.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    public String defaultRole = "ROLE_USER"; // Change role from admin
    public int enabledUser = 1; // By default new user is enabled
    public PasswordEncoder passwordEncoder = passwordEncoder();

    public boolean saveUser(User user, Role role) {
        if (getUserByEmail(user.getEmail()) == null) {
            User newUser = userRepository.save(userEncrypted(user));
            roleRepository.save(customRole(newUser, role));
            return true;
        }
        return false;
    }

    private Role customRole(User user, Role role) {
        role.setUser(user);
        role.setAuthority(defaultRole);
        role.setEnabled(enabledUser);

        return role;
    }

    private User userEncrypted(User user) {
        user.setEmail(user.getEmail());
        user.setName(user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated_at(OffsetDateTime.now());
        user.setUpdated_at(OffsetDateTime.now());

        return user;
    }

    public User getUser(Integer id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        }
        return null;
    }

    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}