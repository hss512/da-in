package com.dain.repository.chat;

import com.dain.domain.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByRoomId(Long roomId);

    List<Chat> findByRoomIdAndMemberIdAndChatCheckIsFalse(Long roomId, Long memberId);

    Long countByRoomIdAndMemberIdAndChatCheckIsFalse(Long roomId, Long memberId);
}
