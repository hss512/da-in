package com.dain.repository.board;

import com.dain.domain.entity.Board;
import com.dain.domain.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Objects;

import static com.dain.domain.entity.QBoard.*;
import static com.dain.domain.entity.QLikes.*;

public class BoardCustomRepositoryImpl implements BoardCustomRepository{

    private final JPAQueryFactory queryFactory;

    public BoardCustomRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public PageImpl<Board> getMembersBoard(Member member, String sort, String gender, String local, String age, String category, Pageable pageable) {

        return null;
    }

    @Override
    public PageImpl<Board> getVisitorsBoard(String sort, String gender, String local, String age, String categoryName, Pageable pageable) {

        List<Board> boardList;

        if(Objects.equals(sort, "최신순")) {
            boardList = queryFactory.selectFrom(board)
                    .where(board.category.title.eq(categoryName))
                    .distinct()
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(board.createdDate.desc())
                    .fetch();

        }else {
            boardList = queryFactory.selectFrom(board)
                    .where(board.category.title.eq(categoryName))
                    .leftJoin(likes).on(likes.board.category.title.eq(categoryName))
                    .distinct()
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(likes.board.count().desc())
                    .fetch();

        }

        return new PageImpl<>(boardList, pageable, boardList.size());
    }

}
