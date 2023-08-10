package com.api.chatop.repository;

import com.api.chatop.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Integer> {

    @Query(value = "SELECT * FROM rental.rentals", nativeQuery = true)
    List<RentalProjection> findAllRentals();

    @Query(value = "SELECT * FROM rental.rentals WHERE id = :selectedID", nativeQuery = true)
    RentalProjection findRentalByID(Integer selectedID);

}

