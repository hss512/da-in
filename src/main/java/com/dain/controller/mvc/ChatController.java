package com.dain.controller.mvc;

import com.dain.principal.UserDetailsImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class ChatController {
    @GetMapping("/chat")
    public String chattingForm(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("chatting 들어옴===============");
        model.addAttribute("nickname",userDetails.returnProfile().getNickname());
        return "chat";
    }
}
