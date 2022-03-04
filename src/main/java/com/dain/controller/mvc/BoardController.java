package com.dain.controller.mvc;

import com.dain.domain.dto.ReadBoardDTO;
import com.dain.domain.entity.Member;
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

import java.util.Objects;

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

    @GetMapping("/develop/board/{boardId}")
    public String getBoard(@PathVariable("boardId") String boardId, @AuthenticationPrincipal UserDetailsImpl userDetails,
                           Model model){

        log.info("Get/getBoard");

        ReadBoardDTO getBoard = boardService.getBoard(boardId);
        Member member = userDetails.returnProfile();

        if(getBoard.getGender() == null || getBoard.getGender().equals(member.getGender())){
            if(getBoard.getAgeLt() == 0 || (getBoard.getAgeLt() <= member.getAge() && getBoard.getAgeRt() >= member.getAge())){
                if(getBoard.getLocal() == null || getBoard.getLocal().equals(member.getLocal())){
                    model.addAttribute("board", getBoard);
                    model.addAttribute("userDetails", userDetails.returnProfile());

                    return "/board/post";
                }
            }
        }

        return "redirect:/api/board/error";
    }
}
