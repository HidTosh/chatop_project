package com.api.chatop.controller;

import com.api.chatop.dto.RequestMessageDto;
import com.api.chatop.dto.ResponseMessageRentalDto;
import com.api.chatop.model.Message;
import com.api.chatop.model.User;
import com.api.chatop.service.MessageService;
import com.api.chatop.service.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    private final ResponseMessageRentalDto responseMessageRentalDto = new ResponseMessageRentalDto();

    @PostMapping("/messages")
    public ResponseMessageRentalDto addMessage(
            @RequestBody RequestMessageDto requestMessageDto,
            Authentication authentication
    ) {
        User user = userService.getUserByEmail(authentication.getName());
        messageService.saveMessage(requestMessageDto, user);

        responseMessageRentalDto.setMessage("Message send with success");
        return responseMessageRentalDto;
    }
}
