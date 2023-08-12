package com.api.chatop.service;

import com.api.chatop.dto.RequestMessageDto;
import com.api.chatop.model.Message;
import com.api.chatop.model.User;
import com.api.chatop.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.Map;

@Service
@Transactional
public class MessageService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private MessageRepository messageRepository;

    public void saveMessage(RequestMessageDto requestMessageDto, User user) {
        Message message = new Message();
        message.setUser(user);
        message.setRental(rentalRepository.findById(requestMessageDto.getRental_id()).get());
        message.setMessage(requestMessageDto.getMessage());
        message.setCreated_at(OffsetDateTime.now());
        message.setUpdated_at(OffsetDateTime.now());
        messageRepository.save(message);
    }


}
