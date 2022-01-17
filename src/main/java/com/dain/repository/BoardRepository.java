package com.dain.repository;

import com.dain.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Board findBoardByIdAndMemberId(Long boardId, Long memberId);
}