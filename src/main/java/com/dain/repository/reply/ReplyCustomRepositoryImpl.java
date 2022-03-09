package com.dain.repository.reply;

import com.dain.domain.entity.QReply;
import com.dain.domain.entity.Reply;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static com.dain.domain.entity.QReply.*;

public class ReplyCustomRepositoryImpl implements ReplyCustomRepository{

    private final JPAQueryFactory queryFactory;

    public ReplyCustomRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Reply> getReply(Long boardId, Pageable pageable) {

        List<Reply> replyList = queryFactory.selectFrom(reply)
                .where(reply.board.id.eq(boardId))
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reply.createdDate.desc())
                .fetch();

        return new PageImpl<>(replyList, pageable, replyList.size());
    }
}