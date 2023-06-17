package com.witcher.ttrpgapi;

import model.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ChatController {
//
//    @MessageMapping("/chat.register")
//    @SendTo("/topic/public")
//    public Message register(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor){
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//        return chatMessage;
//    }
//
//
//
//    @MessageMapping("/chat.send")
//    @SendTo("/topic/public")
//    public Message sendMessage(@Payload Message chatMessage){
//        return chatMessage;
//    }


    // az endpoint /app/hello mert a configben app van
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greet(HelloMessage message) throws InterruptedException {
        return new Greeting("Hello, " +
                HtmlUtils.htmlEscape(message.getName()));
    }

}
