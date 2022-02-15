package com.dain.controller.api;

import com.dain.principal.UserDetailsImpl;
import com.dain.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api")
public class ProfileApiController {
    private final MemberService memberService;

    @GetMapping("/profile/password/{password}/delete")
    public ResponseEntity<?> passwordSameDelete(@PathVariable("password")String password, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return memberService.memberDeleteForm(password,userDetails);
    }
}
