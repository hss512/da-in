package com.dain.chat;

import com.dain.domain.entity.Member;
import com.dain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
    private final MemberRepository memberRepository;

    @MessageMapping("/chat/enter/{roomId}")
    public void enter(@DestinationVariable String roomId, ChatMessage message) {
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        ChatRoom chatRoom = chatRoomRepository.findByRoomCode(roomId).get();
        System.out.println("message.getWriter() = " + message.getWriter());
        System.out.println("chatRoom = " + chatRoom);
        message.setChatRoom(chatRoom);
        message.setChatTime(LocalDateTime.now());
        message.setMessageType(MessageType.CHAT);
        log.info("please={}",chatRoom.getCountUser());
        message.setChatRoomUserCount(chatRoom.getCountUser());
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
        chatMessage.setMessageType(MessageType.CHAT);
        chatMessage.setChatRoomUserCount(chatRoom.getCountUser());
        chatService.saveChat(chatMessage);
        template.convertAndSend("/sub/chat/room/" + roomId, chatMessage.toDto());
    }

    @MessageMapping("/chat/out/{roomId}")
    public void userOut(@DestinationVariable String roomId,ChatMessage chatMessage){
        ChatRoom chatRoom = chatRoomRepository.findByRoomCode(roomId).get();
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setChatTime(LocalDateTime.now());
        chatMessage.setMessageType(MessageType.NOCHAT);
        log.info("please={}",chatRoom.getCountUser());
        chatMessage.setChatRoomUserCount(chatRoom.getCountUser());
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
        chatMessage.setChatRoomUserCount(chatRoom.getCountUser());
        chatMessage.setMessageType(MessageType.NOCHAT);
        template.convertAndSend("/sub/chat/leave/room/"+roomId,chatMessage.toDto());
    }


    @MessageMapping("/chat/kick/{id}/{roomId}")
    public void kick(@DestinationVariable String id, @DestinationVariable String roomId){

        ChatMessageDto dto = new ChatMessageDto();

        Member member = memberRepository.findById(Long.parseLong(id)).get();

        dto.setMessageType(MessageType.KICK);
        dto.setMessage(member.getUsername());
        dto.setChatTime(LocalDateTime.now());
        template.convertAndSend("/sub/chat/room/" + roomId, dto);
    }
    @MessageMapping("/chat/kick/user/{id}/{roomId}")
    public void kickMessage(@DestinationVariable String id,
                            @DestinationVariable String roomId){

        ChatMessageDto dto = new ChatMessageDto();

        Member member = memberRepository.findById(Long.parseLong(id)).get();
        dto.setMessage(member.getUsername()+"님이 강퇴되셨습니다.");
        dto.setChatTime(LocalDateTime.now());
        template.convertAndSend("/sub/chat/kick/room/" + roomId, dto);
    }

    @MessageMapping("/chat/nochat/{roomId}")
    public void noChat(@DestinationVariable String roomId){
        ChatMessageDto dto =new ChatMessageDto();
        dto.setMessageType(MessageType.NOCHAT);
        template.convertAndSend("/sub/chat/room/"+roomId,dto);
    }
}
