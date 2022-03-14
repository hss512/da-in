package com.dain.controller.api;

import com.dain.domain.dto.ReplyDTO;
import com.dain.domain.entity.Reply;
import com.dain.exception.ValidateDTO;
import com.dain.principal.UserDetailsImpl;
import com.dain.service.AlarmService;
import com.dain.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final AlarmService alarmService;

    @PostMapping("/board/{boardId}/user/{userId}")
    public ResponseEntity<?> replyWrite(@PathVariable("boardId") String boardId, @PathVariable("userId") String userId,
                                        @RequestBody ReplyDTO replyDTO, @AuthenticationPrincipal UserDetailsImpl userDetails){

        log.info(replyDTO.getContent());

        if(userDetails.returnProfile().getId().equals(Long.parseLong(userId))) {

            alarmService.message(replyDTO, Long.parseLong(boardId), Long.parseLong(userId));

            return new ResponseEntity<>(new ValidateDTO<>(1, "responseReply",
                    replyService.replyWrite(boardId, userId, replyDTO)), HttpStatus.OK);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/board/{boardId}/reply")
    public ResponseEntity<?> getReply(@PathVariable("boardId") String boardId,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable){

        Page<ReplyDTO> replyList = replyService.getReply(boardId, pageable);

        for (ReplyDTO replyDTO : replyList) {
            if(replyDTO.getMemberDTO().getId().equals(userDetails.returnProfile().getId())){
                replyDTO.setEqual(1);
            }else replyDTO.setEqual(0);
        }

        return new ResponseEntity<>(new ValidateDTO<>(1, "replyList", replyList), HttpStatus.OK);
    }

    @DeleteMapping("/reply/{replyId}/member/{memberId}")
    public int deleteReply(@PathVariable("replyId") String replyId,
                                         @PathVariable("memberId") String memberId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){

        if(userDetails.returnProfile().getId() != Long.parseLong(memberId)){
            return 0;
        }else{
            return replyService.deleteReply(Long.parseLong(replyId));
        }
    }
}
