package com.dain.repository.board;

import com.dain.domain.entity.Board;
import com.dain.domain.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

import static com.dain.domain.entity.QBoard.board;

@Log4j2
public class BoardCustomRepositoryImpl implements BoardCustomRepository{

    private final JPAQueryFactory queryFactory;

    public BoardCustomRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public PageImpl<Board> getMembersBoard(Member member, String sort, String gender, String local, String age, String category, Pageable pageable) {

        List<Board> boardList;

        if(Objects.equals(sort, "최신순")){
            if(Objects.equals(gender, "전체")){
                if(Objects.equals(local, "전체")){
                    if(Objects.equals(age, "전체")){
                        boardList = queryFactory.selectFrom(board)
                                .where(board.category.title.eq(category))
                                .distinct()
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(board.createdDate.desc())
                                .fetch();
                    }else{
                        boardList = queryFactory.selectFrom(board)
                                .where(board.category.title.eq(category))
                                .where(board.ageLt.goe(member.getAge()).and(board.ageRt.loe(member.getAge())))
                                .distinct()
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(board.createdDate.desc())
                                .fetch();
                    }
                }else{
                    if(Objects.equals(age, "전체")){
                        boardList = queryFactory.selectFrom(board)
                                .where(board.category.title.eq(category))
                                .where(board.local.eq(member.getLocal().substring(member.getLocal().lastIndexOf("-")+1)))
                                .distinct()
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(board.createdDate.desc())
                                .fetch();
                    }else{
                        boardList = queryFactory.selectFrom(board)
                                .where(board.category.title.eq(category))
                                .where(board.local.eq(member.getLocal().substring(member.getLocal().lastIndexOf("-")+1)))
                                .where(board.ageLt.goe(member.getAge()).and(board.ageRt.loe(member.getAge())))
                                .distinct()
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(board.createdDate.desc())
                                .fetch();
                    }
                }
            }else{
                if(Objects.equals(local, "전체")){
                    if(Objects.equals(age, "전체")){
                        boardList = queryFactory.selectFrom(board)
                                .where(board.category.title.eq(category))
                                .where(board.gender.eq(member.getGender()))
                                .distinct()
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(board.createdDate.desc())
                                .fetch();
                    }else{
                        boardList = queryFactory.selectFrom(board)
                                .where(board.category.title.eq(category))
                                .where(board.gender.eq(member.getGender()))
                                .where(board.ageLt.goe(member.getAge()).and(board.ageRt.loe(member.getAge())))
                                .distinct()
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(board.createdDate.desc())
                                .fetch();
                    }
                }else{
                    if(Objects.equals(age, "전체")){
                        boardList = queryFactory.selectFrom(board)
                                .where(board.category.title.eq(category))
                                .where(board.gender.eq(member.getGender()))
                                .where(board.local.eq(member.getLocal().substring(member.getLocal().lastIndexOf("-")+1)))
                                .distinct()
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(board.createdDate.desc())
                                .fetch();
                    }else{
                        boardList = queryFactory.selectFrom(board)
                                .where(board.category.title.eq(category))
                                .where(board.gender.eq(member.getGender()))
                                .where(board.local.eq(member.getLocal().substring(member.getLocal().lastIndexOf("-")+1)))
                                .where(board.ageLt.goe(member.getAge()).and(board.ageRt.loe(member.getAge())))
                                .distinct()
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(board.createdDate.desc())
                                .fetch();
                    }
                }
            }
        }else{
            if(Objects.equals(gender, "전체")){
                if(Objects.equals(local, "전체")){
                    if(Objects.equals(age, "전체")){
                        boardList = queryFactory.selectFrom(board)
                                .where(board.category.title.eq(category))
                                .distinct()
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(board.likeCount.desc())
                                .orderBy(board.createdDate.desc())
                                .fetch();
                    }else{
                        boardList = queryFactory.selectFrom(board)
                                .where(board.category.title.eq(category))
                                .where(board.ageLt.goe(member.getAge()).and(board.ageRt.loe(member.getAge())))
                                .distinct()
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(board.likeCount.desc())
                                .orderBy(board.createdDate.desc())
                                .fetch();
                    }
                }else{
                    if(Objects.equals(age, "전체")){
                        boardList = queryFactory.selectFrom(board)
                                .where(board.category.title.eq(category))
                                .where(board.local.eq(member.getLocal().substring(member.getLocal().lastIndexOf("-")+1)))
                                .distinct()
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(board.likeCount.desc())
                                .orderBy(board.createdDate.desc())
                                .fetch();
                    }else{
                        boardList = queryFactory.selectFrom(board)
                                .where(board.category.title.eq(category))
                                .where(board.local.eq(member.getLocal().substring(member.getLocal().lastIndexOf("-")+1)))
                                .where(board.ageLt.goe(member.getAge()).and(board.ageRt.loe(member.getAge())))
                                .distinct()
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(board.likeCount.desc())
                                .orderBy(board.createdDate.desc())
                                .fetch();
                    }
                }
            }else{
                if(Objects.equals(local, "전체")){
                    if(Objects.equals(age, "전체")){
                        boardList = queryFactory.selectFrom(board)
                                .where(board.category.title.eq(category))
                                .where(board.gender.eq(member.getGender()))
                                .distinct()
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(board.likeCount.desc())
                                .orderBy(board.createdDate.desc())
                                .fetch();
                    }else{
                        boardList = queryFactory.selectFrom(board)
                                .where(board.category.title.eq(category))
                                .where(board.gender.eq(member.getGender()))
                                .where(board.ageLt.goe(member.getAge()).and(board.ageRt.loe(member.getAge())))
                                .distinct()
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(board.likeCount.desc())
                                .orderBy(board.createdDate.desc())
                                .fetch();
                    }
                }else{
                    if(Objects.equals(age, "전체")){
                        boardList = queryFactory.selectFrom(board)
                                .where(board.category.title.eq(category))
                                .where(board.gender.eq(member.getGender()))
                                .where(board.local.eq(member.getLocal().substring(member.getLocal().lastIndexOf("-")+1)))
                                .distinct()
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(board.likeCount.desc())
                                .orderBy(board.createdDate.desc())
                                .fetch();
                    }else{
                        boardList = queryFactory.selectFrom(board)
                                .where(board.category.title.eq(category))
                                .where(board.gender.eq(member.getGender()))
                                .where(board.local.eq(member.getLocal().substring(member.getLocal().lastIndexOf("-")+1)))
                                .where(board.ageLt.goe(member.getAge()).and(board.ageRt.loe(member.getAge())))
                                .distinct()
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(board.likeCount.desc())
                                .orderBy(board.createdDate.desc())
                                .fetch();
                    }
                }
            }
        }

        return new PageImpl<>(boardList, pageable, boardList.size());
    }

    @Override
    public PageImpl<Board> getVisitorsBoard(String sort, String gender, String local, String age, String categoryName, Pageable pageable) {

        List<Board> boardList;

        if(Objects.equals(sort, "최신순")) {
            if(Objects.equals(gender, "전체")) {
                boardList = queryFactory.selectFrom(board)
                        .where(board.category.title.eq(categoryName))
                        .distinct()
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .orderBy(board.createdDate.desc())
                        .fetch();
                log.info("최신순, 성별 전체 쿼리");
            }else {
                boardList = queryFactory.selectFrom(board)
                        .where(board.category.title.eq(categoryName).and(board.gender.eq(gender)))
                        .distinct()
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .orderBy(board.createdDate.desc())
                        .fetch();
                log.info("최신순 쿼리");
            }
        }else{
            if(Objects.equals(gender, "전체")) {
                boardList = queryFactory.selectFrom(board)
                        .where(board.category.title.eq(categoryName))
                        .distinct()
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .orderBy(board.createdDate.desc())
                        .fetch();
                log.info("좋아요순, 성별 전체 쿼리");
            }else {
                boardList = queryFactory.selectFrom(board)
                        .where(board.category.title.eq(categoryName).and(board.gender.eq(gender)))
                        .distinct()
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .orderBy(board.likeCount.desc())
                        .orderBy(board.createdDate.desc())
                        .fetch();
                log.info("좋아요순 쿼리");
            }
        }

        return new PageImpl<>(boardList, pageable, boardList.size());
    }
}
