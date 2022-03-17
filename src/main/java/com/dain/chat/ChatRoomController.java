package com.dain.chat;

import com.dain.principal.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomRepository chatRoomRepository;

    private final ChatService chatService;

    @GetMapping("/chat/rooms")
    public String rooms(Model model){
        model.addAttribute("rooms",chatService.findAllRoom());
        return "/chat/rooms";
    }

    @GetMapping("/chat/room")
    public void getRoom(@PathVariable String roomCode, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){

        log.info("# get Chat Room, roomID : " + roomCode);
        ChatRoom room = chatService.findRoom(roomCode);
        model.addAttribute("room", room);
        model.addAttribute("userDetails",userDetails);
    }

    @GetMapping("/chat/new")
    public String make(Model model){
        ChatRoomForm form = new ChatRoomForm();
        model.addAttribute("form",form);
        return "/chat/newRoom";
    }

    @PostMapping("/room/new")
    public String makeRoom(ChatRoomForm form){
        chatService.createChatRoom(form);
        return "redirect:/chat/rooms";
    }

}
