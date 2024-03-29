package com.dain.controller.mvc;

import com.dain.domain.dto.ChatDTO;
import com.dain.domain.dto.RequestSocketDTO;
import com.dain.domain.entity.Alarm;
import com.dain.domain.entity.Member;
import com.dain.exception.ValidateDTO;
import com.dain.repository.MemberRepository;
import com.dain.service.AlarmService;
import com.dain.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Log4j2
public class SocketController {

    private final SimpMessagingTemplate template;
    private final AlarmService alarmService;
    private final ChatService chatService;
    private final MemberRepository memberRepository;

    @MessageMapping("/alarm/{boardId}")
    public void createAlarm(@DestinationVariable("boardId") String boardId,
                    @RequestBody RequestSocketDTO alarmDTO) throws Exception{

        log.info("Socket Test boardId={}", Long.parseLong(boardId));

        Alarm alarm = alarmService.createAlarm(boardId, alarmDTO);

        int count = alarmService.getAll(alarm.getMember().getId());

        template.convertAndSend("/topic/alarm/user/" + alarm.getMember().getId(), "alarm/" + count);
    }

    @MessageMapping("/alarm/chat/all/{roomId}")
    public void chatAllAlarm(@DestinationVariable String roomId,Principal principal){
        String username = principal.getName();
        Member member = memberRepository.findByUsername(username).get();
        Long userId = chatService.anotherMember(username, roomId);
        int chatAllAlarm = chatService.getMemberChatList(username, roomId);
        log.info("userId={}", userId);
        log.info("chatAllAlarm={}", chatAllAlarm);
        template.convertAndSend("/topic/alarm/user/" + userId, "chat/" + chatAllAlarm);
    }

    @MessageMapping("/chat/room/{roomId}")
    public void createChat(@DestinationVariable String roomId, RequestSocketDTO chat,
                           Principal principal){

        log.info("userDetails={}", principal.getName());

        ChatDTO chatDTO = chatService.createChat(Long.parseLong(roomId), principal.getName(), chat.getContent());

        template.convertAndSend("/topic/chat/room/" + Long.parseLong(roomId), chatDTO);
    }


    @MessageMapping("/chat/room/{roomId}/enter")
    public void chatEnter(@DestinationVariable String roomId, Principal principal){

        log.info("chatEnter 호출");

        ValidateDTO<String> response = new ValidateDTO<>(10, principal.getName() + " 입장", principal.getName());

        template.convertAndSend("/topic/chat/room/" + roomId, response);
    }

    @MessageMapping("/chat/room/inRoom/{roomId}/{username}")
    public void inRoom(@DestinationVariable String roomId, @DestinationVariable String username){
        log.info("inRoom");
        template.convertAndSend("/topic/chat/inRoom/" + roomId + "/" + username, 1);
    }
}
