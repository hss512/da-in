package com.dain.controller.mvc;

import com.dain.domain.dto.MessageAlarmDTO;
import com.dain.domain.entity.Alarm;
import com.dain.principal.UserDetailsImpl;
import com.dain.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Log4j2
public class SocketController {

    private final SimpMessagingTemplate template;
    private final AlarmService alarmService;

    @MessageMapping("/alarm/{boardId}")
    public void createAlarm(@DestinationVariable("boardId") String boardId,
                    @RequestBody MessageAlarmDTO alarmDTO) throws Exception{

        log.info("Socket Test boardId={}", Long.parseLong(boardId));

        Alarm alarm = alarmService.createAlarm(boardId, alarmDTO);

        int count = alarmService.getAll(alarm.getMember().getId());

        template.convertAndSend("/topic/user/" + alarm.getMember().getId(), count);
    }

    @MessageMapping("/chat/{memberId}")
    public void createChat(@DestinationVariable("memberId") String memberId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails){


    }

}
