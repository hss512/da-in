package com.dain.controller.api;

import com.dain.chat.ChatService;
import com.dain.principal.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/chat/room")
public class ChatApiController {

    private final ChatService chatService;

    @GetMapping("/{id}/userCountCheck")
    public ResponseEntity<?> UserCountCheck(String countUser,String userLimit,@PathVariable("id") Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("ChatApiController 호출");
        log.info("countUser={}", countUser);
        log.info("userLimit={}", userLimit);
        log.info("id={}", id);
        Long userId = userDetails.getId();
        ResponseEntity<?> check = chatService.userCountCheck(Integer.parseInt(countUser), Integer.parseInt(userLimit), id,userId);
        log.info("이게중요햐={}",check.getBody());
        return check;
    }

    @GetMapping("/leave/{roomId}/exit")
    public ResponseEntity<?> UserCountMinus(@PathVariable("roomId") String roomCode){
        return chatService.userCountMinus(roomCode);
    }

    @GetMapping("/kick/{roomId}/{chatUserId}/exit")
    public ResponseEntity<?> UserCountRefresh(@PathVariable("roomId") String roomCode,@PathVariable("chatUserId") String userId){
        return chatService.userCountRefresh(roomCode,userId);
    }

    @GetMapping("/allmsg/{roomId}")
    public void loadChatRead(@PathVariable("roomId") String roomCode,Long userId){
        List<Integer> integers = chatService.loadAllChat(roomCode, userId);
        System.out.println("Controller integers = " + integers);
    }
}
