package com.dain.controller.mvc;

import com.dain.chat.ChatRoomForm;
import com.dain.chat.ChatRoomRepository;
import com.dain.principal.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomRepository chatRoomRepository;

    @GetMapping("/roomList")
    public String rooms(Model model){
        model.addAttribute("rooms",chatRoomRepository.findAllRoom());
        return "/chat/rooms";
    }

    /*@GetMapping("/rooms/{id}")
    public String room(@PathVariable String id,Model model){
        ChatRoom room = chatRoomRepository.findRoomById(id);
        model.addAttribute("room",room);
        return "/chat/room";
    }*/



    @GetMapping("/newRoom")
    public String makeRoomForm(Model model){
        ChatRoomForm chatRoomForm = new ChatRoomForm();
        model.addAttribute("chatRoomForm",chatRoomForm);
        return "/chat/newroom";
    }

    @PostMapping("/room/new")
    public String makeRoom(ChatRoomForm form){
        chatRoomRepository.createChatRoom(form.getName());
        return "redirect:/roomList";
    }

    @GetMapping("/chat")
    public String chattingForm(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("chatting 들어옴===============");
        model.addAttribute("nickname",userDetails.returnProfile().getNickname());
        return "chat";
    }

}
