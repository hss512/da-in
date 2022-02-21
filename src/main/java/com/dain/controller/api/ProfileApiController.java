package com.dain.controller.api;

import com.dain.principal.UserDetailsImpl;
import com.dain.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api")
public class ProfileApiController {
    private final MemberService memberService;

    @GetMapping("/profile/password/{password}/delete")
    public ResponseEntity<?> passwordSameDelete(@PathVariable("password")String password, @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("패스워드 체크 들어왔어요");
        return memberService.memberDeleteForm(password,userDetails);
    }

    @GetMapping("/profile/member/update/updateMember")
    public ResponseEntity<?> memberUpdate(@AuthenticationPrincipal UserDetailsImpl userDetails, String nickname,String local){
        log.info("update api 호출");
        return memberService.memberUpdate(userDetails.returnProfile().getId(), nickname,local);
    }


    @DeleteMapping("/profile/member/delete/deleteMember")
    public ResponseEntity<?> memberDelete(@AuthenticationPrincipal UserDetailsImpl userDetails){

        return memberService.memberDelete(userDetails.returnProfile());
    }
}
