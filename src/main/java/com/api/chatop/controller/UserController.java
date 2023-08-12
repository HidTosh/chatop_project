package com.api.chatop.controller;

import com.api.chatop.dto.ResponseJwtDto;
import com.api.chatop.dto.UserLoginDto;
import com.api.chatop.dto.UserRegisterDto;
import com.api.chatop.model.User;
import com.api.chatop.service.TokenService;
import com.api.chatop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final ResponseJwtDto responseJwtDto = new ResponseJwtDto();
    public UserController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /* login user */
    @PostMapping(value = "/auth/login", produces = { "application/json" })
    public ResponseJwtDto login(@RequestBody UserLoginDto userLoginDto, HttpServletRequest req) {
        String token = tokenService.generateToken(
            userService.userAuthentication(
                userLoginDto.getEmail(),
                userLoginDto.getPassword(),
                req,
                authenticationManager
            )
        );
        responseJwtDto.setToken(token);
        return responseJwtDto;
    }

    /* Create user account and auto connect */
    @PostMapping(value = "/auth/register", produces = { "application/json" })
    public ResponseJwtDto register(@RequestBody UserRegisterDto userRegisterDto, HttpServletRequest req) {
        if (userService.saveUser(userRegisterDto)) {
            String token = tokenService.generateToken(
                userService.userAuthentication(
                    userRegisterDto.getEmail(),
                    userRegisterDto.getPassword(),
                    req,
                    authenticationManager
                )
            );
            responseJwtDto.setToken(token);
            return responseJwtDto;
        }
        responseJwtDto.setToken("error");
        return responseJwtDto;
    }

    /* Get current user (logged) */
    @GetMapping(value = "/auth/me", produces = { "application/json" })
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        try {
            User user = userService.getUserByEmail(authentication.getName());
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    /* Get user by id */
    @GetMapping(value = "/user/{id}", produces = { "application/json" })
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        try {
            User user = userService.getUser(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }
}