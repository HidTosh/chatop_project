package com.api.chatop.repository;

import com.api.chatop.model.Rental;
import com.api.chatop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
}
