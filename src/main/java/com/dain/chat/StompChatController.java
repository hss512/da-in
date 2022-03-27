package com.dain.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Log4j2
public class StompChatController {

    private final SimpMessagingTemplate template;

    private final ChatService chatService;

    private final ChatRoomRepository chatRoomRepository;

    @MessageMapping("/chat/enter/{roomId}")
    public void enter(@DestinationVariable String roomId, ChatMessage message) {
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        ChatRoom chatRoom = chatRoomRepository.findByRoomCode(roomId).get();
        System.out.println("message.getWriter() = " + message.getWriter());
        System.out.println("chatRoom = " + chatRoom);
        message.setChatRoom(chatRoom);
        message.setChatTime(LocalDateTime.now());
        if(message.getMessage()==message.getWriter()+"님이 채팅방에 참여하였습니다."){
            if (chatService.ifExistSaveEnter(message.getWriter(),chatRoom)){
                chatService.saveChat(message);
            }
        }
        template.convertAndSend("/sub/chat/room/" + roomId,message.toDto());
    }

    @MessageMapping("/chat/message/{roomId}")
    public void message(@DestinationVariable String roomId, ChatMessage chatMessage){
        ChatRoom chatRoom = chatRoomRepository.findByRoomCode(roomId).get();
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setChatTime(LocalDateTime.now());
        chatService.saveChat(chatMessage);
        template.convertAndSend("/sub/chat/room/" + roomId, chatMessage.toDto());
    }

    @MessageMapping("/chat/leave/{roomId}")
    public void leave(@DestinationVariable String roomId,ChatMessage chatMessage){
        log.info("채팅 나가기 들어옴");
        ChatRoom chatRoom = chatRoomRepository.findByRoomCode(roomId).get();
        chatMessage.setMessage(chatMessage.getWriter()+"님이 채팅방을 떠나셨습니다.");
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setChatTime(LocalDateTime.now());
        log.info(chatMessage);
        template.convertAndSend("/sub/chat/leave/room/"+roomId,chatMessage.toDto());
    }
}
