package com.dain.controller.mvc;

import com.dain.domain.dto.MessageAlarmDTO;
import com.dain.domain.entity.Alarm;
import com.dain.domain.entity.Board;
import com.dain.domain.entity.Member;
import com.dain.repository.MemberRepository;
import com.dain.repository.board.BoardRepository;
import com.dain.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Log4j2
public class AlarmController {

    private final SimpMessagingTemplate template;
    private final BoardRepository boardRepository;
    private final AlarmService alarmService;

    @MessageMapping("/TTT/{boardId}")
    public void ttt(@DestinationVariable("boardId") String boardId,
                    @RequestBody MessageAlarmDTO alarmDTO) throws Exception{

        log.info("Socket Test boardId={}", Long.parseLong(boardId));

        Alarm alarm = alarmService.createAlarm(boardId, alarmDTO);

        int count = alarmService.getAll(alarm.getMember().getId());

        template.convertAndSend("/topic/user/" + alarm.getMember().getId(), count);
    }
}
