package com.dain.controller.api;

import com.dain.domain.dto.ChatDTO;
import com.dain.domain.dto.RoomDTO;
import com.dain.domain.entity.Room;
import com.dain.exception.ValidateDTO;
import com.dain.principal.UserDetailsImpl;
import com.dain.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Log4j2
public class ChatApiController {

    private final ChatService chatService;

    @PostMapping("/chat/{replyMemberId}")
    public ResponseEntity<?> createChatRoom(@PathVariable String replyMemberId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){

        int check = chatService.checkChatRoom(Long.parseLong(replyMemberId), userDetails.returnProfile().getId());

        if(check == 0) {
            RoomDTO roomDTO = chatService.createChatRoom(Long.parseLong(replyMemberId), userDetails.returnProfile().getId());

            return new ResponseEntity<>(new ValidateDTO<>(0, "roomDTO", roomDTO), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ValidateDTO<>(1, "이미 있는 채팅방", null), HttpStatus.OK);
        }
    }

    @GetMapping("/chat/room")
    public ResponseEntity<?> getRoomList(@AuthenticationPrincipal UserDetailsImpl userDetails){

        List<RoomDTO> roomList = chatService.getRoomList(userDetails.returnProfile().getId());
        roomList.forEach(roomDTO -> roomDTO.setMyNickname(userDetails.returnProfile().getNickname()));

        return new ResponseEntity<>(new ValidateDTO<>(1, "roomList", roomList), HttpStatus.OK);
    }

    @GetMapping("/chat/room/{roomId}")
    public ResponseEntity<?> getRoom(@PathVariable String roomId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails){
        RoomDTO roomDTO = chatService.getRoom(Long.parseLong(roomId), userDetails.returnProfile().getId());

        chatService.readChat(Long.parseLong(roomId));

        List<ChatDTO> chatList = chatService.getChatList(Long.parseLong(roomId));

        roomDTO.setChatList(chatList);

        return new ResponseEntity<>(new ValidateDTO<>(1, "room", roomDTO), HttpStatus.OK);
    }

    @PostMapping("/chat/room/exit/{roomId}")
    public ResponseEntity<?> exitRoom(@PathVariable String roomId,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails){

        chatService.exitRoom(roomId, userDetails.returnProfile().getId());

        return new ResponseEntity<>("roomExit", HttpStatus.OK);
    }
}
