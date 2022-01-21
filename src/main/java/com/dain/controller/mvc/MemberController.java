package com.dain.controller.mvc;

import com.dain.domain.dto.MemberDto;
import com.dain.service.EmailService;
import com.dain.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j2
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final EmailService emailService;

    @GetMapping("/signup")
    public String singupForm(){

        return "member/signup";
    }//커밋용주석
    @PostMapping("/signup")
    public String singup(@Validated MemberDto dto, BindingResult result){
        log.info("signup postmapping으로 들어왔습니다");

        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                log.info("바로 필드에러가 일어납니다");
                log.error(error.getDefaultMessage());
                log.error(dto.toString());
            }

            throw new ValidationException("회원가입 에러!", (Throwable) errorMap);
        } else {
            try {
                log.info(dto.toString());
                memberService.createUser(dto);
            } catch (Exception e) {
                log.error(e.getMessage());
                log.info("여기서에러가나나요?");
                return "redirect:/member/signup";
            }
            return "redirect:/member/signin";
        }
    }

    @GetMapping("/mail")
    public String email(){

        return "/member/email";
    }


    @PostMapping("/mail")
    @ResponseBody
    public void emailConfirm(String userId)throws Exception{
        log.info("userId={}", userId);
        log.info("post emailConfirm");
        System.out.println("전달 받은 이메일 : "+userId);
        emailService.sendSimpleMessage(userId);
    }

    @PostMapping("/verifyCode")
    @ResponseBody
    public int verifyCode(String code) {

        log.info("Post verifyCode");

        int result = 0;
        System.out.println("code : "+code);
        System.out.println("code match : "+ EmailService.ePw.equals(code));
        if(EmailService.ePw.equals(code)) {
            result =1;
        }
        return result;
    }

}
