package com.dain.controller.api;


import com.dain.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api")
public class SignupApiController {

    private final MemberService memberService;

    @GetMapping("/signup/{nickname}/exist") //커밋용 주석
    public ResponseEntity<?> existnickname(@PathVariable("nickname")String nickname){
        return memberService.checkexistnickname(nickname);
    }
}
