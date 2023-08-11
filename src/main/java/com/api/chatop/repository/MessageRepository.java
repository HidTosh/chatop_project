package com.api.chatop.repository;

import com.api.chatop.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository  extends JpaRepository<Message, Integer> {
    @Modifying
    @Query(value = "DELETE FROM rental.messages WHERE rental_id = :rentalId", nativeQuery = true)
    void deleteByRentalId(Integer rentalId);
}
