package com.dain.controller.mvc;

import com.dain.domain.entity.Alarm;
import com.dain.domain.entity.Board;
import com.dain.domain.entity.Member;
import com.dain.repository.MemberRepository;
import com.dain.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class AlarmController {

    private final SimpMessagingTemplate template;
    private final BoardRepository boardRepository;

/*

    @GetMapping("/alarm/stomp")
    public String stompAlarm(){
        return "/stomp";
    }

    @MessageMapping("/alarm/{fromUserId}/{toUserId}/{content}")
    public void message(@DestinationVariable("fromUserId") Long fromUserId,
                        @DestinationVariable("toUserId") Long toUserId,
                        @DestinationVariable("content") String content){

        Member toMember = memberRepository.findById(toUserId).get();

        messageTemplate.convertAndSend("/sub/" + fromUserId, new Alarm(toMember, content));
    }
*/


    @MessageMapping("/TTT/{boardId}")
    public void ttt(String message, @DestinationVariable("boardId") String boardId) throws Exception{

        log.info("Socket Test boardId={}", Long.parseLong(boardId));

        System.out.println("message = " + message);

        Board board = boardRepository.findById(Long.parseLong(boardId)).get();

        Long memberId = board.getMember().getId();

        template.convertAndSend("/topic/user/" + memberId, message);
    }
}
