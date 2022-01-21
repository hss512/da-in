package com.dain.service;

import com.dain.domain.dto.ReadBoardDTO;
import com.dain.domain.entity.Board;
import com.dain.domain.entity.Category;
import com.dain.domain.entity.Member;
import com.dain.repository.board.BoardRepository;
import com.dain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class BoardService {

    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;

    public void createBoard(Member member, Board board, String categoryName){

        board.addMember(member);

        Category category = categoryRepository.findByTitle(categoryName);

        board.addCategory(category);

        Board savedBoard = boardRepository.save(board);

        log.info("savedBoard={}", savedBoard);
    }

    public ReadBoardDTO read_one_board(Long boardId){

        Board findBoard = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);

        return findBoard.toReadBoardDTO();
    }

    public void updateBoard(Member member, Board board, Long boardId){

        Board findBoard = boardRepository.findBoardByIdAndMemberId(boardId, member.getId());

        findBoard.update(board.getTitle(), board.getContent(), board.getLocal(),
                board.getAge(), board.getGender(), board.getMember(), board.getCategory());

        log.info("findBoard={}", findBoard);
    }

    public void deleteBoard(Member member, Board board){

        Board findBoard = boardRepository.findBoardByIdAndMemberId(board.getId(), member.getId());

        boardRepository.delete(findBoard);

        log.info("deletedBoard={}", board.getTitle());
    }


    public PageImpl<Board> getMembersBoard(Member member, String sort, String gender, String local, String age, String category, Pageable pageable) {

        boardRepository.getMembersBoard(member, sort, gender, local, age, category, pageable);

        return null;
    }

    public PageImpl<Board> getVisitorsBoard(String sort, String gender, String local, String age, String category, Pageable pageable) {

        boardRepository.getVisitorsBoard(sort, gender, local, age, category, pageable);

        return null;
    }
}
