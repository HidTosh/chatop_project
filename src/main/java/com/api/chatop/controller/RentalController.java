package com.api.chatop.controller;

import com.api.chatop.model.Rental;
import com.api.chatop.model.User;
import com.api.chatop.repository.RentalProjection;
import com.api.chatop.service.RentalService;
import com.api.chatop.service.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public JSONObject updateRental(@ModelAttribute Rental rental, Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        rentalService.updateRental(rental, user);
        JSONObject rentalSuccess = new JSONObject();
        rentalSuccess.put("message",  "Rental updated !");

        return rentalSuccess;
    }

    /* Get all rentals */
    @GetMapping("")
    public JSONObject getAllRentals(){
        JSONObject rentalsData = new JSONObject();
        rentalsData.put("rentals", rentalService.getAllRentals());

        return rentalsData;
    }

    /*get rental by id */
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
