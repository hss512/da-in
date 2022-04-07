package com.dain.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<ChatMessage,Long> {

    Optional<ChatMessage> findByWriterAndChatRoomAndMessage(String writer,ChatRoom chatRoom,String message);

}
