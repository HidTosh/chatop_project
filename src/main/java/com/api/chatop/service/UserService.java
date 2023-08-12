package com.api.chatop.service;

import com.api.chatop.dto.UserRegisterDto;
import com.api.chatop.model.User;
import com.api.chatop.model.Role;
import com.api.chatop.repository.UserRepository;
import com.api.chatop.repository.RoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
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

    public boolean saveUser(UserRegisterDto userRegisterDto) {
        Role role = new Role();
        if (getUserByEmail(userRegisterDto.getEmail()) == null) {
            User newUser = userRepository.save(
                userEncrypted(userRegisterDto)
            );
            roleRepository.save(customRole(newUser, role));
            return true;
        }
        return false;
    }

    public Authentication userAuthentication(
            String email,
            String password,
            HttpServletRequest req,
            AuthenticationManager authenticationManager
    ) {
        UsernamePasswordAuthenticationToken authReq =
                new UsernamePasswordAuthenticationToken(email, password);
        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
        HttpSession session = req.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);
        return auth;
    }

    private Role customRole(User user, Role role) {
        role.setUser(user);
        role.setAuthority(defaultRole);
        role.setEnabled(enabledUser);
        return role;
    }

    private User userEncrypted(UserRegisterDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
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