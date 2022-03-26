package com.dain.controller.mvc;

import com.dain.domain.dto.ChatDTO;
import com.dain.domain.dto.RequestSocketDTO;
import com.dain.domain.entity.Alarm;
import com.dain.service.AlarmService;
import com.dain.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @MessageMapping("/alarm/{boardId}")
    public void createAlarm(@DestinationVariable("boardId") String boardId,
                    @RequestBody RequestSocketDTO alarmDTO) throws Exception{

        log.info("Socket Test boardId={}", Long.parseLong(boardId));

        Alarm alarm = alarmService.createAlarm(boardId, alarmDTO);

        int count = alarmService.getAll(alarm.getMember().getId());

        template.convertAndSend("/topic/alarm/user/" + alarm.getMember().getId(), count);
    }

    @MessageMapping("/chat/room/{roomId}")
    public void createChat(@DestinationVariable String roomId, RequestSocketDTO chat,
                           Principal principal){

        log.info("userDetails={}", principal.getName());

        ChatDTO chatDTO = chatService.createChat(Long.parseLong(roomId), principal.getName(), chat.getContent() );

        template.convertAndSend("/topic/chat/room/" + Long.parseLong(roomId), chatDTO);
    }

}
