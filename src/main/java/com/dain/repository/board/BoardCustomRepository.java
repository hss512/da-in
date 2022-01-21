package com.dain.repository.board;

import com.dain.domain.entity.Board;
import com.dain.domain.entity.Member;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {
    PageImpl<Board> getMembersBoard(Member member, String sort, String gender, String local, String age, String category, Pageable pageable);

    PageImpl<Board> getVisitorsBoard(String sort, String gender, String local, String age, String category, Pageable pageable);
}
