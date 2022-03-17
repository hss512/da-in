package com.dain.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Log4j2
public class StompChatController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달


    @MessageMapping("/chat/enter")
    public void enter(ChatMessage message){
        log.info(message);
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getChatRoom().getRoomCode(), message);
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessage chatMessage){
        template.convertAndSend("/sub/chat/room/" + chatMessage.getChatRoom().getRoomCode(), chatMessage);
        log.info(chatMessage);
    }
}
