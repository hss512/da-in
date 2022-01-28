package com.dain.repository.board;

import com.dain.domain.entity.Board;
import com.dain.domain.entity.Member;
import com.dain.domain.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Objects;

import static com.dain.domain.entity.QBoard.*;
import static com.dain.domain.entity.QLikes.*;
import static com.dain.domain.entity.QMember.*;

@Log4j2
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
            log.info("최신순 쿼리");
        }else if(Objects.equals(sort, "좋아요순")){
            boardList = queryFactory.selectFrom(board)
                    .where(board.category.title.eq(categoryName))
                    .distinct()
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(board.likeCount.desc())
                    .orderBy(board.createdDate.desc())
                    .fetch();
            log.info("좋아요순 쿼리");
        }else{
            boardList = queryFactory.selectFrom(board)
                    .where(board.category.title.eq(categoryName))
                    .leftJoin(likes).on(likes.board.category.title.eq(categoryName))
                    .distinct()
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(likes.board.count().desc())
                    .fetch();
            log.info("일단 더미");
        }
        return new PageImpl<>(boardList, pageable, boardList.size());
    }

}
