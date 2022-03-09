package com.dain.controller.api;

import com.dain.domain.dto.ReplyDTO;
import com.dain.domain.entity.Reply;
import com.dain.exception.ValidateDTO;
import com.dain.principal.UserDetailsImpl;
import com.dain.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api")
public class ReplyApiController {

    private final ReplyService replyService;

    @PostMapping("/board/{boardId}/user/{userId}")
    public ResponseEntity<?> replyWrite(@PathVariable("boardId") String boardId, @PathVariable("userId") String userId,
                                        @RequestBody ReplyDTO replyDTO, @AuthenticationPrincipal UserDetailsImpl userDetails){

        log.info(replyDTO.getContent());

        if(userDetails.returnProfile().getId().equals(Long.parseLong(userId))) {
            return new ResponseEntity<>(new ValidateDTO<>(1, "responseReply",
                    replyService.replyWrite(boardId, userId, replyDTO)), HttpStatus.OK);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/board/{boardId}/reply")
    public ResponseEntity<?> getReply(@PathVariable("boardId") String boardId, @PageableDefault(size = 10) Pageable pageable){

        Page<Reply> replyList = replyService.getReply(boardId, pageable);

        List<ReplyDTO> result = replyList.stream().map(Reply::toReplyDTO).collect(Collectors.toList());

        return new ResponseEntity<>(new ValidateDTO<>(1, "replyList", result), HttpStatus.OK);
    }
}
