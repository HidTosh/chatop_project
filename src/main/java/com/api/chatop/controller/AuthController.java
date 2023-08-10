package com.api.chatop.controller;

import com.api.chatop.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import com.api.chatop.service.UserService;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    @Autowired
    UserService userService;
    private final TokenService tokenService;

    @Autowired
    public AuthenticationManager authenticationManager;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody  Map<String, Object> credentials, HttpServletRequest req) {
        String login = credentials.containsKey("login") ?
                (String) credentials.get("login") :
                (String) credentials.get("email");
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
            login,
            (String) credentials.get("password")
        );
        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
        HttpSession session = req.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);

        String token = tokenService.generateToken(auth);

        HashMap<String, String> jwtObject = new HashMap<>();
        jwtObject.put("token",  token);


        return jwtObject;
    }

}