package com.api.chatop.repository;

import com.api.chatop.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository  extends JpaRepository<Message, Integer> {
}
