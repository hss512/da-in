package com.dain.controller.mvc;

import com.dain.principal.UserDetailsImpl;
import com.dain.service.BoardService;
import com.dain.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
@Log4j2
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;

    @GetMapping("/develop")
    public String developPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){

        if(userDetails == null){
            log.info("visitor");
            return "/board/develop";
        }else{
            log.info("member");
            model.addAttribute("userDetails", userDetails);
            return "/board/develop";
        }
    }

    @GetMapping("/{category}/write")
    public String createBoard(@PathVariable("category") String category, @AuthenticationPrincipal UserDetailsImpl userDetails,
                                         Model model){

        model.addAttribute("userDetails", userDetails.returnProfile().toBoardMemberDTO());
        model.addAttribute("category", categoryService.getCategoryId(category));

        return "board/create";
    }
}
