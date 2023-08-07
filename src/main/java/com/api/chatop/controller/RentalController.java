package com.api.chatop.controller;

import com.api.chatop.model.Rental;
import com.api.chatop.model.User;
import com.api.chatop.repository.RentalProjection;
import com.api.chatop.service.RentalService;
import com.api.chatop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    UserService userService;

    @Autowired
    RentalService rentalService;

    //Create rental
    @PostMapping("/add")
    public void addRental(@RequestBody Rental rental, Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        rentalService.saveUpdateRental(rental, user, "new");
    }

    @PutMapping("/update")
    public void updateRental(@RequestBody Rental rental, Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        rentalService.saveUpdateRental(rental, user, "update");
    }

    // Get all rentals
    @GetMapping("/all")
    public List<RentalProjection> getAllRentals(){
        return rentalService.getAllRentals();
    }

    // get rental by id
    @GetMapping("/{id}")
    public ResponseEntity<RentalProjection> get(@PathVariable Integer id) {
        try {
            RentalProjection rental = rentalService.getRental(id);
            return new ResponseEntity<RentalProjection>(rental, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<RentalProjection>(HttpStatus.NOT_FOUND);
        }
    }
}
