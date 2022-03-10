package com.dain.repository.reply;

import com.dain.domain.dto.ReplyDTO;
import com.dain.domain.entity.Reply;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

import static com.dain.domain.entity.QReply.*;

@Log4j2
public class ReplyCustomRepositoryImpl implements ReplyCustomRepository{

    private final JPAQueryFactory queryFactory;

    public ReplyCustomRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ReplyDTO> getReply(Long boardId, Pageable pageable) {

        List<Reply> replyList = queryFactory.selectFrom(reply)
                .where(reply.board.id.eq(boardId))
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reply.createdDate.desc())
                .fetch();

        int size = queryFactory.selectFrom(reply).fetch().size();

        List<ReplyDTO> result = replyList.stream().map(Reply::toReplyDTO).collect(Collectors.toList());

        return new PageImpl<>(result, pageable, size);
    }
}