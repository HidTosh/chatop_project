package com.api.chatop.controller;

import com.api.chatop.dto.ReponseJwtDto;
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
    private final ReponseJwtDto responseJwtDto = new ReponseJwtDto();
    public UserController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /* log user */
    @PostMapping("/auth/login")
    public ReponseJwtDto login(@RequestBody UserLoginDto userLoginDto, HttpServletRequest req) {
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
    @PostMapping("/auth/register")
    public ReponseJwtDto register(@RequestBody UserRegisterDto userRegisterDto, HttpServletRequest req) {
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
    @RequestMapping(value = "/auth/me", method = RequestMethod.GET)
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        try {
            User user = userService.getUserByEmail(authentication.getName());
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    /* Get user by id */
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        try {
            User user = userService.getUser(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }
}