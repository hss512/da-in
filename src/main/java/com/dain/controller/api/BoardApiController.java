package com.dain.controller.api;

import com.dain.principal.UserDetailsImpl;
import com.dain.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

        log.info("sort={}", sort);
        log.info("gender={}", gender);
        log.info("local={}", local);
        log.info("age={}", age);

        if(userDetails != null){
            boardService.getMembersBoard(userDetails.getMember(), sort, gender, local, age, category, pageable);
        }else{
            boardService.getVisitorsBoard(sort, gender, local, age, category, pageable);
        }

        return ResponseEntity.ok("");
    }
}
