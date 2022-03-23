package com.dain.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

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
        log.info("chatroomId={}",chatRoom.getId());
        message.setChatRoom(chatRoom);

        chatService.saveChat(message);
        template.convertAndSend("/sub/chat/room/" + roomId,message.toDto());
    }

    @MessageMapping("/chat/message/{roomId}")
    public void message(@DestinationVariable String roomId, ChatMessage chatMessage){
        ChatRoom chatRoom = chatRoomRepository.findByRoomCode(roomId).get();
        chatMessage.setChatRoom(chatRoom);
        template.convertAndSend("/sub/chat/room/" + roomId, chatMessage);
    }
}
