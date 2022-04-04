package com.dain.controller.api;

import com.dain.domain.dto.BoardDTO;
import com.dain.domain.dto.ReadBoardDTO;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/{category}")
    public ResponseEntity<?> getBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("category") String category,
                                     @PageableDefault(size = 15) Pageable pageable,
                                     @RequestParam("sort") String sort, @RequestParam("gender") String gender,
                                     @RequestParam("local") String local, @RequestParam("age") String age){

        if(userDetails != null){
            Page<Board> membersBoard = boardService.getMembersBoard(userDetails.returnProfile(), sort, gender, local, age, category, pageable);
            for (Board board : membersBoard) {
                log.info("board={}", board.getTitle());
            }
            List<ReadBoardDTO> result = membersBoard.stream().map(Board::toReadBoardDTO/*, Category::toCategoryDTO*/).collect(Collectors.toList());
            return new ResponseEntity<>(new ValidateDTO<>(1, "memberBoard", result), HttpStatus.OK);
        }else{
            Page<Board> visitorsBoard = boardService.getVisitorsBoard(sort, gender, local, age, category, pageable);
            for (Board board : visitorsBoard) {
                log.info("board={}", board.getTitle());
            }
            List<ReadBoardDTO> result = visitorsBoard.stream().map(Board::toReadBoardDTO/*, Category::toCategoryDTO*/).collect(Collectors.toList());
            return new ResponseEntity<>(new ValidateDTO<>(1, "visitorBoard", result), HttpStatus.OK);
        }
    }

    @PostMapping("/{category}/board/create")
    public ResponseEntity<?> createBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @RequestBody BoardDTO.CreateBoard data,
                                         @PathVariable("category") Long categoryId){


        ReadBoardDTO board = boardService.createBoard(userDetails.returnProfile(), data.toBoard(), categoryId);

        return null;
    }

    @GetMapping("/board/error")
    public String boardError(){

        StringBuilder sb = new StringBuilder();

        sb.append("<script>");
        sb.append("alert('").append("안됩니다").append("');");
        sb.append("history.back();");
        sb.append("</script>");

        return sb.toString();
    }

    @GetMapping("/board/error/login")
    public String boardErrorLogin(){

        StringBuilder sb = new StringBuilder();

        sb.append("<script>");
        sb.append("alert('").append("로그인이 필요합니다").append("');");
        sb.append("history.back();");
        sb.append("</script>");

        return sb.toString();
    }
}
