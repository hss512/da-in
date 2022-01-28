package com.dain.controller.api;

import com.dain.domain.entity.Board;
import com.dain.exception.ValidateDTO;
import com.dain.principal.UserDetailsImpl;
import com.dain.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/category/{develop}")
    public ResponseEntity<?> develop(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("develop") String category,
                                     @PageableDefault(size = 15) Pageable pageable,
                                     @RequestParam("sort") String sort, @RequestParam("gender") String gender,
                                     @RequestParam("local") String local, @RequestParam("age") String age){

        log.info("category={}", category);
        log.info("sort={}", sort);
        log.info("gender={}", gender);
        log.info("local={}", local);
        log.info("age={}", age);

        if(userDetails != null){
            Page<Board> membersBoard = boardService.getMembersBoard(userDetails.getMember(), sort, gender, local, age, category, pageable);
            for (Board board : membersBoard) {
                log.info("board={}", board.getTitle());
            }
            return new ResponseEntity<>(new ValidateDTO<>(1, "memberBoard", membersBoard), HttpStatus.OK);
        }else{
            Page<Board> visitorsBoard = boardService.getVisitorsBoard(sort, gender, local, age, category, pageable);
            for (Board board : visitorsBoard) {
                log.info("board={}", board.getTitle());
            }
            return new ResponseEntity<>(new ValidateDTO<>(1, "visitorBoard", visitorsBoard), HttpStatus.OK);
        }
    }
}
