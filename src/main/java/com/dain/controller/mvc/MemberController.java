package com.dain.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("member")
public class MemberController {

    @GetMapping("/signup")
    public String singupForm(){

        return "member/signup";
    }
    //커밋용 주석
}
