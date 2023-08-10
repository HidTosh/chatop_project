package com.api.chatop.controller;

import com.api.chatop.model.Rental;
import com.api.chatop.model.User;
import com.api.chatop.service.RentalService;
import com.api.chatop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    UserService userService;

    @Autowired
    RentalService rentalService;

    /* Create rental */
    @PostMapping("")
    public void addRental(@RequestParam("picture") MultipartFile file, @RequestParam Map<String, String> rentalData, Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        rentalService.createRental(file, rentalData, user);
    }

    /* Update rental */
    @PutMapping("/{id}")
    public Map<String, String> updateRental(@ModelAttribute Rental rental, Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        rentalService.updateRental(rental, user);

        HashMap<String, String> mapSuccess = new HashMap<>();
        mapSuccess.put("message",  "Rental updated !");

        return mapSuccess;
    }

    /* Get all rentals */
    @GetMapping("")
    public Map<String, List<Rental>> getAllRentals(){
        HashMap<String, List<Rental>> rentalsData = new HashMap<>();
        rentalsData.put("rentals", rentalService.getAllRentals());

        return rentalsData;
    }

    /*get rental by id */
    @GetMapping("/{id}")
    public ResponseEntity<Rental> get(@PathVariable Integer id) {
        try {
            Rental rental = rentalService.getRental(id);
            return new ResponseEntity<Rental>(rental, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Rental>(HttpStatus.NOT_FOUND);
        }
    }
}
