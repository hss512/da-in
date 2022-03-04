package com.dain.service;

import com.dain.domain.dto.ReadBoardDTO;
import com.dain.domain.entity.Board;
import com.dain.domain.entity.Category;
import com.dain.domain.entity.Member;
import com.dain.repository.board.BoardRepository;
import com.dain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
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

    public ReadBoardDTO createBoard(Member member, Board board, Long categoryId){

        board.addMember(member);

        Category category = categoryRepository.findCategoryById(categoryId);

        board.addCategory(category);

        Board savedBoard = boardRepository.save(board);

        log.info("savedBoard={}", savedBoard);

        return savedBoard.toReadBoardDTO();
    }

    public ReadBoardDTO read_one_board(Long boardId){

        Board findBoard = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);

        return findBoard.toReadBoardDTO();
    }

    public void updateBoard(Member member, Board board, Long boardId){

        Board findBoard = boardRepository.findBoardByIdAndMemberId(boardId, member.getId());

        findBoard.update(board.getTitle(), board.getContent(), board.getLocal(),
                board.getLtAge(), board.getRtAge(), board.getGender(), board.getMember(), board.getCategory());

        log.info("findBoard={}", findBoard);
    }

    public void deleteBoard(Member member, Board board){

        Board findBoard = boardRepository.findBoardByIdAndMemberId(board.getId(), member.getId());

        boardRepository.delete(findBoard);

        log.info("deletedBoard={}", board.getTitle());
    }


    public Page<Board> getMembersBoard(Member member, String sort, String gender, String local, String age, String category, Pageable pageable) {

        return boardRepository.getMembersBoard(member, sort, gender, local, age, category, pageable);
    }

    public Page<Board> getVisitorsBoard(String sort, String gender, String local, String age, String category, Pageable pageable) {

        return boardRepository.getVisitorsBoard(sort, gender, local, age, category, pageable);
    }

    public ReadBoardDTO getBoard(String boardId) {
        return boardRepository.findById(Long.parseLong(boardId)).get().toReadBoardDTO();
    }
}
