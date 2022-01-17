package com.dain.service;

import com.dain.domain.entity.Board;
import com.dain.domain.entity.Member;
import com.dain.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class BoardService {

    private final BoardRepository boardRepository;

    public void createBoard(Member member, Board board){

        board.addMember(member);

        Board savedBoard = boardRepository.save(board);

        log.info(savedBoard);
    }

    public void updateBoard(Member member, Board board){

        Board findBoard = boardRepository.findBoardByIdAndMemberId(board.getId(), member.getId());

        findBoard.update(board.getId(), board.getTitle(), board.getContent(), board.getLocal(),
                board.getAge(), board.getGender(), board.getMember(), board.getCategory());

        log.info(findBoard);
    }

    public void deleteBoard(Member member, Board board){

        Board findBoard = boardRepository.findBoardByIdAndMemberId(board.getId(), member.getId());

        boardRepository.delete(findBoard);

        log.info(board.getTitle() + " delete success");
    }

}