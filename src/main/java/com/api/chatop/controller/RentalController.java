package com.api.chatop.controller;

import com.api.chatop.model.Rental;
import com.api.chatop.model.User;
import com.api.chatop.service.RentalService;
import com.api.chatop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    UserService userService;

    @Autowired
    RentalService rentalService;

    //Create rental
    @PostMapping("/1")
    public void add(@RequestBody Rental rental, Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        rentalService.saveRental(rental, user);
    }

    // Get all rentals

    // get rental by id

    // update rental




}
