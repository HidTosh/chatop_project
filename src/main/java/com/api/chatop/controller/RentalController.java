package com.api.chatop.controller;

import com.api.chatop.dto.RequestPostRentalDto;
import com.api.chatop.dto.RequestPutRentalDto;
import com.api.chatop.dto.ResponseMessageRentalDto;
import com.api.chatop.dto.ResponseRentalDto;
import com.api.chatop.model.Rental;
import com.api.chatop.model.User;
import com.api.chatop.service.RentalService;
import com.api.chatop.service.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    UserService userService;

    @Autowired
    RentalService rentalService;
    private final ResponseRentalDto responseRentalDto = new ResponseRentalDto();
    private final ResponseMessageRentalDto responseMessageRentalDto = new ResponseMessageRentalDto();

    /* Create rental */
    @PostMapping(
        value ="",
        consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseMessageRentalDto createRental(
            @ModelAttribute RequestPostRentalDto requestPostRentalDto,
            Authentication authentication
    ) {
        User user = userService.getUserByEmail(authentication.getName());
        rentalService.createRental(requestPostRentalDto, user);
        responseMessageRentalDto.setMessage("Rental created !");
        return responseMessageRentalDto;
    }

    /* Update rental */
    @PutMapping(
        value ="/{id}",
        consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseMessageRentalDto updateRental(@ModelAttribute RequestPutRentalDto requestPutRentalDto) {
        rentalService.updateRental(requestPutRentalDto);
        responseMessageRentalDto.setMessage("Rental updated !");
        return responseMessageRentalDto;
    }

    /* delete rental */
    @DeleteMapping(value = "/{id}", produces = { "application/json" })
    public ResponseMessageRentalDto deleteRental(@PathVariable Integer id, Authentication authentication){
        User user = userService.getUserByEmail(authentication.getName());
        String result = rentalService.deleteRental(id, user);
        responseMessageRentalDto.setMessage(result);
        return responseMessageRentalDto;
    }

    /* Get all rentals */
    @GetMapping(value = "", produces = { "application/json" })
    public ResponseRentalDto getAllRentals(){
        responseRentalDto.setRentals(rentalService.getAllRentals());
        return responseRentalDto;
    }

    /*get rental by id */
    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<Rental> getRentalById(@PathVariable Integer id) {
        try {
            Rental rental = rentalService.getRental(id);
            return new ResponseEntity<Rental>(rental, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Rental>(HttpStatus.NOT_FOUND);
        }
    }
}
