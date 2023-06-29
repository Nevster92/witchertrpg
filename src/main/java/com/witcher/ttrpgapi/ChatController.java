package com.witcher.ttrpgapi;

import model.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class ChatController {

    // az endpoint /app/hello mert a configben app van
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    @CrossOrigin()
    public Greeting greet(HelloMessage message, Principal principal) throws InterruptedException {
        System.out.println("hopp egy üzi");
        return new Greeting(principal.getName()+": " +
                HtmlUtils.htmlEscape(message.getName()));
    }

}
