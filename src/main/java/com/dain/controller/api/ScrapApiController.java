package com.dain.controller.api;

import com.dain.principal.UserDetailsImpl;
import com.dain.service.ScrapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class ScrapApiController {

    private final ScrapService scrapService;

    @PostMapping("/scrap/{userId}/{boardId}")
    public ResponseEntity<?> doScrap(@PathVariable("userId") String userId, @PathVariable("boardId") String boardId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails){

        if(userDetails.returnProfile().getId() == Long.parseLong(userId)) {
            int state = scrapService.doScrap(userId, boardId);
            if (state == 1) {
                return ResponseEntity.ok("스크랩 추가");
            } else {
                return ResponseEntity.ok("스크랩 삭제");
            }
        }else{
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }
    }
}
