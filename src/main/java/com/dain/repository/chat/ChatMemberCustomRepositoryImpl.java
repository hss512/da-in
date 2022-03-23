package com.dain.repository.chat;

import com.dain.domain.entity.ChatMember;
import com.dain.domain.entity.Room;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dain.domain.entity.QChatMember.chatMember;
import static com.dain.domain.entity.QRoom.*;

public class ChatMemberCustomRepositoryImpl implements ChatMemberCustomRepository{

    private final JPAQueryFactory queryFactory;

    public ChatMemberCustomRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Room> getRoomList(Long memberId) {

        return queryFactory.selectFrom(room)
                .join(chatMember).on(chatMember.member.id.eq(memberId))
                .where(room.id.eq(
                        JPAExpressions
                                .select(chatMember.room.id)
                                .from(chatMember.room)
                ))
                .orderBy(room.createdDate.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<ChatMember> findAllByMemberId(Long replyMemberId, Long memberId) {

        return queryFactory.selectFrom(chatMember)
                .where(chatMember.member.id.eq(replyMemberId).or(chatMember.member.id.eq(memberId)))
                .distinct()
                .fetch();

    }
}
