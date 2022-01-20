package com.dain.controller.mvc;

import com.dain.principal.UserDetailsImpl;
import com.dain.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MainController {

    private final BoardService boardService;

    @GetMapping("/")
    public String main(@AuthenticationPrincipal UserDetailsImpl userDetails){

        return "main";
    }
}
