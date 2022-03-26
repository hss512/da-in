package com.dain.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<ChatMessage,Long> {

    Optional<ChatMessage> findByWriterAndChatRoom(String writer,ChatRoom chatRoom);
}
