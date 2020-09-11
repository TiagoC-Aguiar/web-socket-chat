package com.javamaster.websocketproject.controller;

import com.javamaster.websocketproject.model.MessageEntity;
import com.javamaster.websocketproject.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageEntity message) {
        System.out.println("handling send message: " + message + " to: " + to);
        boolean isExists = UserStorage.getInstance().getUsers().contains(to);
        if(isExists) {
            template.convertAndSend("/topic/messages/" + to, message);
        }
    }

}
