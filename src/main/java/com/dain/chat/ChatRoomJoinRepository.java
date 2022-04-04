package com.dain.chat;

import com.dain.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomJoinRepository extends JpaRepository<ChatRoomJoin,Long> {
    Optional<ChatRoomJoin> findByChatRoomAndMember(ChatRoom chatRoom,Member member);

    Optional<ChatRoomJoin> findAllByChatRoom(ChatRoom chatRoom);
}
