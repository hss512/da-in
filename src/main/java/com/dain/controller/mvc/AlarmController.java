package com.dain.controller.mvc;

import com.dain.domain.entity.Alarm;
import com.dain.domain.entity.Member;
import com.dain.repository.MemberRepository;
import com.dain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AlarmController {

    private final SimpMessagingTemplate messageTemplate;
    private final MemberRepository memberRepository;

    @GetMapping("/alarm/stomp")
    public String stompAlarm(){
        return "/stomp";
    }

    @MessageMapping("/alarm/{fromUserId}/{toUserId}/{content}")
    public void message(@DestinationVariable("fromUserId") Long fromUserId,
                        @DestinationVariable("toUserId") Long toUserId,
                        @DestinationVariable("content") String content){

        Member fromMember = memberRepository.findById(fromUserId).get();
        Member toMember = memberRepository.findById(toUserId).get();

        messageTemplate.convertAndSend("/sub/" + fromUserId, new Alarm(fromMember, toMember, content));
    }

}
