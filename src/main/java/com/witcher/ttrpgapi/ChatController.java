package com.witcher.ttrpgapi;

import com.witcher.ttrpgapi.pojo.request.AbillityRollReq;
import com.witcher.ttrpgapi.service.ActionService;
import model.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class ChatController {

    private ActionService actionService;
    @Autowired
    public void ActionService(ActionService actionService){
        this.actionService = actionService;
    }


    // subscribe a topic/greetings -re
    // küldeni meg app/hello
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    @CrossOrigin()
    public Greeting greet(HelloMessage message, Principal principal) throws InterruptedException {
        System.out.println("hopp egy üzi");
        return new Greeting(principal.getName()+": " +
                HtmlUtils.htmlEscape(message.getName()));
    }

    @MessageMapping("/{roomId}/room_message")
    @SendTo("/topic/room/{roomId}")
    @CrossOrigin()
    public Greeting roomMessage(@DestinationVariable String roomId, HelloMessage message, Principal principal) throws InterruptedException {
        System.out.println("roomMassage fut");
        System.out.println("roomId: "+roomId);
        System.out.println(message.getName());


        return new Greeting(principal.getName()+": " +
                HtmlUtils.htmlEscape(message.getName()));
    }

    @MessageMapping("/{roomId}/roll_attribute")
    @SendTo("/topic/room/{roomId}")
    @CrossOrigin()
    public String rollAttribute(@DestinationVariable String roomId, AbillityRollReq abillityRollReq, Principal principal) throws InterruptedException {
        System.out.println("roomMassage fut");
        System.out.println("roomId: "+roomId);


        return actionService.rollAbillity(abillityRollReq).getBody().toString();
    }
}
