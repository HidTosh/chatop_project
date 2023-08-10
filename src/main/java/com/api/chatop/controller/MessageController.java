package com.api.chatop.controller;

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

    @PostMapping("/messages")
    public Map<String, String> addMessage(
            @RequestBody Map<String, Object> dataMessage,
            Authentication authentication,
            Message message
    ) {
        User user = userService.getUserByEmail(authentication.getName());
        messageService.saveMessage(dataMessage, user, message);

        HashMap<String, String> mapSuccess = new HashMap<>();
        mapSuccess.put("message",  "Message send with success");

        return mapSuccess;
    }
}
