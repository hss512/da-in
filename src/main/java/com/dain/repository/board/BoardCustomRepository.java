package com.dain.repository.board;

import com.dain.domain.entity.Board;
import com.dain.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {
    Page<Board> getMembersBoard(Member member, String sort, String gender, String local, String age, String category, Pageable pageable);

    Page<Board> getVisitorsBoard(String sort, String gender, String local, String age, String category, Pageable pageable);
}
