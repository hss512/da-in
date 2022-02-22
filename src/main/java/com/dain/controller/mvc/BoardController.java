package com.dain.controller.mvc;

import com.dain.principal.UserDetailsImpl;
import com.dain.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class BoardController {

    private BoardService boardService;

    @GetMapping("/develop")
    public String developPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){

        if(userDetails == null){
            log.info("visitor");
            model.addAttribute("userDetails", userDetails);
            return "develop";
        }else{
            log.info("member");
            model.addAttribute("userDetails", userDetails);
            return "develop";
        }
    }
}
