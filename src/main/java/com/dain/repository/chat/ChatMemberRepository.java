package com.dain.repository.chat;

import com.dain.domain.entity.ChatMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMemberRepository extends JpaRepository<ChatMember, Long>, ChatMemberCustomRepository {
    void deleteByRoomIdAndMemberId(Long roomId, Long memberId);

    List<ChatMember> findByRoomId(Long roomId);

    List<ChatMember> findByMemberId(Long memberId);
}
