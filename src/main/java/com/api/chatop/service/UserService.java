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

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public String defaultRole = "ROLE_USER";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
    public void saveUser(User user, Role role) {
        User newUser = userRepository.save(userEncrypted(user));
        roleRepository.save(customRole(newUser.getId(), role));
    }

    private Role customRole(int userId, Role role) {
        role.setUser_id(userId);
        role.setAuthority(defaultRole); // admin can change the role
        role.setEnabled(1); // activate with account validation
        return role;
    }
    private User userEncrypted(User user) {
        user.setEmail(user.getEmail());
        user.setName(user.getName());
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        user.setCreated_at(currentTimestamp);
        user.setUpdated_at(currentTimestamp);
        return user;
    }
    public User getUser(Integer id) {
        return userRepository.findById(id).get();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).get(0);
    }
}