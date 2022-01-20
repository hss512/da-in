package com.dain.controller.api;

import com.dain.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/develop")
    public ResponseEntity<?> develop(){
        return ResponseEntity.ok("");
    }
}
