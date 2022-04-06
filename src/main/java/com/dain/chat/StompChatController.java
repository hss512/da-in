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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

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
        ChatRoom chatRoom = chatRoomRepository.findByRoomCode(roomId).get();
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        message.setChatRoom(chatRoom);
        message.setChatTime(returnTime(LocalDateTime.now()));
        message.setMessageType(MessageType.CHAT);
        message.setChatRoomUserCount(chatRoom.getCountUser());
        System.out.println("message = " + message.getMessage());
        if (chatService.ifExistSaveEnter(message.getWriter(),chatRoom, message.getMessage())){
            chatService.saveChat(message);
        }


        template.convertAndSend("/sub/chat/enter/" + roomId,message.toDto());
    }

    @MessageMapping("/chat/message/{roomId}")
    public void message(@DestinationVariable String roomId, ChatMessage chatMessage){
        ChatRoom chatRoom = chatRoomRepository.findByRoomCode(roomId).get();
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setChatTime(returnTime(LocalDateTime.now()));
        chatMessage.setMessageType(MessageType.CHAT);
        chatMessage.setChatRoomUserCount(chatRoom.getCountUser());
        chatService.saveChat(chatMessage);
        template.convertAndSend("/sub/chat/room/" + roomId, chatMessage.toDto());
    }

    @MessageMapping("/chat/out/{roomId}")
    public void userOut(@DestinationVariable String roomId,ChatMessage chatMessage){
        ChatRoom chatRoom = chatRoomRepository.findByRoomCode(roomId).get();
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setChatTime(returnTime(LocalDateTime.now()));
        chatMessage.setMessageType(MessageType.NOCHAT);
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
        chatMessage.setChatTime(returnTime(LocalDateTime.now()));
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
        dto.setChatTime(returnTime(LocalDateTime.now()));
        template.convertAndSend("/sub/chat/room/" + roomId, dto);
    }
    @MessageMapping("/chat/kick/user/{id}/{roomId}")
    public void kickMessage(@DestinationVariable String id,
                            @DestinationVariable String roomId){

        ChatMessageDto dto = new ChatMessageDto();

        Member member = memberRepository.findById(Long.parseLong(id)).get();
        dto.setMessage(member.getUsername()+"님이 강퇴되셨습니다.");
        dto.setChatTime(returnTime(LocalDateTime.now()));
        template.convertAndSend("/sub/chat/kick/room/" + roomId, dto);
    }


    @MessageMapping("/chat/nochat/{roomId}")
    public void noChat(@DestinationVariable String roomId){
        ChatMessageDto dto =new ChatMessageDto();
        dto.setMessageType(MessageType.NOCHAT);
        template.convertAndSend("/sub/chat/room/"+roomId,dto);
    }

    @MessageMapping("/chat/read/{roomId}")
    public void readChat(@DestinationVariable("roomId") String roomId,ReadDto readDto){
        System.out.println("readDto222 = " + readDto.getMessageId());
        List<Member> members = chatService.readUserCount(Long.parseLong(readDto.getMessageId()),Long.parseLong(readDto.getUserId()));
        ChatRoom chatRoom = chatRoomRepository.findByRoomCode(roomId).get();
        System.out.println("members.size() = " + members.size());
        readDto.setChatUserCount(chatRoom.getCountUser()-members.size()-1);
        System.out.println("readDto = " + readDto.getChatUserCount());
        template.convertAndSend("/sub/chat/room/"+roomId,readDto);
    }


    public String returnTime(LocalDateTime localDateTime){
        String chatTime = localDateTime.toString();
        String chatTime2="";
        chatTime2 += chatTime.substring(6, 7);
        chatTime2 += "월 ";
        chatTime2 += chatTime.substring(8, 10);
        chatTime2 += "일 ";
        chatTime2 += chatTime.substring(11, 13);
        chatTime2 += "시 ";
        chatTime2 += chatTime.substring(14, 16);
        chatTime2 += "분";
        return chatTime2;
    }
}
