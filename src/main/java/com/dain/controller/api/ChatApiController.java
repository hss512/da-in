package com.dain.controller.api;

import com.dain.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/chat/room")
public class ChatApiController {

    private final ChatService chatService;

    @GetMapping("/{id}/userCountCheck")
    public ResponseEntity<?> UserCountCheck(String countUser,String userLimit,@PathVariable("id") Long id){
        log.info("ChatApiController 호출");
        log.info("countUser={}", countUser);
        log.info("userLimit={}", userLimit);
        log.info("id={}", id);
        ResponseEntity<?> check = chatService.userCountCheck(Integer.parseInt(countUser), Integer.parseInt(userLimit), id);
        log.info("이게중요햐={}",check.getBody());
        return check;
    }

    @GetMapping("/leave/{roomId}/exit")
    public ResponseEntity<?> UserCountMinus(@PathVariable("roomId") String roomCode){
        return chatService.userCountMinus(roomCode);
    }
}
