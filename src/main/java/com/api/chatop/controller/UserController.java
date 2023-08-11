package com.api.chatop.controller;

import com.api.chatop.model.User;
import com.api.chatop.model.Role;
import com.api.chatop.service.TokenService;
import com.api.chatop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;
    private final TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;
    HashMap<String, String> jwtObject = new HashMap<>();
    public UserController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/auth/login")
    public Map<String, String> login(@RequestBody  Map<String, Object> credentials, HttpServletRequest req) {
        String token = tokenService.generateToken(
            userService.userAuthentication(
                    (String) credentials.get("email"),
                    (String) credentials.get("password"),
                    req,
                    authenticationManager
            )
        );
        jwtObject.put("token",  token);

        return jwtObject;
    }

    @PostMapping("/auth/register")
    public Map<String, String> add(@RequestBody User user, Role role, HttpServletRequest req) {
        String password = user.getPassword();
        boolean newUser = userService.saveUser(user, role);

        if (newUser) {
            String token = tokenService.generateToken(
                    userService.userAuthentication(
                            user.getEmail(),
                            password,
                            req,
                            authenticationManager
                    )
            );
            jwtObject.put("token",  token);

            return jwtObject;
        }
        jwtObject.put("token",  "error");

        return jwtObject;
    }



    /* Get current user */
    @RequestMapping(value = "/auth/me", method = RequestMethod.GET)
    public ResponseEntity<User> getName(Authentication authentication) {
        try {
            User user = userService.getUserByEmail(authentication.getName());
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    /* Get user by id */
    @GetMapping("/user/{id}")
    public ResponseEntity<User> get(@PathVariable Integer id) {
        try {
            User user = userService.getUser(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }
}